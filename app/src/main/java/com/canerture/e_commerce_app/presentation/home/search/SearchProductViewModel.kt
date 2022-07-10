package com.canerture.e_commerce_app.presentation.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.data.model.Product
import com.canerture.e_commerce_app.domain.usecase.product.GetProductsByCategoryUseCase
import com.canerture.e_commerce_app.domain.usecase.product.GetProductsUseCase
import com.canerture.e_commerce_app.domain.usecase.product.SearchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
    private val searchProductUseCase: SearchProductUseCase,
) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products

    fun searchProduct(query: String) {
        viewModelScope.launch {
            _products.value = Resource.Loading
            _products.value = searchProductUseCase(query)
        }
    }
}