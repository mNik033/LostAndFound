package com.ink.lnf.activities

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ink.lnf.R
import com.ink.lnf.models.Found
import kotlinx.android.synthetic.main.activity_add_item.*
import java.time.LocalDateTime

class AddFoundActivity : BaseActivity() {

    lateinit var imageView: ImageView

    private val mFireStore = FirebaseFirestore.getInstance()

    private var storageRef = Firebase.storage

    private var uri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        storageRef = FirebaseStorage.getInstance()

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

        btnSave.setOnClickListener {
            showProgressDialog("Uploading iniformation")
            if (uri != null) {
                storageRef.getReference("images").child(System.currentTimeMillis().toString())
                    .putFile(uri!!)
                    .addOnSuccessListener { task ->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener {
                                val image = it.toString()
                                val name = idadditemName.text.toString()
                                val location = idadditemLocation.text.toString()
                                val date = idadditemDate.text.toString()
                                val contact = idadditemContact.text.toString()
                                val description = idadditemDesc.text.toString()
                                val foundItemInfo = Found(
                                    getCurrentUserID(), image, name, location, date, contact, description
                                )
                                addFoundItem(foundItemInfo)
                                finish()
                            }
                    }
            } else {
                val name = idadditemName.text.toString()
                val location = idadditemLocation.text.toString()
                val date = idadditemDate.text.toString()
                val contact = idadditemContact.text.toString()
                val description = idadditemDesc.text.toString()
                val foundItemInfo =
                    Found(getCurrentUserID(), "", name, location, date, contact, description)
                addFoundItem(foundItemInfo)
                finish()
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