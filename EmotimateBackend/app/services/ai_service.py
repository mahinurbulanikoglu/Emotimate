# app/services/ai_service.py
import google.generativeai as genai
from typing import Dict, List

class AIService:
    def __init__(self, api_key: str):
        genai.configure(api_key=api_key)
        self.model = genai.GenerativeModel('gemini-pro')
        
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