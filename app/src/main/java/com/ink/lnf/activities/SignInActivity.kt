package com.ink.lnf.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.ink.lnf.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnSignIn.setOnClickListener(){
            signInRegisteredUser()
        }
    }

    private fun signInRegisteredUser(){

        val email : String = iemail.text.toString()
        val password : String = ipass.text.toString()

        if(validateForm(email, password)){
            showProgressDialog("Please wait...")
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(baseContext, "Signed in successfully!",
                            Toast.LENGTH_LONG).show()
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, task.exception!!.message,
                            Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String) : Boolean {
        return when {
            TextUtils.isEmpty(email)->{
                showErrorSnackbar("Please enter an email")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackbar("Please enter a password")
                false
            }
            else->{
                true
            }
        }
    }
}