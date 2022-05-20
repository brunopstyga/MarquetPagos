package com.mercadopago.model

data class Installment(
    val payment_method_id: String,
    val payment_type_id: String,
    val issuer: Issuer,
    val payer_costs: List<PayerCost>
)