# app/main.py
from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import google.generativeai as genai
from pydantic import BaseModel
from typing import List, Optional
import os
from dotenv import load_dotenv

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

# Gemini AI yapılandırması
genai.configure(api_key=os.getenv("GEMINI_API_KEY"))
model = genai.GenerativeModel('models/gemini-2.0-flash')

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

# API endpoint'leri
@app.get("/")
async def root():
    return {"message": "Emotimate AI Backend API"}

@app.post("/api/v1/analyze-emotion")
async def analyze_emotion(emotion_input: EmotionInput):
    try:
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
        return {"analysis": response.text}
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

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)