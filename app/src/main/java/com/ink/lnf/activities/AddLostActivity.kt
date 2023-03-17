package com.ink.lnf.activities

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.firebase.ui.auth.data.model.User.getUser
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ink.lnf.R
import com.ink.lnf.models.Lost
import com.ink.lnf.models.User
import kotlinx.android.synthetic.main.activity_add_item.*
import java.time.LocalDateTime

class AddLostActivity : BaseActivity() {

    lateinit var imageView: ImageView

    private val mFireStore = FirebaseFirestore.getInstance()

    private var storageRef = Firebase.storage

    private lateinit var uri : Uri

    /*private val contract = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageView.setImageURI(it)
        addPic.setText("Change Picture")
    } */

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

        /* imageView = findViewById(R.id.addItemImage)
        imageView.setOnClickListener {
            contract.launch("image/oo*")
        } */

        val tv = findViewById<TextView>(R.id.idadditem)
        tv.text = "Add lost item"

        val x = findViewById<ImageView>(R.id.idX)
        x.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            Toast.makeText(this, "Please wait while the information is being uploaded to the cloud", Toast.LENGTH_LONG).show()
            // TODO: Use a loading animation here instead
            storageRef.getReference("images").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener {task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val image = it.toString()
                            val name = idadditemName.text.toString()
                            val location = idadditemLocation.text.toString()
                            val date = idadditemDate.text.toString()
                            val contact = idadditemContact.text.toString()
                            val description = idadditemDesc.text.toString()
                            val lostItemInfo = Lost(getCurrentUserID(), image, name, location, date, contact, description)
                            addLostItem(lostItemInfo)
                            finish()
                        }
                }
        }
    }

    private fun addLostItem(lostItemInfo : Lost){
        mFireStore.collection("lost")
            .document("${getCurrentUserID()}_${LocalDateTime.now()}")
            .set(lostItemInfo, SetOptions.merge())
        Toast.makeText(this, "Lost item uploaded successfully", Toast.LENGTH_LONG).show()
    }
}