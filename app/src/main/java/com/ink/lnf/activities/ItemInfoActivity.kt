package com.ink.lnf.activities

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ink.lnf.R
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_item_info.*


class ItemInfoActivity : AppCompatActivity() {

    private lateinit var pfp : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info)

        val toolbar : MaterialToolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val db = Firebase.firestore

        val LocationV : TextView = findViewById(R.id.idItemLocationI)
        val DateV : TextView = findViewById(R.id.idItemDateI)
        val UserNameV : TextView = findViewById(R.id.idItemUNameI)
        val ImageV: ImageView = findViewById(R.id.idItemImageI)
        val DescriptionV : TextView = findViewById(R.id.idItemDescI)
        val ContactV : TextView = findViewById(R.id.idItemContactI)
        val ProfileImgV: ImageView = findViewById(R.id.idProfileImageI)

        val bundle: Bundle? = intent.extras

        val useruid = bundle!!.getString("UserUID")
        val name = bundle.getString("Name")
        val location = bundle.getString("Location")
        val date = bundle.getString("Date")
        val username = bundle.getString("UserName")
        val image = bundle.getString("Image")
        val desc = bundle.getString("Description")
        val contact = bundle.getString("Contact")


        val docRef = db.collection("users").document(useruid!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    pfp = document.data?.get("image") as String
                    Glide
                        .with(ProfileImgV)
                        .load(pfp)
                        .placeholder(R.drawable.ic_outline_account_circle_24)
                        .centerCrop()
                        .circleCrop()
                        .into(ProfileImgV);
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        toolbar.title = name
        LocationV.text = location
        DateV.text = date
        UserNameV.text = username
        DescriptionV.text = desc
        ContactV.text = contact

        val validNumber = Regex("^[+]?[0-9]{10}$")
        val validNumber2 = Regex("^[+]"+"91"+"[+]?[0-9]{10}$")

        if (contact!!.matches(validNumber
            ) or contact.matches(validNumber2)) {
            ContactV.setOnClickListener {

                val call: Uri = Uri.parse("tel:$contact")
                val intent = Intent(Intent.ACTION_DIAL, call)
                startActivity(intent)
            }
        }

        Glide
            .with(this)
            .load(image)
            .placeholder(R.drawable.ic_baseline_image_24)
            .centerCrop()
            .into(ImageV);
    }
}