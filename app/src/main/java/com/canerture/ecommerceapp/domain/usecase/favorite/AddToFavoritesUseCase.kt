package com.canerture.ecommerceapp.domain.usecase.favorite

import com.canerture.ecommerceapp.data.model.Product
import com.canerture.ecommerceapp.domain.repository.ProductsRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
) {
    suspend operator fun invoke(product: Product) = productsRepository.addToFavorites(product)
}