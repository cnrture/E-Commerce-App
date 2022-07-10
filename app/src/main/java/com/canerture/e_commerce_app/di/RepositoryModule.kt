package com.canerture.e_commerce_app.di

import com.canerture.e_commerce_app.data.repository.ProductsRepositoryImpl
import com.canerture.e_commerce_app.domain.datasource.local.LocalDataSource
import com.canerture.e_commerce_app.domain.datasource.remote.RemoteDataSource
import com.canerture.e_commerce_app.domain.repository.ProductsRepository
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
        localDataSource: LocalDataSource
    ): ProductsRepository = ProductsRepositoryImpl(remoteDataSource, localDataSource)
}