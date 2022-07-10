package com.canerture.e_commerce_app.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.canerture.e_commerce_app.data.model.Product

@Dao
interface ProductFavoriteDAO {

    @Insert
    suspend fun addToFavorite(product: Product)

    @Query("SELECT * FROM favorites")
    suspend fun getFavorites(): List<Product>?

    @Query("SELECT title FROM favorites")
    suspend fun getFavoritesTitles(): List<String>?

    @Query("DELETE FROM favorites WHERE id = :idInput")
    suspend fun deleteFromFavorites(idInput: Int)

    @Query("DELETE FROM favorites")
    fun clearFavorites()
}