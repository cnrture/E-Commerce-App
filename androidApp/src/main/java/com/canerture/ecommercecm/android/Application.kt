package com.canerture.ecommercecm.android

import android.app.Application
import com.canerture.ecommercecm.appModule
import com.canerture.ecommercecm.di.initKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            modules(appModule)
        }
    }
}