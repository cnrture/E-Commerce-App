package com.canerture.ecommercecm.domain.mapper

import com.canerture.ecommercecm.data.model.response.Product
import com.canerture.ecommercecm.data.model.response.ProductUI

fun Product?.mapToProductUI(isFavorite: Boolean): ProductUI {
    return ProductUI(
        id = this?.id ?: 1,
        title = this?.title.orEmpty(),
        price = this?.price ?: 0.0,
        salePrice = this?.salePrice ?: 0.0,
        description = this?.description.orEmpty(),
        category = this?.category.orEmpty(),
        imageOne = this?.imageOne.orEmpty(),
        rate = this?.rate ?: 0.0,
        count = this?.count ?: 0,
        saleState = this?.saleState ?: false,
        isFavorite = isFavorite
    )
}