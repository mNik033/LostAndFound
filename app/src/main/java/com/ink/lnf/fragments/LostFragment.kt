package com.ink.lnf.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ink.lnf.R
import com.ink.lnf.activities.AddLostActivity
import kotlinx.android.synthetic.main.fragment_lost.*

class LostFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_lost, container, false)

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
            // set the custom adapter to the RecyclerView
            adapter = RecyclerAdapter(requireActivity(), getItemsList())
        }
    }
    // test data for RecyclerView
    private fun getItemsList() : ArrayList<String> {
        val list = ArrayList<String>()
        for(i in 1..15) {
            list.add("Lost Item $i")
        }
        return list
    }
}