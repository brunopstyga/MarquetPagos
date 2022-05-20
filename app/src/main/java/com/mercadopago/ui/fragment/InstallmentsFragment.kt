package com.mercadopago.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercadopago.R
import com.mercadopago.databinding.InstallmentsFragmentBinding
import com.mercadopago.model.PayerCost
import com.mercadopago.model.Result
import com.mercadopago.ui.adapter.InstallmentAdapter
import com.mercadopago.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InstallmentsFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()
    lateinit var binding: InstallmentsFragmentBinding
    val payersCost = ArrayList<PayerCost>()
    val installmentAdapter = InstallmentAdapter(payersCost) { v ->
        viewModel.showInputData(v, payersCost.find { it.installments.toString() == v.tag.toString() })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.installments_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.installmentsList.apply {
            hasFixedSize()
            adapter = installmentAdapter
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getInstallments().observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    payersCost.apply {
                        binding.installmentsLoading.visibility = View.GONE
                        clear()
                        addAll(it.data.first().payer_costs)
                        sortBy { it.installments }
                    }
                    installmentAdapter.notifyDataSetChanged()
                }
                is Result.Error -> {
                    AlertDialog.Builder(context)
                        .setTitle("ERROR")
                        .setMessage("Intente más tarde")
                        .setNeutralButton("Cerrar") { _, _ ->
                            viewModel.backToHome(requireView())
                        }
                        .show()
                }
            }
        }
    }

}