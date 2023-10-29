package com.canerture.ecommercecm

import io.ktor.client.engine.android.Android
import org.koin.dsl.module

val appModule = module {
    single { Android.create() }
}