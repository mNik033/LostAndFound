package com.ink.lnf.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ink.lnf.R

class AddLostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        var tv = findViewById<TextView>(R.id.idadditem)
        tv.text = "Add lost item"
    }
}