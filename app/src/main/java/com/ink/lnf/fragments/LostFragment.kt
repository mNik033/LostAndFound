package com.ink.lnf.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ink.lnf.R
import com.ink.lnf.activities.AddLostActivity
import kotlinx.android.synthetic.main.fragment_lost.*

class LostFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_lost, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        idlostfab?.setOnClickListener{
            val intent = Intent (getActivity(), AddLostActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }
}