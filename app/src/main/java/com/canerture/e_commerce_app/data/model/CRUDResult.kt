package com.canerture.e_commerce_app.data.model

sealed class CRUDResult {
    data class Success(val success: Int) : CRUDResult()
    data class Error(val message: String) : CRUDResult()
}