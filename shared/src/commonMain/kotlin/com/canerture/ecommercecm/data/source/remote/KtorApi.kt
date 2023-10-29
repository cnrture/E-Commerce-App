package com.canerture.ecommercecm.data.source.remote

import com.canerture.ecommercecm.common.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlin.native.concurrent.SharedImmutable
import kotlinx.serialization.json.Json

abstract class KtorApi {
    val client = httpClient

    fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            header("store", "canerture")
            takeFrom(BASE_URL)
            path(path)
        }
    }
}

private val jsonConfiguration
    get() = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        useAlternativeNames = false
    }

@SharedImmutable
private val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(jsonConfiguration)
    }
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
}