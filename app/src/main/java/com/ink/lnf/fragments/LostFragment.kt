package com.ink.lnf.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ink.lnf.R
import com.ink.lnf.activities.AddLostActivity
import com.ink.lnf.models.Lost
import kotlinx.android.synthetic.main.fragment_lost.*

class LostFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_lost, container, false)

    private lateinit var lostList: ArrayList<Lost>
    private var db = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idlostfab?.setOnClickListener {
            val intent = Intent (getActivity(), AddLostActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        /* icon color -> black
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.getWindow()?.getDecorView()?.getWindowInsetsController()
                ?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
        } */

        idrecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
        }

        lostList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("lost").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for(data in it.documents){
                        val lostItem : Lost? = data.toObject(Lost::class.java)
                        if(lostItem != null){
                            lostList.add(lostItem)
                        }
                    }
                    idrecyclerView.apply {
                        adapter = RecyclerAdapter(lostList)
                    }
                }
            }
            .addOnFailureListener{
                Toast.makeText(activity, it.toString(), Toast.LENGTH_LONG).show()
            }
    }
}