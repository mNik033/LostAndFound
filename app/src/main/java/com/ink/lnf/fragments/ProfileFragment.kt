package com.ink.lnf.fragments

import android.content.ContentValues
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ink.lnf.R
import com.ink.lnf.activities.AddFoundActivity
import com.ink.lnf.activities.BaseActivity
import com.ink.lnf.activities.IntroActivity
import com.ink.lnf.activities.MainActivity
import com.ink.lnf.models.User
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment: Fragment() {

    private lateinit var tv: TextView
    private val pfViewModel : ProfileFragmentViewModel by activityViewModels()
    private var uri : Uri? = null
    private var storageRef = Firebase.storage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.firestore

        storageRef = FirebaseStorage.getInstance()

        val name : String? = pfViewModel.name.value
        idProfileName.text = name

        val email : String? = pfViewModel.email.value
        idProfileEmail.text = email

        val mobile : Long? = pfViewModel.mobile.value
        idaddProfilePhone.setText(mobile.toString())

        var image : String? = pfViewModel.image.value
        Glide
            .with(view)
            .load(image)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .centerCrop()
            .circleCrop()
            .into(idProfilePic);

        val galleryimage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                if (it != null) {
                    uri = it
                    pfViewModel.setImage(it.toString())
                }
            }
        )

        val docRef = db.collection("users").document(BaseActivity().getCurrentUserID())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val oldimg = document.data?.get("image") as String
                    if(oldimg != image){
                        idSavefab.visibility = View.VISIBLE
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        idaddProfilePhone?.setOnFocusChangeListener{ v, hasFocus ->
            idSavefab?.visibility = View.VISIBLE }

        idProfilePic.setOnClickListener{
            galleryimage.launch("image/*")
            idSavefab?.visibility = View.VISIBLE
        }

        idSavefab.setOnClickListener {

            Toast.makeText( context,"Updating info", Toast.LENGTH_SHORT).show()

            val mobile = idaddProfilePhone?.text.toString().toLong()

            if (uri != null) {
                storageRef.getReference("images/pfp")
                    .child(name!!)
                    .putFile(uri!!)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener {
                                image = it.toString()
                                db.collection("users")
                                    .document(BaseActivity().getCurrentUserID())
                                    .set(
                                        hashMapOf("image" to image, "mobile" to mobile),
                                        SetOptions.merge()
                                    )
                                    .addOnSuccessListener {
                                        idaddProfilePhone.clearFocus()
                                        idSavefab.visibility = View.GONE
                                        Toast.makeText(
                                            activity,
                                            "Profile information updated successfully!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                            }
                    }
            } else {
                db.collection("users")
                    .document(BaseActivity().getCurrentUserID())
                    .set(
                        hashMapOf("mobile" to mobile),
                        SetOptions.merge()
                    )
                    .addOnSuccessListener {
                        idaddProfilePhone.clearFocus()
                        idSavefab.visibility = View.GONE
                        Toast.makeText(
                            activity,
                            "Profile information updated successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }

        idBtnSignOut.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(context, "You've been signed out", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, IntroActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        idSavefab.visibility = View.INVISIBLE
    }
}