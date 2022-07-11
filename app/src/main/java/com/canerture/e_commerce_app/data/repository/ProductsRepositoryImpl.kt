package com.canerture.e_commerce_app.data.repository

import com.canerture.e_commerce_app.data.model.Product
import com.canerture.e_commerce_app.domain.datasource.local.LocalDataSource
import com.canerture.e_commerce_app.domain.datasource.remote.RemoteDataSource
import com.canerture.e_commerce_app.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {

        val productList = arrayListOf<Product>()
        val favoriteNamesList = localDataSource.getFavoritesNamesList().orEmpty()

        remoteDataSource.getProducts().forEach { product ->

            productList.add(
                Product(
                    product.id,
                    product.category,
                    product.count,
                    product.description,
                    product.image,
                    product.imageTwo,
                    product.imageThree,
                    product.price,
                    product.rate,
                    product.title,
                    product.saleState,
                    favoriteNamesList.contains(product.title),
                    if (product.saleState == 1) (product.price * 85) / 100 else null
                )
            )
        }
        return productList
    }

    override suspend fun getSaleProducts(): List<Product> {

        val saleProductList = arrayListOf<Product>()
        val favoriteNamesList = localDataSource.getFavoritesNamesList().orEmpty()

        remoteDataSource.getSaleProducts().forEach { product ->

            saleProductList.add(
                Product(
                    product.id,
                    product.category,
                    product.count,
                    product.description,
                    product.image,
                    product.imageTwo,
                    product.imageThree,
                    product.price,
                    product.rate,
                    product.title,
                    product.saleState,
                    favoriteNamesList.contains(product.title),
                    (product.price * 85) / 100
                )
            )
        }

        return saleProductList
    }

    override suspend fun addToBag(product: Product) = remoteDataSource.addToBag(product)

    override suspend fun getBagProducts() = remoteDataSource.getBagProducts()

    override suspend fun getBagProductsCount() = remoteDataSource.getBagProductsCount()

    override suspend fun deleteFromBag(id: Int) = remoteDataSource.deleteFromBag(id)

    override suspend fun getCategories() = remoteDataSource.getCategories()

    override suspend fun getProductsByCategory(category: String) =
        remoteDataSource.getProductsByCategory(category)

    override suspend fun clearBag() = remoteDataSource.clearBag()

    override suspend fun searchProduct(query: String): List<Product> {

        val productList = remoteDataSource.searchProduct(query)
        val tempList = arrayListOf<Product>()
        val favoriteNamesList = localDataSource.getFavoritesNamesList().orEmpty()

        for (i in 0..3) {
            productList.getOrNull(i)?.let {
                tempList.add(
                    Product(
                        it.id,
                        it.category,
                        it.count,
                        it.description,
                        it.image,
                        it.imageTwo,
                        it.imageThree,
                        it.price,
                        it.rate,
                        it.title,
                        it.saleState,
                        favoriteNamesList.contains(it.title),
                        if (it.saleState == 1) (it.price * 85) / 100 else null
                    )
                )
            }
        }
        return tempList
    }

    override suspend fun addToFavorites(product: Product) = localDataSource.addToFavorites(product)

    override suspend fun getFavorites(): List<Product> {

        val favoriteList = arrayListOf<Product>()

        localDataSource.getFavorites()?.forEach { product ->

            favoriteList.add(
                Product(
                    product.id,
                    product.category,
                    product.count,
                    product.description,
                    product.image,
                    product.imageTwo,
                    product.imageThree,
                    product.price,
                    product.rate,
                    product.title,
                    product.saleState,
                    true,
                    if (product.saleState == 1) (product.price * 85) / 100 else null
                )
            )
        }

        return favoriteList
    }

    override suspend fun deleteFromFavorites(id: Int) = localDataSource.deleteFromFavorites(id)

    override suspend fun clearFavorites() = localDataSource.clearFavorites()

    override suspend fun getFavoritesNamesList() = localDataSource.getFavoritesNamesList()

}