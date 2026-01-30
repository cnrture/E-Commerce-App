package com.canerture.ecommerceapp.domain.usecase.bag

import com.canerture.ecommerceapp.common.Resource
import com.canerture.ecommerceapp.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBagProductsCountUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
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