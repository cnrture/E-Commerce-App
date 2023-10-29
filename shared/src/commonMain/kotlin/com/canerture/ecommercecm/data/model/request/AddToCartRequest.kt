package com.canerture.ecommercecm.data.model.request

data class AddToCartRequest(
    val userId: String,
    val productId: Int
)