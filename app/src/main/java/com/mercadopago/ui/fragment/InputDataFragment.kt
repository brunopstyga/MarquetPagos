package com.mercadopago.ui.fragment

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.mercadopago.R
import com.mercadopago.databinding.InputDataFragmentBinding
import com.mercadopago.ui.viewmodel.MainViewModel
import com.mercadopago.util.DateMaskWatcher
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputDataFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()
    lateinit var binding: InputDataFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.input_data_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.apply {
            val settings = viewmodel?.paymentMethod?.settings
            if (settings != null && settings.isNotEmpty()) {
                inputDataCardNumber.filters = arrayOf(InputFilter.LengthFilter(settings[0]?.card_number?.length ?: 20))
                inputDataSecNumber.filters = arrayOf(InputFilter.LengthFilter(settings[0]?.security_code?.length ?: 4))
            }

            inputDataExpireDate.addTextChangedListener(DateMaskWatcher(inputDataExpireDate))
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (viewModel.paymentMethod?.settings != null && viewModel.paymentMethod?.settings?.size!! == 0) {
            viewModel.showSummary(requireView())
        }
    }

}