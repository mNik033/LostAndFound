package com.ink.lnf.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ink.lnf.R
import kotlinx.android.synthetic.main.card_view.view.*

class RecyclerAdapter(val context: Context, val items: ArrayList<String>) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                        return ViewHolder(
                                LayoutInflater.from(context).inflate(
                                        R.layout.card_view,
                                        parent,
                                        false
                                )
                        )

                }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val item = items.get(position)
                holder.tvItem.text = item
        }

        override fun getItemCount(): Int {
                return items.size
        }

        class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
                var tvItem = view.idItemName
                var cardViewItem = view.idCardView
        }
}