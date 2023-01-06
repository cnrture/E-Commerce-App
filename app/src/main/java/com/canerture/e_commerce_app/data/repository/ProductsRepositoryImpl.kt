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

        val favoriteNamesList = localDataSource.getFavoritesNamesList().orEmpty()

        return remoteDataSource.getProducts().map {
            with(it) {
                Product(
                    id, category, count, description, image, imageTwo, imageThree,
                    price, rate, title, saleState,
                    favoriteNamesList.contains(title),
                    if (saleState == 1) (price * 85) / 100 else null
                )
            }
        }
    }

    override suspend fun getSaleProducts(): List<Product> {

        val favoriteNamesList = localDataSource.getFavoritesNamesList().orEmpty()

        return remoteDataSource.getSaleProducts().map {
            with(it) {
                Product(
                    id, category, count, description, image, imageTwo, imageThree,
                    price, rate, title, saleState,
                    favoriteNamesList.contains(title),
                    (price * 85) / 100
                )
            }
        }
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

    override suspend fun getFavorites(): List<Product> = localDataSource.getFavorites()?.map {
        with(it) {
            Product(
                id, category, count, description, image, imageTwo, imageThree,
                price, rate, title, saleState,
                true,
                if (saleState == 1) (price * 85) / 100 else null
            )
        }
    } ?: arrayListOf()

    override suspend fun deleteFromFavorites(id: Int) = localDataSource.deleteFromFavorites(id)

    override suspend fun clearFavorites() = localDataSource.clearFavorites()

    override suspend fun getFavoritesNamesList() = localDataSource.getFavoritesNamesList()

}