package com.canerture.ecommercecm.ui.home

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.response.ProductUI
import com.canerture.ecommercecm.domain.usecase.GetAllProductsUseCase
import com.canerture.ecommercecm.domain.usecase.SearchProductUseCase
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HomeViewModel : ViewModel(), KoinComponent {

    private val getAllProductsUseCase: GetAllProductsUseCase = get()
    private val searchProductsUseCase: SearchProductUseCase = get()

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getAllProducts()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SearchProduct -> searchProduct(event.query)
        }
    }

    private fun getAllProducts() =
        getAllProductsUseCase
            .invoke()
            .onStart { _state.value = HomeState.Loading }
            .onEach {
                _state.value = when (it) {
                    is Resource.Success -> HomeState.AllProductsContent(it.data)
                    is Resource.Error -> HomeState.Error(it.throwable)
                    is Resource.Fail -> HomeState.EmptyScreen(it.message)
                }
            }
            .launchIn(viewModelScope)

    private fun searchProduct(query: String) =
        searchProductsUseCase
            .invoke(query)
            .onStart { _state.value = HomeState.Loading }
            .onEach {
                _state.value = when (it) {
                    is Resource.Success -> HomeState.AllProductsContent(it.data)
                    is Resource.Error -> HomeState.Error(it.throwable)
                    is Resource.Fail -> HomeState.EmptyScreen(it.message)
                }
            }
            .launchIn(viewModelScope)
}

sealed interface HomeState {
    data object Loading : HomeState
    data class EmptyScreen(val message: String) : HomeState
    data class AllProductsContent(val allProducts: List<ProductUI>) : HomeState
    data class Error(val throwable: Throwable) : HomeState
}

sealed interface HomeEvent {
    data class SearchProduct(val query: String) : HomeEvent
}