package com.canerture.e_commerce_app.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.canerture.e_commerce_app.R
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.common.delegate.viewBinding
import com.canerture.e_commerce_app.common.gone
import com.canerture.e_commerce_app.common.showSnackbar
import com.canerture.e_commerce_app.common.visible
import com.canerture.e_commerce_app.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val binding by viewBinding(FragmentProductDetailBinding::bind)

    private val detailViewModel: DetailViewModel by viewModels()

    private val detailImagesAdapter by lazy { DetailImagesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding) {

            imgBack.setOnClickListener {
                findNavController().navigate(R.id.action_productDetailFragment_to_homeFragment)
            }

            imgFavorite.setOnClickListener { detailViewModel.setFavoriteState() }

            btnAddToBag.setOnClickListener { detailViewModel.addToBag() }
        }
    }

    private fun initObservers() {

        with(binding) {
            with(detailViewModel) {

                product.observe(viewLifecycleOwner) {

                    productModel = it

                    val imageList = listOf(it.image, it.imageTwo, it.imageThree)

                    detailImagesAdapter.updateList(imageList)
                    val compositePageTransformer = CompositePageTransformer()
                    compositePageTransformer.addTransformer { page, position ->
                        val r = 1 - abs(position)
                        page.scaleY = (0.85f + r * 0.15f)
                    }

                    viewPager.apply {
                        adapter = detailImagesAdapter
                        clipToPadding = false
                        clipChildren = false
                        offscreenPageLimit = 3
                        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                        setPageTransformer(compositePageTransformer)
                        currentItem = 1
                    }
                }

                crudResponse.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            requireView().showSnackbar(it.data.message)
                        }

                        is Resource.Error -> {
                            progressBar.gone()
                            requireView().showSnackbar(it.throwable.message.toString())
                        }

                        Resource.Loading -> progressBar.visible()
                    }
                }

                isFavorite.observe(viewLifecycleOwner) {
                    imgFavorite.setImageResource(
                        if (it) R.drawable.ic_favorite_selected
                        else R.drawable.ic_favorite_unselected
                    )
                }
            }
        }
    }
}