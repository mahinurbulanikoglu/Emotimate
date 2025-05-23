# app/services/ai_service.py
import google.generativeai as genai
from typing import Dict, List
import json

class AIService:
    def __init__(self, api_key: str):
        genai.configure(api_key=api_key)
        self.model = genai.GenerativeModel('gemini-2.0-flash')
        
    async def analyze_emotion(self, text: str) -> Dict:
        prompt = f"""
        Analiz et: {text}
        Duygu durumu, şemalar ve öneriler sun.
        
        """
        response = await self.model.generate_content(prompt)
        return self._process_response(response)
    
    async def generate_therapy_response(self, 
                                      emotion: str, 
                                      schema_results: List[Dict]) -> str:
        prompt = f"""
        Duygu: {emotion}
        Şema Sonuçları: {schema_results}
        
        BDT tekniklerini kullanarak terapötik bir yanıt oluştur.
        """
        response = await self.model.generate_content(prompt)
        return response.text

    async def generate_recommendations(self, emotion_analysis: str) -> List[Dict]:
        prompt = f"""
        Duygu: {emotion_analysis}
        Şema Sonuçları: {emotion_analysis}
        
        BDT tekniklerini kullanarak terapötik bir yanıt oluştur.
        """
        response = await self.model.generate_content(prompt)
        recommendation_json = response.text
        if not recommendation_json or not recommendation_json.strip():
            parsed_recommendations = []
        else:
            try:
                parsed_recommendations = json.loads(recommendation_json)
            except Exception as e:
                print("❌ JSON parse hatası:", e)
                parsed_recommendations = []
        return parsed_recommendations
    
    def _process_response(self, response):
        # Placeholder implementation, adjust according to original logic
        return response.text # Or parse the response as needed