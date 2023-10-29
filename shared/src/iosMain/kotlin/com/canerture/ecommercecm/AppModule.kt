package com.canerture.ecommercecm

import com.canerture.ecommercecm.di.initKoin
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

fun initKoin() {
    initKoin {
        modules(appModule)
    }
}

val appModule = module {
    single { Darwin.create() }
}