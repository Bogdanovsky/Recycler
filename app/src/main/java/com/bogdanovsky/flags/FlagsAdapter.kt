package com.bogdanovsky.flags

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class FlagsAdapter : RecyclerView.Adapter<FlagsAdapter.FlagsViewHolder>() {

    val countries = Countries.countries

    class FlagsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.flag_item_view, parent, false)
        return FlagsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: FlagsViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.id).text = countries[position].first
        holder.itemView.findViewById<TextView>(R.id.name).text = countries[position].second
        holder.itemView.findViewById<ImageView>(R.id.flag_image).
        load("https://flagcdn.com/w320/${countries[position].first}.webp") {
            placeholder(R.drawable.baseline_broken_image_24)
            error(R.drawable.baseline_error_24)
        }

    }
}