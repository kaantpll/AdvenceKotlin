package com.example.testhazirlik.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.testhazirlik.R
import com.example.testhazirlik.model.Art
import com.example.testhazirlik.viewmodel.ArtViewModel
import org.w3c.dom.Text
import java.util.*
import javax.inject.Inject

class ArtAdapter @Inject constructor(
        val glide : RequestManager
) :RecyclerView.Adapter<ArtAdapter.MyViewHolder>()
{
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem ==newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem ==newItem
        }

    }
    private val recyclerviewdif = AsyncListDiffer(this,diffUtil)

    var arts : List<Art>
    get() = recyclerviewdif.currentList
    set(value) = recyclerviewdif.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageview = holder.itemView.findViewById<ImageView>(R.id.card_image)
        val nameText = holder.itemView.findViewById<TextView>(R.id.card_artName)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.card_artAuthor)
        val artYears = holder.itemView.findViewById<TextView>(R.id.card_artYears)
        val art = arts[position]
        holder.itemView.apply {
            glide.load(art.mimageUrl).into(imageview)
            nameText.text = "Name: ${art.name}"
            artistNameText.text = "Artist Name: ${art.artistName}"
            artYears.text = "Year: ${art.year}"
        }
    }

    override fun getItemCount(): Int {
        return arts.size
    }
}