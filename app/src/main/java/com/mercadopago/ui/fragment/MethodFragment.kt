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
import com.mercadopago.databinding.MethodFragmentBinding
import com.mercadopago.model.PaymentMethod
import com.mercadopago.model.Result
import com.mercadopago.ui.adapter.MethodAdapter
import com.mercadopago.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MethodFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()
    lateinit var binding: MethodFragmentBinding
    val methods = ArrayList<PaymentMethod>()
    val methodsAdapter = MethodAdapter(methods) { v ->
        viewModel.showIssuers(v, methods.find { it.id == v.tag.toString() })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.method_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.methodList.apply {
            hasFixedSize()
            adapter = methodsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getMethods().observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    methods.apply {
                        binding.methodLoading.visibility = View.GONE
                        clear()
                        addAll(it.data)
                        sortBy { it.name }
                    }
                    methodsAdapter.notifyDataSetChanged()
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