# app/main.py
from fastapi import FastAPI, HTTPException, Depends
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List, Optional, Dict, Any
import os
from dotenv import load_dotenv
from .services.firebase_service import FirebaseService
from .services.ai_agent import EmotimateAgent
from datetime import datetime, timedelta
import json  # eklenmeli
from starlette.middleware.base import BaseHTTPMiddleware
from starlette.requests import Request
from starlette.responses import Response
import asyncio
import time
import logging

# Logging ayarları
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# .env dosyasını yükle
load_dotenv()

class TimeoutMiddleware(BaseHTTPMiddleware):
    async def dispatch(self, request: Request, call_next):
        start_time = time.time()
        try:
            return await asyncio.wait_for(call_next(request), timeout=120.0)  # 90 saniye timeout
        except asyncio.TimeoutError:
            logger.error(f"Request timeout after {time.time() - start_time} seconds: {request.url}")
            return Response("Request timeout", status_code=504)

app = FastAPI(
    title="Emotimate AI Backend",
    description="Emotimate uygulaması için yapay zeka destekli backend API",
    version="1.0.0"
)

# Middleware'leri ekle
app.add_middleware(TimeoutMiddleware)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Tüm originlere izin ver
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Servisleri başlat
firebase_service = FirebaseService()
ai_agent = EmotimateAgent()

# Veri modelleri
class EmotionInput(BaseModel):
    text: str
    emotion: str
    userId: str

class SchemaResult(BaseModel):
    schema_name: str
    score: float
    userId: str

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

# Yeni veri modelleri
class TestAnswer(BaseModel):
    question_id: str
    answer: str
    score: Optional[float] = None

class TestSubmission(BaseModel):
    test_type: str  # "schema", "depression", "anxiety" vs.
    test_name: str
    answers: List[TestAnswer]
    userId: str
    completion_time: Optional[int] = None  # saniye cinsinden

class TestResult(BaseModel):
    test_type: str
    test_name: str
    total_score: float
    sub_scores: Dict[str, float]  # Alt kategoriler için puanlar
    interpretation: str
    recommendations: List[str]
    userId: str
    timestamp: datetime

# Response modelleri
class ApiResponse(BaseModel):
    success: bool
    message: str
    data: Optional[Any] = None

class CBTRecommendation(BaseModel):
    title: str
    description: str
    frequency: str
    duration: str

class SchemaInterpretation(BaseModel):
    intensity: str
    description: str
    cbtTechniques: List[str]

class TestAnalysis(BaseModel):
    analysis: str
    riskLevel: str
    recommendations: List[str]
    cbtRecommendations: List[CBTRecommendation]
    schemaInterpretation: Dict[str, SchemaInterpretation]

class EmotionAnalysisResponse(BaseModel):
    emotion: str
    confidence: float
    analysis: str
    suggestions: List[str] = []
    testAnalysis: Optional[TestAnalysis] = None

class TestResultResponse(BaseModel):
    test_type: str
    test_name: str
    total_score: float
    sub_scores: Dict[str, float]
    interpretation: str
    recommendations: List[str]
    timestamp: datetime

class TestTrendResponse(BaseModel):
    total_score: List[float]
    sub_scores: Dict[str, List[float]]
    improvement: float

class TestAnalysisRequest(BaseModel):
    test_type: str
    test_results: Dict[str, Any]  # Changed from Dict[str, int] to Dict[str, Any] to support all test types
    userId: str

class TestAnalysisResponse(BaseModel):
    analysis: str
    recommendations: List[str]
    risk_level: str
    follow_up_points: List[str]
    cbt_recommendations: List[Dict[str, str]]
    schema_interpretation: Dict[str, Dict[str, Any]]

# API endpoint'leri
@app.get("/")
async def root():
    return {"message": "Emotimate AI Backend API"}

@app.post("/api/v1/analyze-emotion", response_model=EmotionAnalysisResponse)
async def analyze_emotion(emotion_input: EmotionInput):
    try:
        print("🟣 1) analyze_emotion çağrıldı.")
        print("📥 Kullanıcı Girdisi:", emotion_input.dict())
        # Kullanıcının duygu geçmişini al
        emotion_history = await firebase_service.get_mood_history(emotion_input.userId)
        print("📊 Emotion History:", emotion_history)
        
        # Kullanıcının test sonuçlarını al
        test_results = await firebase_service.get_test_result_from_android_from_android(emotion_input.userId)
        print("📄 Test Results:", test_results)
        
        # Test sonuçlarını liste formatında tut
        latest_test_results = []
        if test_results:
            # En son test sonuçlarını al
            latest_tests = {}
            for result in test_results:
                test_type = result.get('test_type')
                if test_type not in latest_tests or result.get('timestamp', '') > latest_tests[test_type].get('timestamp', ''):
                    latest_tests[test_type] = result
            
            # Test sonuçlarını liste formatına dönüştür
            latest_test_results = list(latest_tests.values())
            print("📌 Latest Test Results:", latest_test_results)
        
        # Duygu analizi yap
        emotion_analysis = ai_agent.analyze_emotional_context(
            emotion_input.text,
            emotion_history,
            latest_test_results
        )
        print("📈 Emotion Analysis:", emotion_analysis)
        
        # Terapötik yanıt oluştur
        response = ai_agent.generate_therapeutic_response(
            emotion_input.text,
            emotion_analysis
        )
        print("🧠 Terapötik Yanıt:", response)
        
        # Kullanıcı mesajını ve AI yanıtını kaydet
        await firebase_service.save_chat_message(
            emotion_input.userId,
            {
                "text": emotion_input.text,
                "isUser": True,
                "emotion": emotion_input.emotion,
                "emotion_analysis": emotion_analysis
            }
        )
        
        await firebase_service.save_chat_message(
            emotion_input.userId,
            {
                "text": response,
                "isUser": False,
                "emotion": emotion_input.emotion,
                "analysis": response
            }
        )
        
        # Öneriler oluştur
        recommendation_json = ai_agent.generate_recommendations(emotion_analysis)
        try:
            parsed_recommendations = json.loads(recommendation_json)
        except Exception as e:
            print("❌ JSON parse hatası:", e)
            parsed_recommendations = []

        # Liste tipinde değilse fallback
        if not isinstance(parsed_recommendations, list):
            parsed_recommendations = []

        recommendations = parsed_recommendations
        
        # Test analizi oluştur
        test_analysis = None
        if 'test_analysis' in emotion_analysis:
            ta = emotion_analysis['test_analysis']
            test_analysis = TestAnalysis(
                analysis=ta.get('analysis', ''),
                riskLevel=ta.get('riskLevel', ''),
                recommendations=ta.get('recommendations', []),
                cbtRecommendations=[CBTRecommendation(**cbt) for cbt in ta.get('cbtRecommendations', [])],
                schemaInterpretation={k: SchemaInterpretation(**v) for k, v in ta.get('schemaInterpretation', {}).items()}
            )
            print("🧪 Test Analysis:", test_analysis)
        
        return EmotionAnalysisResponse(
            emotion=emotion_input.emotion,
            confidence=1.0,  # örnek değer
            analysis=response,
            suggestions=recommendations,
            testAnalysis=test_analysis
        )
    except Exception as e:
        print("❌ Hata (analyze_emotion):", str(e))
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/therapy-response")
async def get_therapy_response(request: TherapyRequest):
    try:
        # Duygu analizi yap
        emotion_analysis = ai_agent.analyze_emotional_context(
            request.emotion_input.text,
            await firebase_service.get_mood_history(request.emotion_input.userId)
        )
        
        # Terapötik yanıt oluştur
        response = ai_agent.generate_therapeutic_response(
            request.emotion_input.text,
            emotion_analysis,
            [schema.dict() for schema in request.schema_results]
        )
        
        return {"therapy_response": response}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/mood")
async def save_mood(userId: str, mood_entry: MoodEntry):
    try:
        # Duygu analizi yap
        emotion_analysis = ai_agent.analyze_emotional_context(
            mood_entry.note or "",
            await firebase_service.get_mood_history(userId)
        )
        
        # Duygu girişini kaydet
        success = await firebase_service.save_mood_entry(
            userId,
            {
                **mood_entry.dict(),
                "userId": userId,
                "emotion_analysis": emotion_analysis
            }
        )
        
        if success:
            return {"message": "Duygu durumu başarıyla kaydedildi"}
        raise HTTPException(status_code=500, detail="Duygu durumu kaydedilemedi")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/generate-psychologist-report")
async def generate_psychologist_report(userId: str):
    try:
        # Kullanıcı verilerini topla
        user_data = await firebase_service.get_user_data(userId)
        emotion_history = await firebase_service.get_mood_history(userId)
        test_results = await firebase_service.get_test_result_from_android_from_android(userId)
        
        # Psikolog raporu oluştur
        report = ai_agent.generate_psychologist_report(
            user_data,
            emotion_history,
            test_results
        )
        
        # Raporu kaydet
        await firebase_service.save_psychologist_report(
            userId,
            {
                "report_date": datetime.now().isoformat(),
                "report_content": report,
                "is_shared": False
            }
        )
        
        return {"message": "Psikolog raporu başarıyla oluşturuldu"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/recommendations")
async def get_recommendations(userId: str):
    try:
        # Kullanıcının son duygu durumunu al
        emotion_history = await firebase_service.get_mood_history(userId, days=1)
        if not emotion_history:
            raise HTTPException(status_code=404, detail="Duygu durumu bulunamadı")
        
        # Duygu analizi yap
        emotion_analysis = ai_agent.analyze_emotional_context(
            emotion_history[0].get("note", ""),
            emotion_history
        )
        
        # Öneriler oluştur
        recommendations = ai_agent.generate_recommendations(emotion_analysis)
        
        return {"recommendations": recommendations}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/mood/history")
async def get_mood_history(userId: str, days: int = 7):
    try:
        history = await firebase_service.get_mood_history(userId, days)
        return {"history": history}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/mood/stats")
async def get_mood_stats(userId: str, days: int = 7):
    try:
        stats = await firebase_service.get_mood_stats(userId, days)
        return {"stats": stats}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/preferences")
async def save_preferences(userId: str, preferences: UserPreferences):
    try:
        success = await firebase_service.save_user_preferences(userId, preferences.dict())
        if success:
            return {"message": "Tercihler başarıyla kaydedildi"}
        raise HTTPException(status_code=500, detail="Tercihler kaydedilemedi")
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/preferences")
async def get_preferences(userId: str):
    try:
        preferences = await firebase_service.get_user_preferences(userId)
        if preferences is None:
            return {"preferences": UserPreferences().dict()}
        return {"preferences": preferences}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/tests/submit", response_model=ApiResponse)
async def submit_test(submission: TestSubmission):
    try:
        # Test sonuçlarını hesapla
        total_score = sum(answer.score or 0 for answer in submission.answers)
        
        # Alt kategorileri hesapla (test türüne göre)
        sub_scores = {}
        if submission.test_type == "schema":
            # Şema testi için alt kategoriler
            schema_categories = {
                "abandonment": ["1", "2", "3"],  # Örnek soru ID'leri
                "defectiveness": ["4", "5", "6"],
                "failure": ["7", "8", "9"]
            }
            
            for category, question_ids in schema_categories.items():
                category_score = sum(
                    answer.score or 0 
                    for answer in submission.answers 
                    if answer.question_id in question_ids
                )
                sub_scores[category] = category_score / len(question_ids)
        
        # AI Agent ile yorum oluştur
        interpretation = ai_agent.generate_therapeutic_response(
            f"Test Sonuçları: {submission.test_name}\nToplam Puan: {total_score}\nAlt Kategoriler: {sub_scores}",
            {"current_emotions": {}, "emotion_trends": {}},
            [{"schema_name": k, "score": v} for k, v in sub_scores.items()]
        )
        
        # Öneriler oluştur
        recommendations = ai_agent.generate_recommendations(
            {"current_emotions": {}, "emotion_trends": {}},
            {"test_type": submission.test_type, "test_results": sub_scores}
        )
        
        # Sonuçları kaydet
        test_result = TestResult(
            test_type=submission.test_type,
            test_name=submission.test_name,
            total_score=total_score,
            sub_scores=sub_scores,
            interpretation=interpretation,
            recommendations=recommendations,
            userId=submission.userId,
            timestamp=datetime.now()
        )
        
        await firebase_service.save_test_analysis(
            submission.userId,
            test_result.dict()
        )
        
        return ApiResponse(
            success=True,
            message="Test sonuçları başarıyla kaydedildi",
            data=TestResultResponse(**test_result.dict())
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/tests/results/{userId}", response_model=ApiResponse)
async def get_test_result_from_android_from_android(userId: str, test_type: Optional[str] = None):
    try:
        results = await firebase_service.get_test_result_from_android_from_android(userId)
        
        if test_type:
            results = [r for r in results if r.get('test_type') == test_type]
        
        return ApiResponse(
            success=True,
            message="Test sonuçları başarıyla getirildi",
            data=[TestResultResponse(**result) for result in results]
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/tests/analysis/{userId}")
async def get_test_analysis(userId: str, test_type: Optional[str] = None):
    try:
        # Test sonuçlarını al
        test_results = await firebase_service.get_test_result_from_android_from_android(userId)
        if test_type:
            test_results = [r for r in test_results if r.get('test_type') == test_type]
        
        # Duygu geçmişini al
        emotion_history = await firebase_service.get_mood_history(userId)
        
        # AI Agent ile detaylı analiz oluştur
        analysis = ai_agent.generate_psychologist_report(
            {"test_results": test_results},
            emotion_history,
            test_results
        )
        
        return {
            "analysis": analysis,
            "test_results": test_results
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/tests/trends/{userId}", response_model=ApiResponse)
async def get_test_trends(userId: str, test_type: str, days: int = 30):
    try:
        # Test sonuçlarını al
        test_results = await firebase_service.get_test_result_from_android_from_android(userId)
        test_results = [r for r in test_results if r.get('test_type') == test_type]
        
        # Son 30 günlük sonuçları filtrele
        start_date = datetime.now() - timedelta(days=days)
        recent_results = [
            r for r in test_results 
            if datetime.fromisoformat(r['timestamp']) >= start_date
        ]
        
        # Trend analizi yap
        trends = {
            "total_score": [],
            "sub_scores": {},
            "improvement": 0.0
        }
        
        for result in recent_results:
            trends["total_score"].append(result['total_score'])
            for category, score in result['sub_scores'].items():
                if category not in trends["sub_scores"]:
                    trends["sub_scores"][category] = []
                trends["sub_scores"][category].append(score)
        
        # İyileşme oranını hesapla
        if len(trends["total_score"]) >= 2:
            first_score = trends["total_score"][0]
            last_score = trends["total_score"][-1]
            trends["improvement"] = ((last_score - first_score) / first_score) * 100
        
        return ApiResponse(
            success=True,
            message="Test trendleri başarıyla getirildi",
            data=TestTrendResponse(**trends)
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/api/v1/analyze-test", response_model=TestAnalysisResponse)
async def analyze_test(request: TestAnalysisRequest):
    try:
        # Kullanıcı geçmişini al
        user_history = await firebase_service.get_user_data(request.userId)
        
        # Test sonuçlarını al
        test_results = await firebase_service.get_test_result_from_android_from_android(request.userId)
        
        # AI Agent ile test analizi yap
        analysis_result = ai_agent.analyze_test_results(
            test_type=request.test_type,
            test_results=request.test_results,
            user_history=user_history
        )
        
        # Sonuçları Firebase'e kaydet
        await firebase_service.save_test_analysis(
            userId=request.userId,
            test_type=request.test_type,
            analysis_result=analysis_result
        )
        
        return analysis_result
        
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/api/v1/debug/test-results/{userId}")
async def debug_test_results(userId: str):
    try:
        # Test sonuçlarını al (eski yöntem)
        test_results = await firebase_service.get_test_result_from_android_from_android(userId)
        print("Debug - Test Results (eski):", test_results)

        # Test sonuçlarını al (Android path)
        test_results = await firebase_service.get_test_result_from_android_from_android(userId)
        print("Debug - Test Results (android):", test_results)

        # Duygu geçmişini al
        emotion_history = await firebase_service.get_mood_history(userId)
        print("Debug - Emotion History:", emotion_history)

        return {
            "test_results": test_results,
            "test_results": test_results,
            "emotion_history": emotion_history
        }
    except Exception as e:
        print("Debug - Error:", str(e))
        raise HTTPException(status_code=500, detail=str(e))

# Hata yakalama middleware'i
@app.middleware("http")
async def error_handling_middleware(request, call_next):
    try:
        response = await call_next(request)
        return response
    except Exception as e:
        return ApiResponse(
            success=False,
            message=str(e),
            data=None
        )

# API dokümantasyonu için ek bilgiler
app.openapi_tags = [
    {
        "name": "Duygu Analizi",
        "description": "Duygu analizi ve terapötik yanıtlar için endpoint'ler"
    },
    {
        "name": "Testler",
        "description": "Psikolojik testler ve sonuçları için endpoint'ler"
    },
    {
        "name": "Öneriler",
        "description": "Kişiselleştirilmiş öneriler için endpoint'ler"
    }
]

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)