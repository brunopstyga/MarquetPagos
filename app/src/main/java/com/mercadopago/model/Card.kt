package com.mercadopago.model

import androidx.lifecycle.MutableLiveData

class Card {
    val number: MutableLiveData<String> = MutableLiveData()
    val securityCode: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val expireDate: MutableLiveData<String> = MutableLiveData()
}