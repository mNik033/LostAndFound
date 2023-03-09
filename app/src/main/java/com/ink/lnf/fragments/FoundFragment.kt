package com.ink.lnf.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ink.lnf.R
import com.ink.lnf.activities.AddFoundActivity
import kotlinx.android.synthetic.main.fragment_found.*

class FoundFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_found, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idfoundfab?.setOnClickListener {
            val intent = Intent(getActivity(), AddFoundActivity::class.java)
            getActivity()?.startActivity(intent)
        }
            idrecyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                // set adapter
                adapter = RecyclerAdapter(requireActivity(), getItemsList())
            }
        }
    // test data for RecyclerView
    private fun getItemsList() : ArrayList<String> {
        val list = ArrayList<String>()
        for(i in 1..15) {
            list.add("Found Item $i")
        }
        return list
    }

}