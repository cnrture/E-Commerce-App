package com.canerture.ecommercecm.data.model.response

data class GetCartProductsResponse(
    val products: List<Product>?
) : BaseResponse()