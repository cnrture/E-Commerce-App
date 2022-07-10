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
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int,

    @SerializedName("category")
    val category: String,

    @SerializedName("count")
    @ColumnInfo(name = "count")
    val count: Int,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String,

    @SerializedName("image")
    @ColumnInfo(name = "image")
    val image: String,

    @SerializedName("image_two")
    @ColumnInfo(name = "image_two")
    val imageTwo: String,

    @SerializedName("image_three")
    @ColumnInfo(name = "image_three")
    val imageThree: String,

    @SerializedName("price")
    @ColumnInfo(name = "price")
    val price: Double,

    @SerializedName("rate")
    @ColumnInfo(name = "rate")
    val rate: Double,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String,

    @SerializedName("sale_state")
    @ColumnInfo(name = "sale_state")
    val saleState: Int,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "salePrice")
    val salePrice: Double?,

    ) : Parcelable