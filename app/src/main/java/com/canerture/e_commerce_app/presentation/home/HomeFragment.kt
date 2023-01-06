package com.canerture.e_commerce_app.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.common.delegate.viewBinding
import com.canerture.e_commerce_app.common.util.gone
import com.canerture.e_commerce_app.common.util.showSnackbar
import com.canerture.e_commerce_app.common.util.visible
import com.canerture.e_commerce_app.presentation.home.categories.CategoryPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import e_commerce_app.R
import e_commerce_app.databinding.FragmentHomeBinding
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

    private val saleProductsAdapter by lazy { SaleProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding) {

            imgBag.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_bagFragment)
            }
        }

        saleProductsAdapter.onFavoriteClick = {
            if (it.isFavorite.not()) viewModel.addToFavorite(it)
            else viewModel.deleteFromFavorites(it.id)
        }

        saleProductsAdapter.onProductClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun initObservers() {
        with(binding) {
            with(viewModel) {

                user.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            tvNickname.text = it.data.nickname
                        }
                        is Resource.Error -> {
                            progressBar.gone()
                            requireView().showSnackbar(it.throwable.message.toString())
                        }
                        Resource.Loading -> progressBar.visible()
                    }
                }

                saleProducts.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            saleProductsAdapter.updateList(it.data)
                            val compositePageTransformer = CompositePageTransformer()
                            compositePageTransformer.addTransformer { page, position ->
                                val r = 1 - abs(position)
                                page.scaleY = (0.85f + r * 0.15f)
                            }

                            vpSaleProducts.apply {
                                adapter = saleProductsAdapter
                                clipToPadding = false
                                clipChildren = false
                                offscreenPageLimit = 3
                                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                                setPageTransformer(compositePageTransformer)
                                currentItem = 2
                            }
                        }
                        is Resource.Error -> {
                            progressBar.gone()
                            requireView().showSnackbar(it.throwable.message.toString())
                        }
                        Resource.Loading -> progressBar.visible()
                    }
                }

                bagProductsCount.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            tvBagProductsCount.text = it.data.toString()
                        }
                        is Resource.Error -> {
                            progressBar.gone()
                            requireView().showSnackbar(it.throwable.message.toString())
                        }
                        Resource.Loading -> progressBar.visible()
                    }
                }

                categories.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            val tempCategories = it.data.toMutableList()
                            tempCategories.add(0, "All")
                            vpCategory.adapter =
                                CategoryPagerAdapter(this@HomeFragment, tempCategories)
                            TabLayoutMediator(tabLayout, vpCategory) { tab, position ->
                                tab.text = tempCategories[position]
                            }.attach()
                        }
                        is Resource.Error -> {
                            progressBar.gone()
                            requireView().showSnackbar(it.throwable.message.toString())
                        }
                        Resource.Loading -> progressBar.visible()
                    }
                }
            }
        }
    }
}