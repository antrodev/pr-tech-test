package com.antrodev.printerview.network

import com.antrodev.printerview.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiManager {
    private var prServiceInstance: PRService? = null
    private var gsonConverterFactory: GsonConverterFactory? = null
    private var gson: Gson? = null

    /**
     * Get Photoroom service instance
     *
     * @param context Context
     * @return PR service instance
     */
    fun getPRServiceInstance(): PRService {
        if (prServiceInstance == null) {
            val httpClient: OkHttpClient.Builder = newOkHttpClient()
            val baseUrl = "https://interview.photoroom.com/"
            val retrofit: Retrofit = newRetrofit(httpClient, baseUrl, gsonConverter)
            prServiceInstance = retrofit.create(PRService::class.java)
        }
        return prServiceInstance!!
    }

    private fun newOkHttpClient(): OkHttpClient.Builder {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        val logging: HttpLoggingInterceptor = newLoggingInterceptor()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(ApiInterceptor())
        return httpClient
    }

    private fun newLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        val logLevel: HttpLoggingInterceptor.Level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        logging.level = logLevel
        return logging
    }

    private fun newRetrofit(
        httpClient: OkHttpClient.Builder,
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory?
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient.build())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    private val gsonConverter: GsonConverterFactory?
        get() {
            if (gsonConverterFactory == null) {
                val gson: Gson = getGson()
                gsonConverterFactory = GsonConverterFactory.create(gson)
            }
            return gsonConverterFactory
        }


    fun getGson(): Gson {
        if (gson == null) {
            val gsonBuilder = GsonBuilder()
            gson = gsonBuilder.create()
        }
        return gson!!
    }
}