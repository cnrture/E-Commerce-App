package com.canerture.ecommerceapp.domain.usecase.favorite

import com.canerture.ecommerceapp.common.Resource
import com.canerture.ecommerceapp.data.model.Product
import com.canerture.ecommerceapp.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
) {

    suspend operator fun invoke(): Resource<List<Product>> {
        return try {
            Resource.Loading
            Resource.Success(productsRepository.getFavorites().orEmpty())
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}