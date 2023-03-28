package com.ink.lnf.fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ink.lnf.R
import com.ink.lnf.activities.BaseActivity
import com.ink.lnf.activities.MainActivity
import com.ink.lnf.models.User
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment: Fragment() {

    private lateinit var tv: TextView
    private val pfViewModel : ProfileFragmentViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.firestore

        val name : String? = pfViewModel.name.value
        idProfileName.text = name

        val email : String? = pfViewModel.email.value
        idProfileEmail.text = email

        val mobile : Long? = pfViewModel.mobile.value
        idaddProfilePhone.setText(mobile.toString())

        val image : String? = pfViewModel.image.value
        Glide
            .with(view)
            .load(image)
            .placeholder(R.drawable.ic_baseline_account_circle_24)
            .centerCrop()
            .circleCrop()
            .into(idProfilePic);

        idaddProfilePhone?.setOnFocusChangeListener{ v, hasFocus ->
            idSavefab?.visibility = View.VISIBLE }

        idSavefab.setOnClickListener {
            val mobile = idaddProfilePhone?.text.toString().toLong()

            val data = hashMapOf(
                "mobile" to mobile
            )

            db.collection("users").document(BaseActivity().getCurrentUserID())
                .set(data, SetOptions.merge())
                .addOnSuccessListener {
                    idSavefab?.visibility = View.GONE
                    idaddProfilePhone.clearFocus()
                    Toast.makeText(activity,
                        "Profile information updated successfully!", Toast.LENGTH_LONG)
                }
        }

    }
}