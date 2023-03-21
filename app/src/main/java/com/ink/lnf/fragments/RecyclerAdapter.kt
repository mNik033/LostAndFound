package com.ink.lnf.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ink.lnf.R
import com.ink.lnf.models.Lost

class RecyclerAdapter(private val List : ArrayList<Lost>):
        RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>(){
        class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
                val Name : TextView = itemView.findViewById(R.id.idItemName)
                val Location : TextView = itemView.findViewById(R.id.idItemLocation)
                val Date : TextView = itemView.findViewById(R.id.idItemDate)
                val userName : TextView = itemView.findViewById(R.id.idItemUName)
                val Image: ImageView = itemView.findViewById(R.id.idItemImage)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                                .inflate(R.layout.card_view, parent, false)
                return ItemViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
                holder.Name.text = List[position].name
                holder.Location.text = List[position].location
                holder.Date.text = List[position].date
                holder.userName.text = List[position].username
                Glide
                        .with(holder.Image)
                        .load(List[position].image)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .centerCrop()
                        .into(holder.Image);
        }

        override fun getItemCount(): Int {
                return List.size
        }
}