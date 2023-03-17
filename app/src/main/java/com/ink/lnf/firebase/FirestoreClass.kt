package com.ink.lnf.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.ink.lnf.activities.BaseActivity
import com.ink.lnf.activities.MainActivity
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

    fun signInUser(activity: Activity){
        mFireStore.collection("users")
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)!!
                when(activity){
                    is SignInActivity ->{}
                    is MainActivity -> {
                        activity.updateUserDetails(loggedInUser)
                    }
                }
            }
            .addOnFailureListener { e->
                Log.e(activity.javaClass.simpleName, "Error writing document")
            }
    }

}