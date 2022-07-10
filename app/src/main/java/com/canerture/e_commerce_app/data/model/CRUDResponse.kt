package com.canerture.e_commerce_app.data.model

import com.google.gson.annotations.SerializedName

data class CRUDResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
)