package com.canerture.e_commerce_app.presentation.bag

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.common.delegate.viewBinding
import com.canerture.e_commerce_app.common.util.gone
import com.canerture.e_commerce_app.common.util.showSnackbar
import com.canerture.e_commerce_app.common.util.visible
import dagger.hilt.android.AndroidEntryPoint
import e_commerce_app.R
import e_commerce_app.databinding.FragmentBagBinding

@AndroidEntryPoint
class BagFragment : Fragment(R.layout.fragment_bag) {

    private val binding by viewBinding(FragmentBagBinding::bind)

    private val bagViewModel: BagViewModel by viewModels()

    private val bagProductsAdapter by lazy { BagProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(bagViewModel) {

            bagProductsAdapter.onIncreaseClick = {
                increase(it)
            }

            bagProductsAdapter.onDecreaseClick = {
                decrease(it)
            }

            bagProductsAdapter.onDeleteClick = { id ->
                deleteFromBag(id)
                resetTotalAmount()
            }
        }
    }

    private fun initObservers() {

        with(binding) {
            with(bagViewModel) {

                bagProducts.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.forEach { product ->
                                increase(product.price)
                            }
                            progressBar.gone()
                            bagProductsAdapter.updateList(result.data)
                            rvBagProducts.adapter = bagProductsAdapter

                            binding.btnBuyNow.setOnClickListener {
                                if (result.data.isNotEmpty()) {
                                    findNavController().navigate(R.id.action_bagFragment_to_paymentFragment)
                                } else {
                                    requireView().showSnackbar(getString(R.string.invalid_bag_products))
                                }
                            }
                        }
                        is Resource.Error -> {
                            progressBar.gone()
                            requireView().showSnackbar(result.throwable.message.toString())
                        }
                        Resource.Loading -> progressBar.visible()
                    }
                }

                totalAmount.observe(viewLifecycleOwner) {
                    tvTotalAmount.text = String.format("%.3f$", it)
                }
            }
        }
    }
}