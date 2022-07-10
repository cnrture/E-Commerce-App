package com.canerture.e_commerce_app.domain.usecase.bag

import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.data.model.CRUDResponse
import com.canerture.e_commerce_app.data.model.Product
import com.canerture.e_commerce_app.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddToBagUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
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