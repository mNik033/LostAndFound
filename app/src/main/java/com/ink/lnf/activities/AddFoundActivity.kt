package com.ink.lnf.activities

import android.app.DatePickerDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ink.lnf.R
import com.ink.lnf.models.Found
import kotlinx.android.synthetic.main.activity_add_item.*
import java.time.LocalDateTime
import java.util.*

class AddFoundActivity : BaseActivity() {

    lateinit var imageView: ImageView

    private val mFireStore = FirebaseFirestore.getInstance()

    private var storageRef = Firebase.storage

    private var uri : Uri? = null

    private lateinit var username : String

    private lateinit var usermobile : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        storageRef = FirebaseStorage.getInstance()

        val db = Firebase.firestore

        val docRef = db.collection("users").document(getCurrentUserID())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    username = document.data?.get("name") as String
                    usermobile = document.data?.get("mobile").toString()
                    idadditemContact.setText(usermobile)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }


        val imageView = findViewById<ImageView>(R.id.addItemImage)

        val galleryimage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imageView.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            }
        )

        imageView.setOnClickListener {
            galleryimage.launch("image/*")
        }

        val tv = findViewById<TextView>(R.id.idadditem)
        tv.text = "Add found item"

        val x = findViewById<ImageView>(R.id.idX)
        x.setOnClickListener {
            finish()
        }

        val datePicker = findViewById<TextInputEditText>(R.id.idadditemDate)

        datePicker.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->
                    val dt = (dayOfMonth.toString() + "-" +
                            (monthOfYear + 1) + "-" + year)
                    datePicker.setText(dt)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        btnSave.setOnClickListener {

            val name = idadditemName.text.toString()
            val location = idadditemLocation.text.toString()
            val date = idadditemDate.text.toString()
            val contact = idadditemContact.text.toString()
            val description = idadditemDesc.text.toString()

            it.hideKeyboard()

            if(validateForm(name,location,date,contact)) {
                showProgressDialog("Uploading information")
                if (uri != null) {
                    storageRef.getReference("images")
                        .child(System.currentTimeMillis().toString())
                        .putFile(uri!!)
                        .addOnSuccessListener { task ->
                            task.metadata!!.reference!!.downloadUrl
                                .addOnSuccessListener {
                                    val image = it.toString()
                                    val foundItemInfo =
                                        Found(getCurrentUserID(), username, image, name,
                                            location, date, contact, description, true)
                                    addFoundItem(foundItemInfo)
                                    hideProgressDialog()
                                    finish()
                                }
                        }
                } else {
                    val foundItemInfo =
                        Found(getCurrentUserID(), username, "", name,
                            location, date, contact, description, true)
                    addFoundItem(foundItemInfo)
                    hideProgressDialog()
                    finish()
                }
            }
        }
    }

    private fun validateForm(name: String, location: String, date: String, contact: String) : Boolean {
        return when {
            TextUtils.isEmpty(name)->{
                showErrorSnackbar("Please enter the name of item lost")
                false
            }
            TextUtils.isEmpty(location)->{
                showErrorSnackbar("Please enter an approximate location")
                false
            }
            TextUtils.isEmpty(date)->{
                showErrorSnackbar("Please enter an approximate date")
                false
            }
            TextUtils.isEmpty(contact)->{
                showErrorSnackbar("Please enter contact information")
                false
            }
            else->{
                true
            }
        }
    }

    private fun addFoundItem(foundItemInfo : Found){
        mFireStore.collection("found")
            .document("${getCurrentUserID()}_${LocalDateTime.now()}")
            .set(foundItemInfo, SetOptions.merge())
        Toast.makeText(this, "Found item uploaded successfully", Toast.LENGTH_LONG).show()
    }
}