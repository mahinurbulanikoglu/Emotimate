import firebase_admin
from firebase_admin import credentials, firestore
from typing import Dict, List, Optional
from datetime import datetime, timedelta

class FirebaseService:
    def __init__(self):
        # Firebase Admin SDK'yı başlat
        cred = credentials.Certificate("firebase-adminsdk.json")
        firebase_admin.initialize_app(cred)
        self.db = firestore.client()

    async def save_chat_message(self, user_id: str, message: Dict) -> bool:
        try:
            chat_ref = self.db.collection('chats')
            chat_ref.add({
                'userId': user_id,
                'text': message['text'],
                'isUser': message['isUser'],
                'emotion': message.get('emotion', ''),
                'timestamp': firestore.SERVER_TIMESTAMP,
                'analysis': message.get('analysis', ''),
                'suggestions': message.get('suggestions', [])
            })
            return True
        except Exception as e:
            print(f"Error saving chat message: {e}")
            return False

    async def get_chat_history(self, user_id: str, limit: int = 50) -> List[Dict]:
        try:
            chat_ref = self.db.collection('chats')
            query = chat_ref.where('userId', '==', user_id)\
                          .order_by('timestamp', direction=firestore.Query.DESCENDING)\
                          .limit(limit)
            
            docs = query.get()
            return [doc.to_dict() for doc in docs]
        except Exception as e:
            print(f"Error getting chat history: {e}")
            return []

    async def save_mood_entry(self, user_id: str, mood_data: Dict) -> bool:
        try:
            mood_ref = self.db.collection('moods')
            mood_ref.add({
                'userId': user_id,
                'mood': mood_data['mood'],
                'note': mood_data.get('note', ''),
                'timestamp': firestore.SERVER_TIMESTAMP,
                'activities': mood_data.get('activities', []),
                'triggers': mood_data.get('triggers', [])
            })
            return True
        except Exception as e:
            print(f"Error saving mood entry: {e}")
            return False

    async def get_mood_history(self, user_id: str, days: int = 7) -> List[Dict]:
        try:
            mood_ref = self.db.collection('moods')
            start_date = datetime.now() - timedelta(days=days)
            
            query = mood_ref.where('userId', '==', user_id)\
                          .where('timestamp', '>=', start_date)\
                          .order_by('timestamp', direction=firestore.Query.DESCENDING)
            
            docs = query.get()
            return [doc.to_dict() for doc in docs]
        except Exception as e:
            print(f"Error getting mood history: {e}")
            return []

    async def get_mood_stats(self, user_id: str, days: int = 7) -> Dict[str, int]:
        try:
            moods = await self.get_mood_history(user_id, days)
            stats = {}
            for mood in moods:
                mood_type = mood.get('mood', 'unknown')
                stats[mood_type] = stats.get(mood_type, 0) + 1
            return stats
        except Exception as e:
            print(f"Error getting mood stats: {e}")
            return {}

    async def save_user_preferences(self, user_id: str, preferences: Dict) -> bool:
        try:
            user_ref = self.db.collection('users').document(user_id)
            user_ref.set({
                'preferences': preferences,
                'updatedAt': firestore.SERVER_TIMESTAMP
            }, merge=True)
            return True
        except Exception as e:
            print(f"Error saving user preferences: {e}")
            return False

    async def get_user_preferences(self, user_id: str) -> Optional[Dict]:
        try:
            user_ref = self.db.collection('users').document(user_id)
            doc = user_ref.get()
            if doc.exists:
                return doc.to_dict().get('preferences', {})
            return None
        except Exception as e:
            print(f"Error getting user preferences: {e}")
            return None

    async def save_therapy_report(self, user_id: str, report: Dict):
        """Save therapy report to Firebase"""
        try:
            report_ref = self.db.collection('users').document(user_id).collection('therapy_reports')
            await report_ref.add(report)
            return True
        except Exception as e:
            print(f"Error saving therapy report: {e}")
            return False

    async def get_therapy_report(self, user_id: str) -> Optional[Dict]:
        """Get latest therapy report from Firebase"""
        try:
            report_ref = self.db.collection('users').document(user_id).collection('therapy_reports')
            reports = await report_ref.order_by('generated_at', direction='DESCENDING').limit(1).get()
            
            if not reports:
                return None
                
            return reports[0].to_dict()
        except Exception as e:
            print(f"Error getting therapy report: {e}")
            return None

    async def save_test_result(self, user_id: str, test_result: Dict):
        """Save test result to Firebase"""
        try:
            test_ref = self.db.collection('users').document(user_id).collection('test_results')
            await test_ref.add(test_result)
            return True
        except Exception as e:
            print(f"Error saving test result: {e}")
            return False

    async def get_user_test_results(self, user_id: str, limit: int = 10) -> List[Dict]:
        """Get user's recent test results"""
        try:
            test_ref = self.db.collection('users').document(user_id).collection('test_results')
            tests = await test_ref.order_by('taken_at', direction='DESCENDING').limit(limit).get()
            
            return [test.to_dict() for test in tests]
        except Exception as e:
            print(f"Error getting test results: {e}")
            return []

    