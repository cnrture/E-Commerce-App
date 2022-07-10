package com.canerture.e_commerce_app.presentation.detail

import androidx.lifecycle.*
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.data.model.CRUDResponse
import com.canerture.e_commerce_app.data.model.Product
import com.canerture.e_commerce_app.domain.usecase.bag.AddToBagUseCase
import com.canerture.e_commerce_app.domain.usecase.favorite.AddToFavoritesUseCase
import com.canerture.e_commerce_app.domain.usecase.favorite.DeleteFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val addToBagUseCase: AddToBagUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _crudResponse = MutableLiveData<Resource<CRUDResponse>>()
    val crudResponse: LiveData<Resource<CRUDResponse>> = _crudResponse

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val productModel = savedStateHandle.get<Product>("product")

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        getProduct()
        _isFavorite.value = productModel?.isFavorite
    }

    fun addToBag() {
        viewModelScope.launch {
            productModel?.let {
                _crudResponse.value = Resource.Loading
                _crudResponse.value = addToBagUseCase(it)
            }
        }
    }

    private fun addToFavorite(product: Product) {
        viewModelScope.launch {
            addToFavoritesUseCase(product)
        }
    }

    private fun deleteFromFavorites(id: Int) {
        viewModelScope.launch {
            deleteFromFavoritesUseCase(id)
        }
    }

    private fun getProduct() {
        productModel?.let {
            _product.value = it
        }
    }

    fun setFavoriteState() {
        productModel?.let {
            if (_isFavorite.value == true) {
                deleteFromFavorites(it.id)
                _isFavorite.value = false
            } else {
                addToFavorite(it)
                _isFavorite.value = true
            }
        }
    }
}