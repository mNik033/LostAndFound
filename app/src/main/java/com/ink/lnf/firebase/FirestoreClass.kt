package com.ink.lnf.firebase

import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.ink.lnf.R
import com.ink.lnf.activities.BaseActivity
import com.ink.lnf.activities.SignInActivity
import com.ink.lnf.activities.SignUpActivity
import com.ink.lnf.models.User

class FirestoreClass : BaseActivity() {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User){
        mFireStore.collection("users")
            .document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
    }

    fun signInUser(activity: SignInActivity){
        mFireStore.collection("users")
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)
            }
            .addOnFailureListener { e->
                Log.e(activity.javaClass.simpleName, "Error writing document")
            }
    }

}