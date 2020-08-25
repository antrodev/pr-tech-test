package com.antrodev.printerview.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class ApiInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest: Request = chain.request()
        val requestWithUserAgent: Request = originRequest.newBuilder()
            .header("Content-Type", "application/json")
            .build()
        return chain.proceed(requestWithUserAgent)
    }

}