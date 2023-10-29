package com.canerture.ecommercecm.data.model.response

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    var status: Int? = null,
    var message: String? = null
)