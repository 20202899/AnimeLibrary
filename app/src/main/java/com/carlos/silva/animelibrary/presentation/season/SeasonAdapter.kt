package com.carlos.silva.animelibrary.presentation.season

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.carlos.silva.animelibrary.R
import com.carlos.silva.core.domain.Anime
import jp.wasabeef.glide.transformations.BlurTransformation

class SeasonAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val animes = mutableListOf<Anime>()

    private val colors = intArrayOf(
        R.color.colorPrimaryDark,
        R.color.colorPrimary
    )

    lateinit var onClickListener: (Anime) -> Unit

    fun addAnimes(animes: List<Anime>) {
        this.animes.clear()
        this.animes.addAll(animes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.anime_item,
            parent,
            false
        )
    )

    override fun getItemCount() = animes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val anime = animes[position]
            holder.bind(anime, position)
            holder.itemView.setOnClickListener {
                onClickListener.invoke(anime)
            }
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.image)
        private val layerView = itemView.findViewById<View>(R.id.layer_view)
        private val textView = itemView.findViewById<TextView>(R.id.title)

        fun bind(anime: Anime, position: Int) {
            val color = Color.parseColor(anime.conceitualColor)
            layerView.setBackgroundColor(color)

            textView.text = anime.title

            Glide.with(itemView.context)
                .asBitmap()
                .load(anime.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageView.setImageBitmap(resource)
                    }

                })
        }
    }

}