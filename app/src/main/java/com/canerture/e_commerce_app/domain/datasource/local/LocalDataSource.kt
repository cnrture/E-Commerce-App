package com.canerture.e_commerce_app.domain.datasource.local

import com.canerture.e_commerce_app.data.model.Product

interface LocalDataSource {

    suspend fun addToFavorites(product: Product)

    suspend fun getFavorites(): List<Product>?

    suspend fun deleteFromFavorites(id: Int)

    suspend fun clearFavorites()

    suspend fun getFavoritesNamesList(): List<String>?

}