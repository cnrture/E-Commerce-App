package com.canerture.e_commerce_app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.canerture.e_commerce_app.data.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class FavoritesRoomDB : RoomDatabase() {
    abstract fun productFavoriteDAO(): ProductFavoriteDAO
}