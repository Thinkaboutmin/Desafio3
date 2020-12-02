package com.trab.desafio3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.trab.desafio3.R
import com.trab.desafio3.helper.MarvelAPI
import com.trab.desafio3.models.Results


class ComicsAdapter(val marvelApi: MarvelAPI) : RecyclerView.Adapter<ComicsAdapter.PosterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hq_poster, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val list = marvelApi.comics
        val cover =  holder.itemView.findViewById<ImageView>(R.id.imgCover)
        val image = list[position].images[0]
        Picasso.get().load("${image.path}.${image.extension}").into(cover)

        val title = holder.itemView.findViewById<TextView>(R.id.tvTitle)
        title.text = title.resources.getString(R.string.cover_title, list[position].id)

        val params = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        // Tudo no meio controla a margem
        when (position % 3) {
            0 -> {
                params.marginEnd = 5
            }
            1 -> {
                title.updatePadding(left = 15)
                params.marginEnd = 10
                params.marginStart = 10
            }
            2 -> {
                title.updatePadding(left = 30)
                params.marginStart = 5
            }
        }
    }

    override fun getItemCount(): Int = marvelApi.comics.size

    inner class PosterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}