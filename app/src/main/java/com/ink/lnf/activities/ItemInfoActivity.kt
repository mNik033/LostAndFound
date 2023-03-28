package com.ink.lnf.activities

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ink.lnf.R
import kotlinx.android.synthetic.main.activity_item_info.*

class ItemInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info)

        val toolbar : MaterialToolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val LocationV : TextView = findViewById(R.id.idItemLocationI)
        val DateV : TextView = findViewById(R.id.idItemDateI)
        val UserNameV : TextView = findViewById(R.id.idItemUNameI)
        val ImageV: ImageView = findViewById(R.id.idItemImageI)
        val DescriptionV : TextView = findViewById(R.id.idItemDescI)
        val ContactV : TextView = findViewById(R.id.idItemContactI)

        val bundle: Bundle? = intent.extras

        val useruid = bundle!!.getString("UserUID")
        val name = bundle.getString("Name")
        val location = bundle.getString("Location")
        val date = bundle.getString("Date")
        val username = bundle.getString("UserName")
        val image = bundle.getString("Image")
        val desc = bundle.getString("Description")
        val contact = bundle.getString("Contact")

        toolbar.title = name
        LocationV.text = location
        DateV.text = date
        UserNameV.text = username
        DescriptionV.text = desc
        ContactV.text = contact

        Glide
            .with(this)
            .load(image)
            .placeholder(R.drawable.ic_baseline_image_24)
            .centerCrop()
            .into(ImageV);
    }
}