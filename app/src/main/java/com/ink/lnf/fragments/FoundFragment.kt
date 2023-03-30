package com.ink.lnf.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ink.lnf.R
import com.ink.lnf.activities.AddFoundActivity
import com.ink.lnf.activities.ItemInfoActivity
import com.ink.lnf.models.Lost
import kotlinx.android.synthetic.main.fragment_found.*
import kotlinx.android.synthetic.main.fragment_found.idrecyclerView
import kotlinx.android.synthetic.main.fragment_lost.*

class FoundFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_found, container, false)

    private lateinit var foundList: ArrayList<Lost>
    private var db = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idfoundfab?.setOnClickListener {
            val intent = Intent(getActivity(), AddFoundActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        idrecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            (layoutManager as LinearLayoutManager).setReverseLayout(true)
            (layoutManager as LinearLayoutManager).setStackFromEnd(true)
        }

        foundList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("found").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    for(data in it.documents){
                        val foundItem : Lost? = data.toObject(Lost::class.java)
                        if(foundItem != null && foundItem.display==true){
                            foundList.add(foundItem)
                        }
                    }
                    val adapter = RecyclerAdapter(foundList)
                    idrecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : RecyclerAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(activity, ItemInfoActivity::class.java)
                            intent.putExtra("UserUID", foundList[position].useruid)
                            intent.putExtra("Name", foundList[position].name)
                            intent.putExtra("Location", foundList[position].location)
                            intent.putExtra("Date", foundList[position].date)
                            intent.putExtra("UserName", foundList[position].username)
                            intent.putExtra("Image", foundList[position].image)
                            intent.putExtra("Description", foundList[position].description)
                            intent.putExtra("Contact", foundList[position].contact)
                            getActivity()?.startActivity(intent)
                        }

                    })
                }
            }
            .addOnFailureListener{
                Toast.makeText(activity, it.toString(), Toast.LENGTH_LONG).show()
            }
    }
}