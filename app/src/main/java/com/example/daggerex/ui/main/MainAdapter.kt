package com.example.daggerex.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daggerex.R
import com.example.daggerex.data.model.Hotgirl

class MainAdapter(var itemClick: View.OnClickListener) : ListAdapter<Hotgirl, MainAdapter.GirlHolder>(GirlCallBack()) {

    private var inflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater!!.inflate(R.layout.item_girl, parent, false)
        return GirlHolder(view)
    }

    override fun onBindViewHolder(holder: GirlHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data)

        holder.itemView.setOnClickListener(itemClick)
    }


    class GirlHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvItemName)
        private val ivAvatar: ImageView = itemView.findViewById(R.id.ivItemAvatar)

        fun onBind(data: Hotgirl) {
            tvName.text = data.name
            val imageRes = when(data.id % 3) {
                0 -> R.drawable.demo_first
                1 -> R.drawable.demo_second
                else -> R.drawable.demo_third
            }
            Glide.with(ivAvatar)
                    .load(imageRes)
                    .into(ivAvatar)
        }
    }
}