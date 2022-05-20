package com.mercadopago.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mercadopago.R
import com.mercadopago.ui.fragment.AmountFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}