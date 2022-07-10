package com.canerture.e_commerce_app.domain.usecase.bag

import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBagProductsCountUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(): Resource<Int> {
        return try {
            Resource.Loading
            Resource.Success(productsRepository.getBagProductsCount())
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}