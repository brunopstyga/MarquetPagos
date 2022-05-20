package com.mercadopago.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mercadopago.databinding.IssuerItemBinding
import com.mercadopago.model.Issuer

class IssuerAdapter(
    val issuers: List<Issuer>,
    val onClickListener: View.OnClickListener
) :
    RecyclerView.Adapter<IssuerAdapter.ViewHolder>() {

    class ViewHolder(val bind: IssuerItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(issuer: Issuer) {
            bind.issuer = issuer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IssuerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        binding.methodItemCard.setOnClickListener(onClickListener)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = issuers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(issuers[position])
    }
}