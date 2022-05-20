package com.mercadopago.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadopago.databinding.InstallmentItemBinding
import com.mercadopago.model.PayerCost

class InstallmentAdapter(
    val payer_costs: List<PayerCost>,
    val onClickListener: View.OnClickListener
) :
    RecyclerView.Adapter<InstallmentAdapter.ViewHolder>() {

    class ViewHolder(val bind: InstallmentItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(payerCost: PayerCost) {
            bind.payerCost = payerCost
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = InstallmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        binding.installmentItemCard.setOnClickListener(onClickListener)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = payer_costs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(payer_costs[position])
    }
}