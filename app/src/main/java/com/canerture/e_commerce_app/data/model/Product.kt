package com.canerture.e_commerce_app.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorites")
@Parcelize
data class Product(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "count")
    val count: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image")
    val image: String,

    @SerializedName("image_two")
    @ColumnInfo(name = "image_two")
    val imageTwo: String,

    @SerializedName("image_three")
    @ColumnInfo(name = "image_three")
    val imageThree: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "rate")
    val rate: Double,

    @ColumnInfo(name = "title")
    val title: String,

    @SerializedName("sale_state")
    @ColumnInfo(name = "sale_state")
    val saleState: Int,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "salePrice")
    val salePrice: Double?
) : Parcelable