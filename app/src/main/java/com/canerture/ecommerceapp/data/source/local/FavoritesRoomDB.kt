package com.canerture.ecommerceapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.canerture.ecommerceapp.data.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class FavoritesRoomDB : RoomDatabase() {
    abstract fun productFavoriteDAO(): ProductFavoriteDAO
}