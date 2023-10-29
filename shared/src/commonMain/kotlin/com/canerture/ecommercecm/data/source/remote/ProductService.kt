package com.canerture.ecommercecm.data.source.remote

import com.canerture.ecommercecm.common.Constants
import com.canerture.ecommercecm.data.model.request.AddToCartRequest
import com.canerture.ecommercecm.data.model.request.ClearCartRequest
import com.canerture.ecommercecm.data.model.request.DeleteFromCartRequest
import com.canerture.ecommercecm.data.source.remote.KtorApi
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ProductService : KtorApi() {

    suspend fun addToCart(addToCartRequest: AddToCartRequest) =
        client.post {
            apiUrl(Constants.EndPoints.ADD_TO_CART)
            setBody(addToCartRequest)
        }

    suspend fun deleteFromCart(deleteFromCartRequest: DeleteFromCartRequest) =
        client.post {
            apiUrl(Constants.EndPoints.DELETE_FROM_CART)
            setBody(deleteFromCartRequest)
        }

    suspend fun getCartProducts(userId: String) =
        client.get {
            apiUrl(Constants.EndPoints.GET_CART_PRODUCTS)
            parameter("userId", userId)
        }

    suspend fun clearCart(clearCartRequest: ClearCartRequest) =
        client.post {
            apiUrl(Constants.EndPoints.CLEAR_CART)
            setBody(clearCartRequest)
        }

    suspend fun getProducts() =
        client.get {
            apiUrl(Constants.EndPoints.GET_PRODUCTS)
        }

    suspend fun getProductsByCategory(category: String) =
        client.get {
            apiUrl(Constants.EndPoints.GET_PRODUCTS_BY_CATEGORY)
            parameter("category", category)
        }

    suspend fun getSaleProducts() =
        client.get {
            apiUrl(Constants.EndPoints.GET_SALE_PRODUCTS)
        }

    suspend fun searchProduct(query: String) =
        client.get {
            apiUrl(Constants.EndPoints.SEARCH_PRODUCT)
            parameter("query", query)
        }

    suspend fun getCategories() =
        client.get {
            apiUrl(Constants.EndPoints.GET_CATEGORIES)
        }

    suspend fun getProductDetail(id: Int) =
        client.get {
            apiUrl(Constants.EndPoints.GET_PRODUCT_DETAIL)
            parameter("id", id)
        }
}