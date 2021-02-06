package com.barney.gnbapp.data.repository.datasource.tools

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GNBAppRetrofit {

    companion object {

        private val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl("https://quiet-stone-2094.herokuapp.com/")
            .client(getHTTPClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        private fun getHTTPClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        fun <T> getClient(x: Class<T>): T {
            return retrofit.create(x)
        }
    }
}