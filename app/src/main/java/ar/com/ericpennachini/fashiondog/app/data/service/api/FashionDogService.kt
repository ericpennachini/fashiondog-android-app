package ar.com.ericpennachini.fashiondog.app.data.service.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.http.HttpMethod

class FashionDogService {

    private val client: HttpClient = HttpClient(OkHttp) {
        install(Logging)
    }

    suspend fun getAllCustomers() = client.request("http://127.0.0.1/customers") {
        method = HttpMethod.Get
    }



}