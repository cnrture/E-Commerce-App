package com.canerture.e_commerce_app.di

import com.canerture.e_commerce_app.data.source.local.LocalDataSourceImpl
import com.canerture.e_commerce_app.data.source.local.ProductFavoriteDAO
import com.canerture.e_commerce_app.data.source.remote.ProductService
import com.canerture.e_commerce_app.data.source.remote.RemoteDataSourceImpl
import com.canerture.e_commerce_app.domain.datasource.local.LocalDataSource
import com.canerture.e_commerce_app.domain.datasource.remote.RemoteDataSource
import com.canerture.e_commerce_app.domain.repository.Authenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        productService: ProductService,
        authenticator: Authenticator
    ): RemoteDataSource =
        RemoteDataSourceImpl(productService, authenticator)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        productFavoriteDAO: ProductFavoriteDAO,
        ioDispatcher: CoroutineContext
    ): LocalDataSource = LocalDataSourceImpl(productFavoriteDAO, ioDispatcher)

}