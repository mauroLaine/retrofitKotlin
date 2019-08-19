package com.laine.mauro.retrofitv1.data

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubServiceGenerator {

    companion object {
        val API_URL = "https://api.github.com/"

        private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        private val retrofitBuilder: Retrofit.Builder =
            Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())

        private lateinit var retrofit: Retrofit

        fun <S> createService(serviceClass: Class<S>): S {
            retrofitBuilder.client(httpClient.build())
            retrofit = retrofitBuilder.build()
            return retrofit.create(serviceClass)
        }

        fun <S> createService(serviceClass: Class<S>, token: String): S {
            token.let {
                httpClient.interceptors().clear()
                httpClient.addInterceptor { chain ->
                    val original = chain.request()
                    val request: Request = original.newBuilder()
                        .header("Authorization", token).build()
                    return@addInterceptor chain.proceed(request)
                }
            }
            retrofitBuilder.client(httpClient.build())
            retrofit = retrofitBuilder.build()
            return retrofit.create(serviceClass)
        }
    }
}