package com.canerture.e_commerce_app.domain.usecase.favorite

import com.canerture.e_commerce_app.domain.repository.ProductsRepository
import javax.inject.Inject

class ClearFavoritesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke() = productsRepository.clearFavorites()
}