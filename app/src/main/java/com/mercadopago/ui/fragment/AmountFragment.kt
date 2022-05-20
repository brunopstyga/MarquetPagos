package com.mercadopago.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.mercadopago.R
import com.mercadopago.databinding.AmountFragmentBinding
import com.mercadopago.ui.viewmodel.MainViewModel
import com.mercadopago.util.MaskWatcher
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class AmountFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: AmountFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.amount_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.amountValue.addTextChangedListener(MaskWatcher(binding.amountValue))

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


//        viewModel.amount.observe(viewLifecycleOwner) {
//            Log.e(">>>", "Received update")
//        }
    }

}