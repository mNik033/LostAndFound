package com.ink.lnf.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.ink.lnf.R

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val btnSignInIntroActivity = findViewById<Button>(R.id.buttonSignInIntro)
        btnSignInIntroActivity.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        val btnSignUpIntroActivity = findViewById<Button>(R.id.buttonSignUpIntro)
        btnSignUpIntroActivity.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}