package com.mercadopago.repositories

import com.mercadopago.net.ApiService
import com.mercadopago.model.Result
import javax.inject.Inject

class ApiRepository @Inject constructor(
    val apiService: ApiService
) {

    suspend fun getMethods() = safeCall {
        apiService.getMethods()
    }

    suspend fun getIssuers(methodId: String) = safeCall {
        apiService.getIssuers(methodId)
    }

    suspend fun getInstallments(amount: String, methodId: String, issuer: String) = safeCall {
        apiService.getInstallments(amount, methodId, issuer)
    }


    suspend fun <T> safeCall(f: suspend () -> T): Result<T> {
        return try {
            Result.Success(f.invoke())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}