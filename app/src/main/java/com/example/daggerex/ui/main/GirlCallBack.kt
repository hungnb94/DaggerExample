package com.example.daggerex.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.daggerex.data.model.Hotgirl

class GirlCallBack : DiffUtil.ItemCallback<Hotgirl>() {

    override fun areItemsTheSame(oldItem: Hotgirl, newItem: Hotgirl): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Hotgirl, newItem: Hotgirl): Boolean {
        return oldItem.name == newItem.name
                && oldItem.avatar == newItem.avatar
    }

}