import firebase_admin
from firebase_admin import credentials, db, firestore
from typing import Dict, List, Optional
from datetime import datetime, timedelta
import os
from google.cloud.firestore_v1 import SERVER_TIMESTAMP

class FirebaseService:
    def __init__(self):
        try:
            # Firebase Admin SDK'yı başlat
            cred_path = os.getenv("FIREBASE_CREDENTIAL_PATH", "firebase-adminsdk.json")
            cred = credentials.Certificate(cred_path)
            
            # Eğer uygulama zaten başlatılmışsa, yeni bir uygulama oluşturma
            try:
                firebase_admin.get_app()
            except ValueError:
                firebase_admin.initialize_app(cred, {
                    'databaseURL': 'https://emotimate-6a03e-default-rtdb.firebaseio.com/'
                })
            
            self.db = db.reference()
            self.firestore_db = firestore.client()
        except Exception as e:
            print(f"Firebase başlatma hatası: {e}")
            raise

    async def save_chat_message(self, userId: str, message: Dict) -> bool:
        try:
            print(f"[save_chat_message] userId: {userId}, message: {message}")
            chat_ref = self.db.child('chats')
            chat_ref.push({
                'userId': userId,
                'text': message['text'],
                'isUser': message['isUser'],
                'emotion': message.get('emotion', ''),
                'timestamp': datetime.now().isoformat(),
                'analysis': message.get('analysis', ''),
                'emotion_analysis': message.get('emotion_analysis', {}),
                'suggestions': message.get('suggestions', [])
            })
            print("[save_chat_message] Message saved successfully.")
            return True
        except Exception as e:
            print(f"[save_chat_message] Error: {e}")
            return False

    async def get_chat_history(self, userId: str, limit: int = 50) -> List[Dict]:
        try:
            print(f"[get_chat_history] userId: {userId}")
            chat_ref = self.db.child('chats')
            all_messages = chat_ref.order_by_child('userId').equal_to(userId).get()
            print(f"[get_chat_history] all_messages: {all_messages}")
            if not all_messages:
                return []
            messages = list(all_messages.values())
            messages.sort(key=lambda x: x.get('timestamp', ''), reverse=True)
            print(f"[get_chat_history] messages (sorted): {messages[:limit]}")
            return messages[:limit]
        except Exception as e:
            print(f"[get_chat_history] Error: {e}")
            return []

    async def save_mood_entry(self, userId: str, mood_data: Dict) -> bool:
        try:
            mood_ref = self.firestore_db.collection('feelings')
            current_time = datetime.now()
            
            # Timestamp'i Firestore formatında kaydet
            mood_data_to_save = {
                'userId': userId,
                'mood': mood_data['mood'],
                'comment': mood_data.get('comment', ''),
                'timestamp': current_time,  # Firestore datetime objesi olarak kaydet
                'activities': mood_data.get('activities', []),
                'triggers': mood_data.get('triggers', []),
                'emotion_analysis': mood_data.get('emotion_analysis', {})
            }
            
            # Veriyi kaydet
            doc_ref = mood_ref.add(mood_data_to_save)
            print(f"✅ Mood entry saved successfully for user {userId} with ID: {doc_ref.id}")
            return True
        except Exception as e:
            print(f"❌ Error saving mood entry: {e}")
            return False

    async def get_mood_history(self, userId: str, days: int = 7) -> List[Dict]:
        try:
            print(f"🔍 get_mood_history çağrıldı - userId: {userId}")
            mood_ref = self.firestore_db.collection('feelings')
            start_date = datetime.now() - timedelta(days=days)
            print(f"📅 start_date: {start_date}")
            
            # Firestore sorgusu için timestamp'i doğru formatta ayarla
            query = mood_ref.where('userId', '==', userId)\
                          .where('timestamp', '>=', start_date)\
                          .order_by('timestamp', direction=firestore.Query.DESCENDING)
            
            print("🔎 Sorgu oluşturuldu, veriler getiriliyor...")
            docs = query.get()
            print(f"📊 Bulunan döküman sayısı: {len(docs)}")

            if not docs:
                print("⚠️ No mood entries found for the specified period")
                return []

            results = []
            for doc in docs:
                try:
                    data = doc.to_dict()
                    if data:
                        # Timestamp'i ISO formatına çevir
                        if 'timestamp' in data:
                            if isinstance(data['timestamp'], datetime):
                                data['timestamp'] = data['timestamp'].isoformat()
                            elif hasattr(data['timestamp'], 'isoformat'):
                                data['timestamp'] = data['timestamp'].isoformat()
                        results.append(data)
                except Exception as e:
                    print(f"⚠️ Error processing document {doc.id}: {e}")
                    continue

            print(f"📝 Processed {len(results)} mood entries")
            return results
        except Exception as e:
            print(f"❌ Hata (get_mood_history): {str(e)}")
            return []

    async def get_mood_stats(self, userId: str, days: int = 7) -> Dict[str, int]:
        try:
            moods = await self.get_mood_history(userId, days)
            stats = {}
            for mood in moods:
                mood_type = mood.get('mood', 'unknown')
                stats[mood_type] = stats.get(mood_type, 0) + 1
            return stats
        except Exception as e:
            print(f"Error getting mood stats: {e}")
            return {}

    async def save_user_preferences(self, userId: str, preferences: Dict) -> bool:
        try:
            user_ref = self.db.child('users').child(userId)
            user_ref.update({
                'preferences': preferences,
                'updatedAt': datetime.now().isoformat()
            })
            return True
        except Exception as e:
            print(f"Error saving user preferences: {e}")
            return False

    async def get_user_preferences(self, userId: str) -> Optional[Dict]:
        try:
            user_ref = self.db.child('users').child(userId)
            user_data = user_ref.get()
            if user_data:
                return user_data.get('preferences', {})
            return None
        except Exception as e:
            print(f"Error getting user preferences: {e}")
            return None

    async def save_test_analysis(self, userId: str, analysis_data: dict) -> bool:
        try:
            test_ref = self.db.child('test_analyses').child(userId)
            test_ref.push({
                **analysis_data,
                'timestamp': datetime.now().isoformat()
            })
            return True
        except Exception as e:
            print(f"Error saving test analysis: {e}")
            return False

    async def get_test_result_from_android_from_android(self, userId: str) -> List[Dict]:
        try:
            test_ref = self.db.child('test_analyses').child(userId)
            test_data = test_ref.get()
            if not test_data:
                return []
            return list(test_data.values())
        except Exception as e:
            print(f"Error getting test results: {e}")
            return []

    async def get_test_result_from_android_from_android(self, userId: str) -> List[Dict]:
        """
        Android uygulamasının kaydettiği test sonuçlarını Firebase Realtime Database'den çeker.
        Path: /users/{userId}/test_results/{test_type}/{test_result_id}/answers
        """
        try:
            test_results_ref = self.db.child('users').child(userId).child('test_results')
            test_types = test_results_ref.get()
            if not test_types:
                return []
            results = []
            for test_type, test_entries in test_types.items():
                # panas_test gibi doğrudan answers içeren testler
                if isinstance(test_entries, dict) and 'answers' in test_entries:
                    result = {
                        "test_type": test_type,
                        "test_id": None,
                        **test_entries
                    }
                    results.append(result)
                # Diğer testler (her biri bir dict)
                elif isinstance(test_entries, dict):
                    for test_id, test_data in test_entries.items():
                        if isinstance(test_data, dict):
                            result = {
                                "test_type": test_type,
                                "test_id": test_id,
                                **test_data
                            }
                            results.append(result)
            return results
        except Exception as e:
            print(f"Error getting test results from android path: {e}")
            return []

    async def get_user_data(self, userId: str) -> Dict:
        try:
            # Firestore'dan kullanıcı verilerini al
            user_ref = self.firestore_db.collection('users').document(userId)
            user_doc = user_ref.get()
            
            if not user_doc.exists:
                return {}
            
            user_data = user_doc.to_dict()
            
            # Duygu durumu geçmişini al (Firestore)
            mood_history = await self.get_mood_history(userId)
            user_data['mood_history'] = mood_history
            
            # Test sonuçlarını al (Realtime Database)
            test_results = await self.get_test_result_from_android_from_android(userId)
            user_data['test_results'] = test_results
            
            return user_data
        except Exception as e:
            print(f"Error getting user data: {e}")
            return {}

    async def save_psychologist_report(self, userId: str, report_data: dict) -> bool:
        try:
            doc_ref = self.db.collection('users').document(userId).collection('psychologist_reports')
            doc_ref.add({
                **report_data,
                'timestamp': datetime.now().isoformat()
            })
            return True
        except Exception as e:
            print(f"Error saving psychologist report: {e}")
            return False

    async def save_recommendations(self, userId: str, recommendation_data: dict) -> bool:
        try:
            doc_ref = self.db.collection('users').document(userId).collection('recommendations')
            doc_ref.add({
                **recommendation_data,
                'timestamp': datetime.now().isoformat()
            })
            return True
        except Exception as e:
            print(f"Error saving recommendations: {e}")
            return False

    async def get_schema_analyses(self, userId: str) -> List[Dict]:
        try:
            schema_ref = self.db.collection('users').document(userId).collection('schema_analyses')
            schema_docs = await schema_ref.get()
            return [doc.to_dict() for doc in schema_docs]
        except Exception as e:
            print(f"Error getting schema analyses: {e}")
            return []

    async def get_recent_recommendations(self, userId: str, limit: int = 5) -> List[Dict]:
        try:
            rec_ref = self.db.collection('users').document(userId).collection('recommendations')
            query = rec_ref.order_by('timestamp', direction=firestore.Query.DESCENDING).limit(limit)
            docs = query.get()
            return [doc.to_dict() for doc in docs]
        except Exception as e:
            print(f"Error getting recent recommendations: {e}")
            return []

    async def get_emotion_trends(self, userId: str, days: int = 30) -> Dict:
        try:
            moods = await self.get_mood_history(userId, days)
            trends = {
                'emotions': {},
                'triggers': {},
                'activities': {}
            }
            
            for mood in moods:
                # Duygu analizlerini topla
                emotion_analysis = mood.get('emotion_analysis', {})
                for emotion, intensity in emotion_analysis.items():
                    if emotion not in trends['emotions']:
                        trends['emotions'][emotion] = []
                    trends['emotions'][emotion].append(intensity)
                
                # Tetikleyicileri topla
                for trigger in mood.get('triggers', []):
                    trends['triggers'][trigger] = trends['triggers'].get(trigger, 0) + 1
                
                # Aktiviteleri topla
                for activity in mood.get('activities', []):
                    trends['activities'][activity] = trends['activities'].get(activity, 0) + 1
            
            return trends
        except Exception as e:
            print(f"Error getting emotion trends: {e}")
            return {}

    