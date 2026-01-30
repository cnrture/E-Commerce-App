package com.canerture.ecommerceapp.di

import com.canerture.ecommerceapp.data.repository.ProductsRepositoryImpl
import com.canerture.ecommerceapp.domain.datasource.local.LocalDataSource
import com.canerture.ecommerceapp.domain.datasource.remote.RemoteDataSource
import com.canerture.ecommerceapp.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductsRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
    ): ProductsRepository = ProductsRepositoryImpl(remoteDataSource, localDataSource)
}