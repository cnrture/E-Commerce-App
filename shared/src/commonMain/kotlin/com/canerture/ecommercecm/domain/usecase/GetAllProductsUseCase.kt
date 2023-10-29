package com.canerture.ecommercecm.domain.usecase

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.domain.mapper.mapToProductUI
import com.canerture.ecommercecm.domain.repository.ProductRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class GetAllProductsUseCase(private val productRepository: ProductRepository) {

    operator fun invoke() = callbackFlow {

        val favoriteTitles = productRepository.getFavoriteTitles()

        when (val result = productRepository.getAllProducts()) {
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