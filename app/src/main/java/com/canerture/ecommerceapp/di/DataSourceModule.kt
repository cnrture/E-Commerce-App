package com.canerture.ecommerceapp.di

import com.canerture.ecommerceapp.data.source.local.LocalDataSourceImpl
import com.canerture.ecommerceapp.data.source.local.ProductFavoriteDAO
import com.canerture.ecommerceapp.data.source.remote.ProductService
import com.canerture.ecommerceapp.data.source.remote.RemoteDataSourceImpl
import com.canerture.ecommerceapp.domain.datasource.local.LocalDataSource
import com.canerture.ecommerceapp.domain.datasource.remote.RemoteDataSource
import com.canerture.ecommerceapp.domain.repository.Authenticator
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
        authenticator: Authenticator,
    ): RemoteDataSource = RemoteDataSourceImpl(productService, authenticator)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        productFavoriteDAO: ProductFavoriteDAO,
        ioDispatcher: CoroutineContext,
    ): LocalDataSource = LocalDataSourceImpl(productFavoriteDAO, ioDispatcher)

}