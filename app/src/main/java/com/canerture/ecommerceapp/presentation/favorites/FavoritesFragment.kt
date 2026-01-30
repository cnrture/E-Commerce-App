package com.canerture.ecommerceapp.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.canerture.ecommerceapp.R
import com.canerture.ecommerceapp.common.Resource
import com.canerture.ecommerceapp.common.delegate.viewBinding
import com.canerture.ecommerceapp.common.gone
import com.canerture.ecommerceapp.common.showSnackbar
import com.canerture.ecommerceapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private val favoritesAdapter by lazy { FavoritesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        favoritesAdapter.onDeleteClick = {
            favoritesViewModel.deleteFromFavorites(it)
        }
    }

    private fun initObservers() {

        with(binding) {

            favoritesViewModel.favoriteList.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        progressBar.gone()
                        favoritesAdapter.updateList(it.data)
                        rvFavorites.adapter = favoritesAdapter
                    }

                    is Resource.Error -> {
                        progressBar.gone()
                        requireView().showSnackbar(it.throwable.message.toString())
                    }

                    Resource.Loading -> progressBar.gone()
                }
            }
        }
    }
}