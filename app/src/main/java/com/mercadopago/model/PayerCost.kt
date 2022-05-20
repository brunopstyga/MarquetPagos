package com.mercadopago.model

data class PayerCost(
    val installments: Int,
    val installment_rate: Float,
    val labels: List<String>,
    val installment_amount: Float,
    val recommended_message: String,
)