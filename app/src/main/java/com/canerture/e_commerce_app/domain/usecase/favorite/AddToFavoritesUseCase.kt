package com.canerture.e_commerce_app.domain.usecase.favorite

import com.canerture.e_commerce_app.data.model.Product
import com.canerture.e_commerce_app.domain.repository.ProductsRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(product: Product) = productsRepository.addToFavorites(product)
}