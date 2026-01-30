package com.canerture.ecommerceapp.domain.datasource.remote

import com.canerture.ecommerceapp.data.model.CRUDResponse
import com.canerture.ecommerceapp.data.model.Product

interface RemoteDataSource {

    suspend fun getProducts(): List<Product>

    suspend fun getSaleProducts(): List<Product>

    suspend fun addToBag(product: Product): CRUDResponse

    suspend fun getBagProducts(): List<Product>

    suspend fun getBagProductsCount(): Int

    suspend fun deleteFromBag(id: Int): CRUDResponse

    suspend fun getCategories(): List<String>

    suspend fun getProductsByCategory(category: String): List<Product>

    suspend fun clearBag(): CRUDResponse

    suspend fun searchProduct(query: String): List<Product>

}