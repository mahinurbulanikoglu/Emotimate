# app/main.py
from fastapi import FastAPI, HTTPException, Depends
from fastapi.middleware.cors import CORSMiddleware
import google.generativeai as genai
from pydantic import BaseModel
from typing import List, Optional, Dict
import os
from dotenv import load_dotenv
from .services.firebase_service import FirebaseService
from .services.ai_service import AIService
from .models.schemas import TestResult, TherapyReport, ContentRecommendation

# .env dosyasını yükle
load_dotenv()

app = FastAPI(title="Emotimate AI Backend")

# CORS ayarları
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Geliştirme için. Prodüksiyonda spesifik origin'ler belirtilmeli
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Servisleri başlat
firebase_service = FirebaseService()

# Gemini AI yapılandırması
genai.configure(api_key=os.getenv("GEMINI_API_KEY"))
model = genai.GenerativeModel('models/gemini-2.0-flash')

# Initialize AI service
ai_service = AIService()

# Veri modelleri
class EmotionInput(BaseModel):
    text: str
    emotion: str
    user_id: str

class SchemaResult(BaseModel):
    schema_name: str
    score: float
    user_id: str

class TherapyRequest(BaseModel):
    emotion_input: EmotionInput
    schema_results: List[SchemaResult]

class MoodEntry(BaseModel):
    mood: str
    note: Optional[str] = None
    activities: Optional[List[str]] = None
    triggers: Optional[List[str]] = None

class UserPreferences(BaseModel):
    notification_enabled: bool = True
    dark_mode: bool = False
    language: str = "tr"
    meditation_reminders: bool = True
    daily_mood_check: bool = True

# API endpoint'leri
@app.get("/")
async def root():
    return {"message": "Emotimate AI Backend API"}

@app.post("/api/v1/analyze-emotion")
async def analyze_emotion(emotion_input: EmotionInput):
    try:
        # Kullanıcı mesajını Firebase'e kaydet
        await firebase_service.save_chat_message(
            emotion_input.user_id,
            {
                "text": emotion_input.text,
                "isUser": True,
                "emotion": emotion_input.emotion
            }
        )

        prompt = f"""
        Kullanıcıdan gelen mesaj:
        "{emotion_input.text}"

        Lütfen bu metni terapist gibi analiz et. Aşağıdaki başlıklarda yanıt ver:
        - Duygu durumu analizi (hangi duygular, yoğunluk?)
        - Olası psikolojik şemalar
        - BDT teknikleriyle düşünce yorumu
        - Kapanışta kısa öneri ve empati

        Yanıt kuralları:
        - Türkçe yaz.
        - Sade, samimi ve açıklayıcı yaz.
        - Madde işaretleri (*, -, •), başlıklar (##, **) veya markdown biçimi kullanma.
        - Paragraflar hâlinde düz yazı kullan.
        """

        response = model.generate_content(prompt)
        analysis = response.text

        # AI yanıtını Firebase'e kaydet
        await firebase_service.save_chat_message(
            emotion_input.user_id,
            {
                "text": analysis,
                "isUser": False,
                "emotion": emotion_input.emotion,
                "analysis": analysis
            }
        )

        return {"analysis": analysis}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/therapy-response")
async def get_therapy_response(request: TherapyRequest):
    try:
        schema_text = "\n".join([
            f"{schema.schema_name} ({schema.score})"
            for schema in request.schema_results
        ])

        prompt = f"""
        Kullanıcıdan gelen duygusal metin:
        "{request.emotion_input.text}"

        Duygu: {request.emotion_input.emotion}

        Şema Sonuçları:
        {schema_text}

        Lütfen aşağıdaki yapıda terapötik bir yanıt ver:
        - Empatik ve anlayışlı giriş
        - BDT (bilişsel davranışçı terapi) teknikleriyle düşünce analizi
        - Şema terapisi çerçevesinde yorum
        - Pratik egzersiz önerisi (günlük, nefes, vb.)
        - Destekleyici kapanış

        🔒 Format kuralları:
        - Türkçe yaz.
        - Samimi, insan gibi yaz.
        - Madde işareti, yıldız, başlık, markdown kullanma.
        - Düz metin yaz.
        """

        response = model.generate_content(prompt)
        return {"therapy_response": response.text}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/mood")
async def save_mood(user_id: str, mood_entry: MoodEntry):
    try:
        success = await firebase_service.save_mood_entry(user_id, mood_entry.dict())
        if success:
            return {"message": "Duygu durumu başarıyla kaydedildi"}
        raise HTTPException(status_code=500, detail="Duygu durumu kaydedilemedi")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/mood/history")
async def get_mood_history(user_id: str, days: int = 7):
    try:
        history = await firebase_service.get_mood_history(user_id, days)
        return {"history": history}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/mood/stats")
async def get_mood_stats(user_id: str, days: int = 7):
    try:
        stats = await firebase_service.get_mood_stats(user_id, days)
        return {"stats": stats}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/preferences")
async def save_preferences(user_id: str, preferences: UserPreferences):
    try:
        success = await firebase_service.save_user_preferences(user_id, preferences.dict())
        if success:
            return {"message": "Tercihler başarıyla kaydedildi"}
        raise HTTPException(status_code=500, detail="Tercihler kaydedilemedi")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/preferences")
async def get_preferences(user_id: str):
    try:
        preferences = await firebase_service.get_user_preferences(user_id)
        if preferences is None:
            return {"preferences": UserPreferences().dict()}
        return {"preferences": preferences}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/analyze-test-results")
async def analyze_test_results(test_results: List[TestResult], user_id: str):
    try:
        analysis = await ai_service.analyze_test_results(test_results, user_id)
        return analysis
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/emotional-support")
async def get_emotional_support(mood_entry: MoodEntry, user_id: str):
    try:
        support = await ai_service.provide_emotional_support(mood_entry, user_id)
        return support
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/generate-therapy-report")
async def generate_therapy_report(user_id: str):
    try:
        report = await ai_service.generate_therapy_report(user_id)
        # Save report to database
        await firebase_service.save_therapy_report(user_id, report.dict())
        return {"message": "Terapi raporu başarıyla oluşturuldu"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/therapy-report/{user_id}")
async def get_therapy_report(user_id: str):
    try:
        report = await firebase_service.get_therapy_report(user_id)
        if not report:
            raise HTTPException(status_code=404, detail="Rapor bulunamadı")
        return report
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)