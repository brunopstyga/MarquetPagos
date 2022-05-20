package com.mercadopago.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mercadopago.databinding.MethodItemBinding
import com.mercadopago.model.PaymentMethod

class MethodAdapter(
    val paymentMethods: List<PaymentMethod>,
    val onClickListener: View.OnClickListener
) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(val bind: MethodItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(paymentMethod: PaymentMethod) {
            bind.method = paymentMethod
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MethodItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        binding.methodItemCard.setOnClickListener(onClickListener)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = paymentMethods.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(paymentMethods[position])
    }
}