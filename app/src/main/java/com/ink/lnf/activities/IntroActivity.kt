package com.ink.lnf.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.ink.lnf.R

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        var currentUserID = BaseActivity().getCurrentUserID()
        if (currentUserID.isNotEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val btnSignInIntroActivity = findViewById<TextView>(R.id.buttonSignInIntro)
        btnSignInIntroActivity.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        val btnSignUpIntroActivity = findViewById<TextView>(R.id.buttonSignUpIntro)
        btnSignUpIntroActivity.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}