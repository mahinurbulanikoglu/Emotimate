package com.mahinurbulanikoglu.emotimate

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahinurbulanikoglu.emotimate.SignupActivity
import com.mahinurbulanikoglu.emotimate.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Thread.sleep(3000)
        //installSplashScreen()

        binding = ActivitySigninBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            if (user != null && user.isEmailVerified) {
                                // Kullanıcı doğrulandıysa HomePageActivity'ye yönlendir
                                val intent = Intent(this, HomePageActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // Kullanıcı doğrulama yapmadıysa hata mesajı göster
                                Toast.makeText(
                                    this,
                                    "Please verify your email before signing in!",
                                    Toast.LENGTH_LONG
                                ).show()
                                firebaseAuth.signOut() // Kullanıcı çıkış yapsın
                            }
                        } else {
                            Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val user = firebaseAuth.currentUser
        if (user != null) {
            if (user.isEmailVerified) {
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
                finish() // SigninActivity'yi kapat
            } else {
                Toast.makeText(
                    this,
                    "Please verify your email before signing in!",
                    Toast.LENGTH_LONG
                ).show()
                firebaseAuth.signOut() // Kullanıcı doğrulama yapmadıysa oturumu kapat
            }
        }
    }
}