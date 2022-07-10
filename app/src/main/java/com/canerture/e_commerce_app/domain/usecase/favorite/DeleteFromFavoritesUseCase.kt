package com.canerture.e_commerce_app.domain.usecase.favorite

import com.canerture.e_commerce_app.domain.repository.ProductsRepository
import javax.inject.Inject

class DeleteFromFavoritesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) = productsRepository.deleteFromFavorites(id)
}