package com.canerture.ecommercecm.domain.usecase

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.domain.mapper.mapToProductUI
import com.canerture.ecommercecm.domain.repository.ProductRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class SearchProductUseCase(private val productRepository: ProductRepository) {

    operator fun invoke(query: String) = callbackFlow {

        val favoriteTitles = productRepository.getFavoriteTitles()

        when (val result = productRepository.searchProduct(query)) {
            is Resource.Success -> {
                val products = result.data.products.orEmpty().map {
                    it.mapToProductUI(favoriteTitles.contains(it.title))
                }
                trySend(Resource.Success(products))
            }

            is Resource.Error -> trySend(result)
            is Resource.Fail -> trySend(result)
        }

        awaitClose { close() }
    }
}