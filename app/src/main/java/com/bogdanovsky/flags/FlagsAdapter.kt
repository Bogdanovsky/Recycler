package com.bogdanovsky.flags

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class FlagsAdapter : RecyclerView.Adapter<FlagsAdapter.FlagsViewHolder>() {

    private val countries = Countries.getCountries()

    class FlagsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.flag_item_view, parent, false)
        return FlagsViewHolder(itemView)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: FlagsAdapter.FlagsViewHolder, position: Int) {
        with(holder) {
            itemView.findViewById<TextView>(R.id.id).text = countries[position].first
            itemView.findViewById<TextView>(R.id.name).text = countries[position].second
            itemView.findViewById<ImageView>(R.id.flag_image)
                .load("https://flagcdn.com/w320/${countries[position].first}.webp") {
                    placeholder(R.drawable.baseline_broken_image_24)
                    error(R.drawable.baseline_error_24)
                }
        }
    }
}