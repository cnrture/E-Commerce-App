package com.canerture.ecommercecm.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SearchProductResponse(
    val products: List<Product>?
) : BaseResponse()