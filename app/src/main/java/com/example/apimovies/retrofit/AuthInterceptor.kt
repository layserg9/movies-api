package com.example.apimovies.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-API-KEY", "FK6PAAJ-243MM4N-PDWTYTD-0CFVG31")
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}