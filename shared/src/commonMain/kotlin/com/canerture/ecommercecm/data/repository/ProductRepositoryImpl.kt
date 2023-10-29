package com.canerture.ecommercecm.data.repository

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.request.AddToCartRequest
import com.canerture.ecommercecm.data.model.request.ClearCartRequest
import com.canerture.ecommercecm.data.model.request.DeleteFromCartRequest
import com.canerture.ecommercecm.data.model.response.BaseResponse
import com.canerture.ecommercecm.data.model.response.GetCartProductsResponse
import com.canerture.ecommercecm.data.model.response.GetCategoriesResponse
import com.canerture.ecommercecm.data.model.response.GetProductDetailResponse
import com.canerture.ecommercecm.data.model.response.GetProductsByCategoryResponse
import com.canerture.ecommercecm.data.model.response.GetProductsResponse
import com.canerture.ecommercecm.data.model.response.GetSaleProductsResponse
import com.canerture.ecommercecm.data.model.response.ProductUI
import com.canerture.ecommercecm.data.model.response.SearchProductResponse
import com.canerture.ecommercecm.data.source.remote.ProductService
import com.canerture.ecommercecm.domain.repository.ProductRepository
import io.ktor.client.call.body
import io.ktor.client.statement.readBytes
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class ProductRepositoryImpl(
    private val service: ProductService
) : KoinComponent, ProductRepository, BaseRepository() {

    override suspend fun getAllProducts(): Resource<GetProductsResponse> =
        safeApiCall { service.getProducts() }

    override suspend fun getSaleProducts(): Resource<GetSaleProductsResponse> =
        safeApiCall { service.getSaleProducts() }

    override suspend fun getProductDetail(id: Int): Resource<GetProductDetailResponse> =
        safeApiCall { service.getProductDetail(id) }

    override suspend fun getCartProducts(userId: String): Resource<GetCartProductsResponse> =
        safeApiCall { service.getCartProducts(userId) }

    override suspend fun addToCart(userId: String, id: Int): Resource<BaseResponse> =
        safeApiCall { service.addToCart(AddToCartRequest(userId, id)) }

    override suspend fun deleteFromCart(id: Int): Resource<BaseResponse> =
        safeApiCall { service.deleteFromCart(DeleteFromCartRequest(id)) }

    override suspend fun clearCart(userId: String): Resource<BaseResponse> =
        safeApiCall { service.clearCart(ClearCartRequest(userId)) }

    override suspend fun getProductsByCategory(category: String): Resource<GetProductsByCategoryResponse> =
        safeApiCall { service.getProductsByCategory(category) }

    override suspend fun searchProduct(query: String): Resource<SearchProductResponse> =
        safeApiCall { service.searchProduct(query) }

    override suspend fun getCategories(): Resource<GetCategoriesResponse> =
        safeApiCall { service.getCategories() }

    override suspend fun getFavoriteTitles(): List<String> {
        return emptyList()
    }

    override suspend fun getFavorites(): Resource<List<ProductUI>> {
        return Resource.Success(emptyList())
    }

    override suspend fun addToFavorites(product: ProductUI) {
        // no-op
    }

    override suspend fun deleteFromFavorites(product: ProductUI) {
        // no-op
    }

    override suspend fun clearFavorites() {
        // no-op
    }
}