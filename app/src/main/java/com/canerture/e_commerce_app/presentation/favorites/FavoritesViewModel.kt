package com.canerture.e_commerce_app.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.data.model.Product
import com.canerture.e_commerce_app.domain.usecase.favorite.DeleteFromFavoritesUseCase
import com.canerture.e_commerce_app.domain.usecase.favorite.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase
) : ViewModel() {

    private val _favoriteList = MutableLiveData<Resource<List<Product>>>()
    val favoriteList: LiveData<Resource<List<Product>>> = _favoriteList

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            _favoriteList.value = getFavoritesUseCase()
        }
    }

    fun deleteFromFavorites(id: Int) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(id)
            getFavorites()
        }
    }
}