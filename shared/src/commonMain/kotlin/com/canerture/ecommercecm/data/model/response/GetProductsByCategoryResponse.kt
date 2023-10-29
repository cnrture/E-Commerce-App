package com.canerture.ecommercecm.data.model.response

data class GetProductsByCategoryResponse(
    val products: List<Product>?
) : BaseResponse()