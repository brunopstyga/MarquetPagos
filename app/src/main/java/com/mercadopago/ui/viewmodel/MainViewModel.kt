package com.mercadopago.ui.viewmodel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.navigation.Navigation
import com.mercadopago.R
import com.mercadopago.model.*
import com.mercadopago.repositories.ApiRepository
import com.mercadopago.util.Keyboard
import kotlinx.coroutines.Dispatchers

class MainViewModel @ViewModelInject constructor(
    val apiRepository: ApiRepository
) : ViewModel() {

    val amount: MutableLiveData<String> = MutableLiveData()
    var paymentMethod: PaymentMethod? = null
    var issuer: Issuer? = null
    var payerCost: PayerCost? = null
    var card: Card = Card()


    fun showPaymentMethods(view: View) {
        Keyboard.hide(view)
        Navigation.findNavController(view).navigate(R.id.action_amountFragment_to_cardFragment)
    }

    fun showIssuers(v: View, m: PaymentMethod?) {
        paymentMethod = m
        Navigation.findNavController(v).navigate(R.id.action_cardFragment_to_issuerFragment)
    }

    fun showInstallments(v: View, i: Issuer?) {
        issuer = i
        if (i == null) {
            Navigation.findNavController(v).apply {
                popBackStack()
                navigate(R.id.action_cardFragment_to_installmentsFragment)
            }
        } else {
            Navigation.findNavController(v).navigate(R.id.action_issuerFragment_to_installmentsFragment)
        }
    }

    fun showInputData(v: View, p: PayerCost?) {
        payerCost = p
        Navigation.findNavController(v).navigate(R.id.action_installmentsFragment_to_inputDataFragment)
    }

    fun showSummary(v: View) {
        with(Navigation.findNavController(v)) {
            if (v.id != R.id.input_data_btn_ok) {
                popBackStack()
                navigate(R.id.action_installmentsFragment_to_summaryFragment)
            } else {
                navigate(R.id.action_inputDataFragment_to_summaryFragment)
            }
        }
    }

    fun backToHome(v: View) {
        amount.value = null
        paymentMethod = null
        issuer = null
        payerCost = null
        card = Card()
        Navigation.findNavController(v).popBackStack(R.id.amountFragment, false)
    }

    fun getMethods() = liveData(Dispatchers.IO) {
        emit(apiRepository.getMethods())
    }

    fun getIssuers() = liveData(Dispatchers.IO) {
        emit(apiRepository.getIssuers(paymentMethod?.id ?: ""))
    }

    fun getInstallments() = liveData(Dispatchers.IO) {
        emit(apiRepository.getInstallments(
            amount.value?.replace(Regex("[$ ]"), "") ?: "",
            paymentMethod?.id ?: "",
            issuer?.id ?: "")
        )
    }
}