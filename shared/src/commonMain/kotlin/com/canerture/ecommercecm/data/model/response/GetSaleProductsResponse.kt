package com.canerture.ecommercecm.data.model.response

data class GetSaleProductsResponse(
    val products: List<Product>?
) : BaseResponse()