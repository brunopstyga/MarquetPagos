package com.mercadopago.net

import com.mercadopago.model.Issuer
import com.mercadopago.model.Installment
import com.mercadopago.model.PaymentMethod
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(" ")
    suspend fun getMethods(): List<PaymentMethod>

    @GET("card_issuers")
    suspend fun getIssuers(@Query("payment_method_id") methodId: String): List<Issuer>

    @GET("installments")
    suspend fun getInstallments(
        @Query("amount") amount: String,
        @Query("payment_method_id") methodId: String,
        @Query("issuer.id") issuer: String,
    ): List<Installment>
}