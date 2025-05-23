import google.generativeai as genai
from typing import Dict, List, Optional
import os
from datetime import datetime
import json

class EmotimateAgent:
    def __init__(self):
        # Gemini AI yapılandırması
        genai.configure(api_key=os.getenv("GEMINI_API_KEY"))
        self.model = genai.GenerativeModel('gemini-2.0-flash')
        self.chat = self.model.start_chat(history=[])
        
        # Duygu kategorileri ve anahtar kelimeleri (GERİ EKLENDİ)
        self.emotion_keywords = {
            "kaygı": ["endişe", "kaygı", "panik", "stres", "anksiyete"],
            "depresyon": ["üzüntü", "mutsuzluk", "karamsarlık", "enerjisizlik"],
            "öfke": ["kızgınlık", "sinir", "öfke", "hiddet"],
            "mutluluk": ["mutlu", "neşeli", "keyifli", "sevinc"],
            "yalnızlık": ["yalnız", "terkedilmiş", "izole", "sosyal izolasyon"],
            "korku": ["korku", "dehşet", "tedirginlik", "endişe"]
        }
        
        # Şema kategorileri ve açıklamaları
        self.schema_descriptions = {
            "abandonment": {
                "description": "Terk edilme ve istikrarsız ilişkiler korkusu",
                "cbt_techniques": [
                    "Güvenli bağlanma egzersizleri",
                    "İlişki güvenliği değerlendirmesi",
                    "Kaygı yönetimi teknikleri"
                ]
            },
            "dependency": {
                "description": "Bağımlılık ve kendine güven eksikliği",
                "cbt_techniques": [
                    "Bağımsızlık geliştirme egzersizleri",
                    "Karar verme becerileri",
                    "Öz-yeterlilik artırma teknikleri"
                ]
            },
            "emotional_deprivation": {
                "description": "Duygusal yoksunluk ve destek eksikliği",
                "cbt_techniques": [
                    "Duygu ihtiyaç farkındalığı",
                    "İlişki beklentileri analizi",
                    "Duygu ifade egzersizleri"
                ]
            },
            "social_isolation": {
                "description": "Sosyal izolasyon ve yabancılık hissi",
                "cbt_techniques": [
                    "Sosyal beceri geliştirme",
                    "Güvenli sosyal ortam oluşturma",
                    "Sosyal kaygı yönetimi"
                ]
            },
            "self_sacrifice": {
                "description": "Kendini feda etme ve başkalarını önceliklendirme",
                "cbt_techniques": [
                    "Sınır koyma egzersizleri",
                    "Öz-bakım planlaması",
                    "İhtiyaç ifadesi pratikleri"
                ]
            },
            "subjugation": {
                "description": "Boyun eğicilik ve kontrol edilme",
                "cbt_techniques": [
                    "Özgüven geliştirme",
                    "Sınır belirleme",
                    "İletişim becerileri"
                ]
            },
            "failure": {
                "description": "Başarısızlık ve yetersizlik hissi",
                "cbt_techniques": [
                    "Gerçekçi hedef belirleme",
                    "Başarı günlüğü tutma",
                    "Olumlu iç konuşma pratikleri"
                ]
            },
            "shame": {
                "description": "Kusurluluk ve utanç duyguları",
                "cbt_techniques": [
                    "Kendini kabul etme egzersizleri",
                    "Olumlu özellik listesi oluşturma",
                    "Düşünce kayıtları tutma"
                ]
            },
            "pessimism": {
                "description": "Karamsarlık ve felaketçilik",
                "cbt_techniques": [
                    "Düşünce yeniden yapılandırma",
                    "Olumlu senaryo oluşturma",
                    "Farkındalık meditasyonu"
                ]
            },
            "emotional_suppression": {
                "description": "Duygusal bastırma ve ifade edememe",
                "cbt_techniques": [
                    "Duygu ifade egzersizleri",
                    "Duygu günlüğü tutma",
                    "Farkındalık pratikleri"
                ]
            }
        }

        # Test puan aralıkları ve yorumları
        self.test_score_ranges = {
            "schema": {
                "abandonment": {
                    "0-24": "Düşük düzeyde Terk Edilme / İstikrarsızlık Şeması",
                    "25-39": "Orta düzeyde Terk Edilme / İstikrarsızlık Şeması",
                    "40-100": "Yüksek düzeyde Terk Edilme / İstikrarsızlık Şeması"
                },
                "dependency": {
                    "0-24": "Düşük düzeyde Bağımlılık Şeması",
                    "25-39": "Orta düzeyde Bağımlılık Şeması",
                    "40-100": "Yüksek düzeyde Bağımlılık Şeması"
                },
                "emotional_deprivation": {
                    "0-24": "Düşük düzeyde Duygusal Yoksunluk Şeması",
                    "25-39": "Orta düzeyde Duygusal Yoksunluk Şeması",
                    "40-100": "Yüksek düzeyde Duygusal Yoksunluk Şeması"
                },
                "social_isolation": {
                    "0-24": "Düşük düzeyde Sosyal İzolasyon / Yabancılık Şeması",
                    "25-39": "Orta düzeyde Sosyal İzolasyon / Yabancılık Şeması",
                    "40-100": "Yüksek düzeyde Sosyal İzolasyon / Yabancılık Şeması"
                },
                "self_sacrifice": {
                    "0-24": "Düşük düzeyde Kendini Feda Şeması",
                    "25-39": "Orta düzeyde Kendini Feda Şeması",
                    "40-100": "Yüksek düzeyde Kendini Feda Şeması"
                },
                "subjugation": {
                    "0-24": "Düşük düzeyde Boyun Eğicilik Şeması",
                    "25-39": "Orta düzeyde Boyun Eğicilik Şeması",
                    "40-100": "Yüksek düzeyde Boyun Eğicilik Şeması"
                },
                "failure": {
                    "0-24": "Düşük düzeyde Başarısızlık Şeması",
                    "25-39": "Orta düzeyde Başarısızlık Şeması",
                    "40-100": "Yüksek düzeyde Başarısızlık Şeması"
                },
                "shame": {
                    "0-34": "Düşük düzeyde Kusurluluk / Utanç Şeması",
                    "35-54": "Orta düzeyde Kusurluluk / Utanç Şeması",
                    "55-100": "Yüksek düzeyde Kusurluluk / Utanç Şeması"
                },
                "pessimism": {
                    "0-24": "Düşük düzeyde Karamsarlık / Felaketçilik Şeması",
                    "25-39": "Orta düzeyde Karamsarlık / Felaketçilik Şeması",
                    "40-100": "Yüksek düzeyde Karamsarlık / Felaketçilik Şeması"
                },
                "emotional_suppression": {
                    "0-24": "Düşük düzeyde Duygusal Bastırma Şeması",
                    "25-39": "Orta düzeyde Duygusal Bastırma Şeması",
                    "40-100": "Yüksek düzeyde Duygusal Bastırma Şeması"
                }
            },
            "beck_depresyon": {
                "0-9": "Minimal depresyon",
                "10-16": "Hafif depresyon",
                "17-29": "Orta düzey depresyon",
                "30-63": "Şiddetli depresyon"
            },
            "beck_anksiyete": {
                "0-7": "Minimal anksiyete",
                "8-15": "Hafif anksiyete",
                "16-25": "Orta düzey anksiyete",
                "26-63": "Şiddetli anksiyete"
            },
            "panas": {
                "positive": {
                    "10-19": "Düşük pozitif duygu",
                    "20-29": "Orta düzey pozitif duygu",
                    "30-39": "Yüksek pozitif duygu",
                    "40-50": "Çok yüksek pozitif duygu"
                },
                "negative": {
                    "10-19": "Düşük negatif duygu",
                    "20-29": "Orta düzey negatif duygu",
                    "30-39": "Yüksek negatif duygu",
                    "40-50": "Çok yüksek negatif duygu"
                }
            },
            "poms": {
                "tension": {
                    "0-8": "Düşük düzeyde gerilim",
                    "9-15": "Orta düzeyde gerilim",
                    "16-100": "Yüksek düzeyde gerilim"
                },
                "depression": {
                    "0-8": "Düşük düzeyde depresyon",
                    "9-15": "Orta düzeyde depresyon",
                    "16-100": "Yüksek düzeyde depresyon"
                },
                "anger": {
                    "0-8": "Düşük düzeyde öfke",
                    "9-15": "Orta düzeyde öfke",
                    "16-100": "Yüksek düzeyde öfke"
                },
                "fatigue": {
                    "0-8": "Düşük düzeyde yorgunluk",
                    "9-15": "Orta düzeyde yorgunluk",
                    "16-100": "Yüksek düzeyde yorgunluk"
                },
                "confusion": {
                    "0-8": "Düşük düzeyde zihin bulanıklığı",
                    "9-15": "Orta düzeyde zihin bulanıklığı",
                    "16-100": "Yüksek düzeyde zihin bulanıklığı"
                },
                "vigor": {
                    "0-8": "Düşük düzeyde canlılık",
                    "9-15": "Orta düzeyde canlılık",
                    "16-100": "Yüksek düzeyde canlılık"
                }
            }
        }

    def detect_emotion(self, text: str) -> Dict[str, float]:
        """
        Metindeki duyguları tespit eder ve yoğunluklarını hesaplar
        """
        emotions = {}
        text = text.lower()
        
        for emotion, keywords in self.emotion_keywords.items():
            score = 0
            for keyword in keywords:
                if keyword in text:
                    score += 1
            if score > 0:
                emotions[emotion] = score / len(keywords)
        
        return emotions

    def analyze_emotional_context(self, text: str, emotion_history: List[Dict], test_results: Optional[List[Dict]] = None) -> Dict:
        """
        Metni, duygu geçmişini ve test sonuçları listesini analiz eder.
        test_results artık Optional[Dict] yerine Optional[List[Dict]] alıyor.
        """
        # Test sonuçlarını analiz et (liste versiyonunu kullan)
        test_analysis = {}
        if test_results:
            # Eğer test_results bir string ise, boş liste olarak işle
            if isinstance(test_results, str):
                test_results = []
            test_analysis = self._analyze_test_scores(test_results)
        
        # Mevcut duyguları tespit et
        current_emotions = self.detect_emotion(text)
        
        # Duygu geçmişinden trend analizi
        emotion_trends = self._analyze_emotion_trends(emotion_history)
        
        # Şema analizi (liste versiyonunu kullan)
        schema_analysis = self._analyze_schemas(test_results) if test_results else {}
        
        return {
            "current_emotions": current_emotions,
            "emotion_trends": emotion_trends,
            "test_analysis": test_analysis,
            "schema_analysis": schema_analysis
        }

    def _analyze_test_scores(self, test_results_list: List[Dict]) -> Dict:
        """Test puanlarını analiz eder ve yorumlar (Liste versiyonu)."""
        analysis = {}
        
        for test_result in test_results_list:
            test_type = test_result.get('test_type')
            test_name = test_result.get('testName')
            total_score = test_result.get('totalScore')
            interpretation = test_result.get('interpretation')

            if test_type and total_score is not None and interpretation:
                # Burada her bir test sonucu için detaylı analizi oluşturabilirsiniz.
                # analyze_test_results metodu tek bir test sonucu işleyebildiği için onu burada kullanabiliriz.
                try:
                    # analyze_test_results metodu Dict[str, Any] test_results bekliyor, bu da gelen her bir test_result objesine uyuyor.
                    # User history burada boş geçildi, gerekirse analyze_emotional_context'ten alınıp gönderilmeli.
                    single_test_analysis = self.analyze_test_results(test_type, test_result, {}) 
                    
                    # Sonuçları ana analysis dictionary'sine ekle. Test tipine göre veya başka bir anahtarla gruplayabilirsiniz.
                    # Örneğin, test tipi + test adı gibi benzersiz bir anahtar kullanabilirsiniz.
                    analysis_key = f"{test_type}_{test_name}" if test_name else test_type
                    analysis[analysis_key] = single_test_analysis
                    
                except Exception as e:
                    print(f"⚠️ Tekil test analizi sırasında hata: {e}")
                    # Hata durumunda o testi atla veya hata bilgisi ekle

        return analysis

    def _analyze_emotion_trends(self, emotion_history: List[Dict]) -> Dict:
        """Duygu geçmişinden trend analizi yapar."""
        trends = {
            "dominant_emotions": [],
            "emotion_changes": {},
            "triggers": [],
            "coping_strategies": []
        }
        
        if not emotion_history:
            return trends
        
        # Son 7 günlük verileri analiz et
        recent_history = emotion_history[-7:]
        
        # Baskın duyguları belirle
        emotion_counts = {}
        for entry in recent_history:
            for emotion, intensity in entry.get("emotions", {}).items():
                if emotion not in emotion_counts:
                    emotion_counts[emotion] = 0
                emotion_counts[emotion] += intensity
        
        trends["dominant_emotions"] = sorted(
            emotion_counts.items(),
            key=lambda x: x[1],
            reverse=True
        )[:3]
        
        # Duygu değişimlerini analiz et
        for i in range(1, len(recent_history)):
            prev_emotions = recent_history[i-1].get("emotions", {})
            curr_emotions = recent_history[i].get("emotions", {})
            
            for emotion in set(prev_emotions.keys()) | set(curr_emotions.keys()):
                if emotion not in trends["emotion_changes"]:
                    trends["emotion_changes"][emotion] = []
                
                prev_intensity = prev_emotions.get(emotion, 0)
                curr_intensity = curr_emotions.get(emotion, 0)
                
                if curr_intensity > prev_intensity:
                    trends["emotion_changes"][emotion].append("artış")
                elif curr_intensity < prev_intensity:
                    trends["emotion_changes"][emotion].append("azalış")
        
        # Tetikleyicileri ve baş etme stratejilerini topla
        for entry in recent_history:
            if "triggers" in entry:
                trends["triggers"].extend(entry["triggers"])
            if "coping_strategies" in entry:
                trends["coping_strategies"].extend(entry["coping_strategies"])
        
        return trends

    def _analyze_schemas(self, test_results: List[Dict]) -> Dict:
        """
        Test sonuçları listesine göre şema analizi yapar.
        Frontend'den gelen 'android_test_results' yapısını kullanır.
        """
        schema_analysis = {}

        # ai_agent'taki tanımlı şema isimleri
        defined_schemas = set(self.schema_descriptions.keys())

        for result in test_results:
            test_type = result.get('test_type')
            test_name = result.get('testName') # Frontend'den gelen test adı (örn. "Terk Edilme / İstikrarsızlık Testi")
            total_score = result.get('totalScore')
            interpretation = result.get('interpretation') # Frontend'den gelen yorum (örn. "Yüksek düzeyde ... Şeması")
            
            # Interpretation alanını kontrol ederek şema testi olup olmadığını anlayalım
            if interpretation and ("Şeması" in interpretation or (test_name and "Şeması" in test_name)):
                 # Şema adını doğrudan interpretation veya testName'den alıp temizleyelim
                 final_schema_name = test_name.replace(" Testi", "").strip() if test_name else test_type

                 if final_schema_name and total_score is not None and interpretation:
                     # severity bilgisini mevcut _determine_severity metodunu kullanarak al
                     severity = self._determine_severity(total_score, test_type, schema=final_schema_name)

                     schema_analysis[final_schema_name] = {
                         "score": total_score,
                         "interpretation": interpretation,
                         "severity": severity,
                         "cbt_techniques": self.schema_descriptions.get(final_schema_name.lower(), {}).get("cbt_techniques", [])
                     }

        return schema_analysis

    def generate_therapeutic_response(self, 
                                   text: str, 
                                   emotion_analysis: Dict,
                                   schema_results: Optional[List[Dict]] = None) -> str:
        """
        Terapötik yanıt oluşturur
        """
        # Prompt oluşturma
        prompt = f"""
        Kullanıcı Mesajı: "{text}"

        Duygu Analizi:
        Mevcut Duygular: {emotion_analysis['current_emotions']}
        
        Test Analizi:
        {emotion_analysis.get('test_analysis', {})}
        
        Duygu Trendleri:
        Baskın Duygular: {emotion_analysis['emotion_trends']['dominant_emotions']}
        Duygu Değişimleri: {emotion_analysis['emotion_trends']['emotion_changes']}
        Tetikleyiciler: {emotion_analysis['emotion_trends']['triggers']}
        """

        if schema_results:
            prompt += f"""
            Şema Analizi Sonuçları:
            {schema_results}
            """

        prompt += """
        Lütfen aşağıdaki yapıda terapötik bir yanıt ver:
        1. Empatik ve anlayışlı bir giriş
        2. Test sonuçlarına dayalı durum değerlendirmesi
        3. Duygu durumu analizi ve yorumu
        4. BDT teknikleriyle düşünce analizi
        5. Pratik öneriler ve egzersizler
        6. Destekleyici kapanış

        Yanıt kuralları:
        - Türkçe yaz
        - Samimi ve destekleyici bir dil kullan
        - Test sonuçlarını öncelikli olarak değerlendir
        - Duygu durumunu test sonuçlarıyla ilişkilendir
        - Madde işaretleri kullanma
        - Düz metin formatında yaz
        """

        response = self.model.generate_content(prompt)
        return response.text

    def generate_recommendations(self,
                               emotion_analysis: Dict,
                               user_preferences: Optional[Dict] = None) -> List[Dict]:
        """
        Kullanıcıya özel öneriler oluşturur
        """
        prompt = f"""
        Duygu Analizi:
        {emotion_analysis['current_emotions']}

        Duygu Trendleri:
        {emotion_analysis['emotion_trends']}

        Lütfen kullanıcıya uygun öneriler hazırla:
        - Meditasyon önerileri
        - Film önerileri
        - Kitap önerileri
        - Günlük aktivite önerileri

        Her öneri için:
        - Başlık
        - Kısa açıklama
        - Neden bu öneriyi yaptığınızın açıklaması

        Yanıt kuralları:
        - Türkçe yaz
        - Her kategori için en fazla 3 öneri ver
        - **Yanıtını yalnızca geçerli bir JSON array olarak döndür. Başka metin ekleme.**
        """

        try:
            response = self.model.generate_content(prompt)
            recommendation_text = response.text.strip()
            
            # Modeldan gelen yanıtın JSON formatında olduğundan emin ol
            if recommendation_text.startswith('[') and recommendation_text.endswith(']'):
                return json.loads(recommendation_text)
            else:
                print(f"⚠️ generate_recommendations: Modeldan beklenen JSON formatı gelmedi. Yanıt: {recommendation_text}")
                return []
        except Exception as e:
            print(f"❌ generate_recommendations sırasında hata: {e}")
            return []

    def generate_psychologist_report(self, 
                                   user_data: Dict,
                                   emotion_history: List[Dict],
                                   test_results: List[Dict]) -> str:
        """
        Psikolog raporu oluşturur
        """
        prompt = f"""
        Kullanıcı Verileri:
        Duygu Geçmişi: {emotion_history}
        Test Sonuçları: {test_results}
        Şema Analizleri: {user_data.get('schema_analyses', [])}

        Lütfen bu verileri kullanarak profesyonel bir psikolog raporu hazırla:
        1. Duygusal durum analizi ve trendler
        2. Şema analizi ve etkileri
        3. Test sonuçlarının değerlendirmesi
        4. Genel öneriler ve yönlendirmeler

        Rapor kuralları:
        - Profesyonel ve teknik bir dil kullan
        - Madde işaretleri kullanma
        - Düz metin formatında yaz
        """

        response = self.model.generate_content(prompt)
        return response.text

    def analyze_test_results(self, test_type: str, test_results: dict, user_history: dict) -> dict:
        """
        Test sonuçlarını analiz eder ve detaylı bir rapor oluşturur.
        Frontend'den gelen test sonuçları ve yorumları kullanılır.
        """
        # Test türüne göre özel analiz yapısı oluştur
        analysis_prompt = self._create_test_analysis_prompt(test_type, test_results, user_history)
        
        try:
            response = self.model.generate_content(analysis_prompt)
            analysis_text = response.text
            
            # Analizden önerileri çıkar
            recommendations = self._extract_recommendations(analysis_text)
            
            # Risk seviyesini belirle
            risk_level = self._assess_risk_level(test_results, test_type)
            
            # Takip noktalarını belirle
            follow_up_points = self._generate_follow_up_points(analysis_text)
            
            # BDT önerilerini oluştur
            cbt_recommendations = self._generate_cbt_recommendations(test_type, test_results)
            
            return {
                "analysis": analysis_text,
                "recommendations": recommendations,
                "risk_level": risk_level,
                "follow_up_points": follow_up_points,
                "cbt_recommendations": cbt_recommendations,
                "schema_interpretation": self._interpret_schemas(test_results)
            }
        except Exception as e:
            raise Exception(f"Test analizi sırasında hata oluştu: {str(e)}")

    def _create_test_analysis_prompt(self, test_type: str, test_results: dict, user_history: dict) -> str:
        """Test analizi için özel prompt oluşturur."""
        # Frontend'den gelen test sonuçlarını ve yorumları kullan
        test_interpretation = test_results.get("interpretation", "")
        test_score = test_results.get("totalScore", 0)
        
        prompt = f"""
        Test Türü: {test_type}
        Test Sonuçları: 
        - Puan: {test_score}
        - Yorum: {test_interpretation}
        - Detaylı Sonuçlar: {test_results}
        
        Kullanıcı Geçmişi: {user_history}
        
        Lütfen aşağıdaki başlıklar altında detaylı bir analiz yapın:
        
        1. Test Sonuçlarının Yorumu:
           - Frontend'den gelen yorumu değerlendir: {test_interpretation}
           - Alt kategorilerdeki durum
           - Önceki sonuçlarla karşılaştırma
        
        2. Şema Analizi:
           - Tespit edilen şemalar
           - Şemaların günlük yaşama etkileri
           - Şema tetikleyicileri
        
        3. BDT Perspektifi:
           - Düşünce-duygu-davranış döngüsü analizi
           - İşlevsel olmayan düşünce kalıpları
           - Alternatif düşünce önerileri
        
        4. Öneriler:
           - Günlük pratikler
           - Farkındalık egzersizleri
           - Kaynak kullanımı
        
        Yanıtınızı Türkçe olarak, empatik ve destekleyici bir tonla verin.
        """
        return prompt

    def _interpret_schemas(self, test_results: dict) -> dict:
        """Test sonuçlarına göre şemaları yorumlar."""
        schema_interpretations = {}
        
        # Frontend'den gelen şema yorumlarını kullan
        if "schema_interpretations" in test_results:
            return test_results["schema_interpretations"]
        
        # Test adından şema anahtarını belirle
        test_name = test_results.get("testName", "")
        schema_key = self._get_schema_key_from_test_name(test_name)
        
        if schema_key and schema_key in self.schema_descriptions:
            score = test_results.get("totalScore", 0)
            interpretation = {
                "description": self.schema_descriptions[schema_key]["description"],
                "cbt_techniques": self.schema_descriptions[schema_key]["cbt_techniques"],
                "intensity": self._calculate_schema_intensity(score, schema_key)
            }
            schema_interpretations[schema_key] = interpretation
        
        return schema_interpretations

    def _get_schema_key_from_test_name(self, test_name: str) -> str:
        """Test adından şema anahtarını belirler."""
        test_name_mapping = {
            "Terk Edilme / İstikrarsızlık Testi": "abandonment",
            "Bağımlılık Testi": "dependency",
            "Duygusal Yoksunluk Testi": "emotional_deprivation",
            "Sosyal İzolasyon / Yabancılık Testi": "social_isolation",
            "Kendini Feda Testi": "self_sacrifice",
            "Boyun Eğicilik Testi": "subjugation",
            "Başarısızlık Testi": "failure",
            "Kusurluluk/Utanç Testi": "shame",
            "Karamsarlık / Felaketçilik Testi": "pessimism",
            "Duygusal Bastırma Testi": "emotional_suppression"
        }
        return test_name_mapping.get(test_name, "")

    def _calculate_schema_intensity(self, score: int, schema_key: str) -> str:
        """Şema yoğunluğunu hesaplar."""
        if schema_key == "shame":
            if score >= 55:
                return "yüksek"
            elif score >= 35:
                return "orta"
            else:
                return "düşük"
        else:
            if score >= 40:
                return "yüksek"
            elif score >= 25:
                return "orta"
            else:
                return "düşük"

    def _generate_cbt_recommendations(self, test_type: str, test_results: dict) -> List[Dict[str, str]]:
        """Test sonuçlarına göre BDT önerileri oluşturur."""
        recommendations = []
        
        # Frontend'den gelen önerileri kullan
        if "cbt_recommendations" in test_results:
            return test_results["cbt_recommendations"]
        
        # Test adından şema anahtarını belirle
        test_name = test_results.get("testName", "")
        schema_key = self._get_schema_key_from_test_name(test_name)
        
        if schema_key and schema_key in self.schema_descriptions:
            score = test_results.get("totalScore", 0)
            if score >= 25:  # Orta ve yüksek şema puanları için öneriler
                for technique in self.schema_descriptions[schema_key]["cbt_techniques"]:
                    recommendations.append({
                        "type": "cbt_technique",
                        "title": technique,
                        "description": f"{schema_key} şeması için {technique} uygulaması",
                        "frequency": "günlük",
                        "duration": "15-20 dakika"
                    })
        
        # Genel BDT önerileri
        recommendations.extend([
            {
                "type": "general",
                "title": "Düşünce Kaydı",
                "description": "Otomatik düşünceleri kaydetme ve analiz etme",
                "frequency": "günlük",
                "duration": "10-15 dakika"
            },
            {
                "type": "general",
                "title": "Farkındalık Meditasyonu",
                "description": "Düşünce ve duyguları gözlemleme pratiği",
                "frequency": "günlük",
                "duration": "10 dakika"
            }
        ])
        
        return recommendations

    def _extract_recommendations(self, analysis_text: str) -> List[str]:
        """Analiz metninden önerileri çıkarır."""
        # TODO: Implement recommendation extraction logic
        return []
        
    def _assess_risk_level(self, test_results: dict, test_type: str) -> str:
        """Test sonuçlarına göre risk seviyesini belirler."""
        score = test_results.get("totalScore", 0)
        
        if test_type == "beck_depresyon":
            if score >= 30:
                return "yüksek"
            elif score >= 17:
                return "orta"
            elif score >= 10:
                return "düşük"
            else:
                return "minimal"
        elif test_type == "beck_anksiyete":
            if score >= 26:
                return "yüksek"
            elif score >= 16:
                return "orta"
            elif score >= 8:
                return "düşük"
            else:
                return "minimal"
        elif test_type == "schema":
            test_name = test_results.get("testName", "")
            schema_key = self._get_schema_key_from_test_name(test_name)
            if schema_key == "shame":
                if score >= 55:
                    return "yüksek"
                elif score >= 35:
                    return "orta"
                else:
                    return "düşük"
            else:
                if score >= 40:
                    return "yüksek"
                elif score >= 25:
                    return "orta"
                else:
                    return "düşük"
        
        return "normal"
        
    def _generate_follow_up_points(self, analysis_text: str) -> List[str]:
        """Analiz metninden takip noktalarını çıkarır."""
        # TODO: Implement follow-up points extraction logic
        return []

    def _determine_severity(self, score: float, test_type: str, schema: Optional[str] = None) -> str:
        """
        Test puanına göre şiddet seviyesini belirler.
        """
        if schema == "shame":
            if score >= 55:
                return "yüksek"
            elif score >= 35:
                return "orta"
            else:
                return "düşük"
        elif test_type == "beck_depresyon":
            if score >= 30:
                return "yüksek"
            elif score >= 17:
                return "orta"
            elif score >= 10:
                return "düşük"
            else:
                return "minimal"
        elif test_type == "beck_anksiyete":
            if score >= 26:
                return "yüksek"
            elif score >= 16:
                return "orta"
            elif score >= 8:
                return "düşük"
            else:
                return "minimal"
        else:  # Diğer şema testleri için
            if score >= 40:
                return "yüksek"
            elif score >= 25:
                return "orta"
            else:
                return "düşük" 