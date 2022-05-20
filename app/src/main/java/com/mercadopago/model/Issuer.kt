package com.mercadopago.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class Issuer(
    val id: String,
    val name: String,
    val thumbnail: String,
) {
    companion object {
        @BindingAdapter("thumbnail")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .placeholder(android.R.drawable.stat_sys_download)
                .into(view)
        }
    }
}