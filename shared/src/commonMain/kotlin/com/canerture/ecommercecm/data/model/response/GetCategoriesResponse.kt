package com.canerture.ecommercecm.data.model.response

import com.canerture.ecommercecm.data.model.response.BaseResponse

data class GetCategoriesResponse(
    val categories: List<String>?
) : BaseResponse()