package com.laine.mauro.retrofitv1.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubServiceGenerator {

    companion object {
        val API_URL = "https://api.github.com/"

        private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        private val retrofitBuilder: Retrofit.Builder =
            Retrofit.Builder()
                .baseUrl(API_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())

        private val retrofit: Retrofit = retrofitBuilder.build()

        fun <S> createService(serviceClass: Class<S>): S {
            return retrofit.create(serviceClass)
        }
    }
}