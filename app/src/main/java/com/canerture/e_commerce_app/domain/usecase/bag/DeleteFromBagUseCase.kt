package com.canerture.e_commerce_app.domain.usecase.bag

import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.data.model.CRUDResponse
import com.canerture.e_commerce_app.domain.repository.ProductsRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteFromBagUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {

    suspend operator fun invoke(id: Int): Resource<CRUDResponse> {
        return try {
            Resource.Loading
            Resource.Success(productsRepository.deleteFromBag(id))
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: IOException) {
            Resource.Error(e)
        }
    }
}