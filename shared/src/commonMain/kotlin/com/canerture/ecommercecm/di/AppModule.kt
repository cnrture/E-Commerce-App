package com.canerture.ecommercecm.di

import com.canerture.ecommercecm.data.repository.ProductRepositoryImpl
import com.canerture.ecommercecm.data.source.remote.ProductService
import com.canerture.ecommercecm.domain.repository.ProductRepository
import com.canerture.ecommercecm.domain.usecase.GetAllProductsUseCase
import com.canerture.ecommercecm.domain.usecase.GetSaleProductsUseCase
import com.canerture.ecommercecm.domain.usecase.SearchProductUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val apiModule = module {
    factory { ProductService() }
}

private val repositoryModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}

private val useCaseModule = module {
    factory { GetAllProductsUseCase(get()) }
    factory { GetSaleProductsUseCase(get()) }
    factory { SearchProductUseCase(get()) }
}

private val sharedModules = listOf(apiModule, repositoryModule, useCaseModule)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(sharedModules)
}

fun initKoin() = initKoin { }