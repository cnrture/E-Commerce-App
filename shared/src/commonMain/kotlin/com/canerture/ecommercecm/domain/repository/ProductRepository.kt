package com.canerture.ecommercecm.domain.repository

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.response.BaseResponse
import com.canerture.ecommercecm.data.model.response.GetCartProductsResponse
import com.canerture.ecommercecm.data.model.response.GetCategoriesResponse
import com.canerture.ecommercecm.data.model.response.GetProductDetailResponse
import com.canerture.ecommercecm.data.model.response.GetProductsByCategoryResponse
import com.canerture.ecommercecm.data.model.response.GetProductsResponse
import com.canerture.ecommercecm.data.model.response.GetSaleProductsResponse
import com.canerture.ecommercecm.data.model.response.ProductUI
import com.canerture.ecommercecm.data.model.response.SearchProductResponse

interface ProductRepository {

    suspend fun getAllProducts(): Resource<GetProductsResponse>

    suspend fun getSaleProducts(): Resource<GetSaleProductsResponse>

    suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse>

    suspend fun getCartProducts(userId: String): Resource<GetCartProductsResponse>

    suspend fun addToCart(userId: String, id: Int): Resource<BaseResponse>

    suspend fun deleteFromCart(id: Int): Resource<BaseResponse>

    suspend fun clearCart(userId: String): Resource<BaseResponse>

    suspend fun getProductsByCategory(category: String): Resource<GetProductsByCategoryResponse>

    suspend fun searchProduct(query: String): Resource<SearchProductResponse>

    suspend fun getCategories(): Resource<GetCategoriesResponse>

    suspend fun getFavoriteTitles(): List<String>

    suspend fun getFavorites(): Resource<List<ProductUI>>

    suspend fun addToFavorites(product: ProductUI)

    suspend fun deleteFromFavorites(product: ProductUI)

    suspend fun clearFavorites()
}