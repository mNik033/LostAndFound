package com.ink.lnf.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ink.lnf.R
import com.ink.lnf.firebase.FirestoreClass
import com.ink.lnf.models.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignUp.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser(){
        val name: String = uname.text.toString()
        val email: String = uemail.text.toString()
        val password: String = upass.text.toString()
        if(validateForm(name, email, password)){
            showProgressDialog("Please wait...")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    hideProgressDialog()
                    if(task.isSuccessful){
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val firebaseEmail = firebaseUser!!.email!!
                        val user = User(firebaseUser.uid, name, firebaseEmail)
                        FirestoreClass().registerUser(this, user)
                        Toast.makeText(this, "$name , you've registered successfully!",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else{
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
            )
        }
    }

    private fun validateForm(name: String, email: String, password: String) : Boolean {
     return when {
        TextUtils.isEmpty(name)->{
            showErrorSnackbar("Please enter your name")
            false
        }
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