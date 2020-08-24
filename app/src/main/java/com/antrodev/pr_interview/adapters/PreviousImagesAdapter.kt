package com.antrodev.pr_interview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.antrodev.pr_interview.R
import com.antrodev.pr_interview.helper.ImageHelper

class PreviousImagesAdapter(var previousImages: ArrayList<String>) :
    RecyclerView.Adapter<PreviousImagesAdapter.ViewHolder>() {

    class ViewHolder(val image: ImageView) : RecyclerView.ViewHolder(image)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageBitmap(ImageHelper.convertBase64ToBitmap(previousImages[position]))
    }

    override fun getItemCount() = previousImages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.previous_image_item, parent, false) as ImageView

        return ViewHolder(imageView)
    }

}

