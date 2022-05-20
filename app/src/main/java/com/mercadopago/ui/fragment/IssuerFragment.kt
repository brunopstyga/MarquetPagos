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
import com.mercadopago.databinding.IssuerFragmentBinding
import com.mercadopago.model.Issuer
import com.mercadopago.model.Result
import com.mercadopago.ui.adapter.IssuerAdapter
import com.mercadopago.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssuerFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()
    lateinit var binding: IssuerFragmentBinding
    val issuers = ArrayList<Issuer>()
    val issuerAdapter = IssuerAdapter(issuers) { v ->
        viewModel.showInstallments(v, issuers.find { it.id == v.tag.toString() })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.issuer_fragment, container, false)
        binding.apply {
            viewmodel = this@IssuerFragment.viewModel
            binding.lifecycleOwner = this@IssuerFragment
            issuerList.apply {
                hasFixedSize()
                adapter = issuerAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }

        viewModel.getIssuers().observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    if (it.data.isNotEmpty()) {
                        issuers.apply {
                            binding.issuerLoading.visibility = View.GONE
                            clear()
                            addAll(it.data)
                            sortBy { it.name }
                        }
                        issuerAdapter.notifyDataSetChanged()
                    } else {
                        viewModel.showInstallments(requireView(), null)
                    }
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

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}