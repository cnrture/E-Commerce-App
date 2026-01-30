package com.canerture.ecommerceapp.data.source.remote

import com.canerture.ecommerceapp.common.Constants
import com.canerture.ecommerceapp.data.model.CRUDResponse
import com.canerture.ecommerceapp.data.model.Product
import com.canerture.ecommerceapp.domain.datasource.remote.RemoteDataSource
import com.canerture.ecommerceapp.domain.repository.Authenticator
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val productService: ProductService,
    private val authenticator: Authenticator,
) : RemoteDataSource {

    override suspend fun getProducts(): List<Product> =
        productService.getProducts(Constants.USER)

    override suspend fun getSaleProducts(): List<Product> =
        productService.getSaleProducts(Constants.USER)

    override suspend fun addToBag(product: Product): CRUDResponse {

        return productService.addToBag(
            authenticator.getFirebaseUserUid(),
            product.title,
            product.salePrice ?: product.price,
            product.description,
            product.category,
            product.image,
            product.imageTwo,
            product.imageThree,
            product.rate,
            product.count,
            product.saleState
        )
    }

    override suspend fun getBagProducts(): List<Product> {
        return productService.getBagProductsByUser(authenticator.getFirebaseUserUid())
    }

    override suspend fun getBagProductsCount(): Int {
        return productService.getBagProductsByUser(authenticator.getFirebaseUserUid()).size
    }

    override suspend fun deleteFromBag(id: Int): CRUDResponse = productService.deleteFromBag(id)

    override suspend fun getCategories(): List<String> =
        productService.getCategories(Constants.USER)

    override suspend fun getProductsByCategory(category: String): List<Product> =
        productService.getProductsByCategory(Constants.USER, category)

    override suspend fun clearBag(): CRUDResponse {
        return productService.clearBag(authenticator.getFirebaseUserUid())
    }

    override suspend fun searchProduct(query: String) =
        productService.searchProduct(Constants.USER, query)

}