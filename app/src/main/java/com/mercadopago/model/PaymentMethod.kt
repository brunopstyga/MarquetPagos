package com.mercadopago.model

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


data class PaymentMethod(
    val id: String,
    val name: String,
    val type: String,
    val status: String,
    val thumbnail: String,
    val settings: List<Settings?>?,
    val min_allowed_amount: Float,
    val max_allowed_amount: Float
) {
    data class Settings(
        val card_number: CardNumber?,
        val bin: Bin?,
        val security_code: SecurityCode?
    ) {
        data class CardNumber(
            val validation: String?,
            val length: Int?
        )

        data class Bin(
            val pattern: String?,
            val installments_pattern: String?,
            val exclusion_pattern: String?
        )

        data class SecurityCode(
            val length: Int?,
            val card_location: String?,
            val mode: String?
        )
    }

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



