package com.canerture.ecommercecm.data.repository

import com.canerture.ecommercecm.common.Resource
import com.canerture.ecommercecm.data.model.response.BaseResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

abstract class BaseRepository {
    suspend inline fun <reified T : Any> safeApiCall(
        crossinline apiToBeCalled: suspend () -> HttpResponse
    ): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = apiToBeCalled()

                val json = Json {
                    ignoreUnknownKeys = true
                }

                val baseResponse = json.decodeFromString<BaseResponse>(response.toString())

                if (baseResponse.status == 200) {
                    val result = json.decodeFromString<T>(response.body())
                    Resource.Success(result)
                } else {
                    Resource.Fail(baseResponse.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(Throwable("Something went wrong"))
            }
        }
    }
}
