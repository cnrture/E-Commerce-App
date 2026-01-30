package com.canerture.ecommerceapp.domain.usecase.bag

import com.canerture.ecommerceapp.common.Resource
import com.canerture.ecommerceapp.data.model.CRUDResponse
import com.canerture.ecommerceapp.data.model.Product
import com.canerture.ecommerceapp.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddToBagUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
) {
    suspend operator fun invoke(product: Product): Resource<CRUDResponse> {
        return try {
            Resource.Loading
            Resource.Success(productsRepository.addToBag(product))
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}