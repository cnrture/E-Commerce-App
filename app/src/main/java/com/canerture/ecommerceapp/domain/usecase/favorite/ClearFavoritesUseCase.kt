package com.canerture.ecommerceapp.domain.usecase.favorite

import com.canerture.ecommerceapp.domain.repository.ProductsRepository
import javax.inject.Inject

class ClearFavoritesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
) {
    suspend operator fun invoke() = productsRepository.clearFavorites()
}