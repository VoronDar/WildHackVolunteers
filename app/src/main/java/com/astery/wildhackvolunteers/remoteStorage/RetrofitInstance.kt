package com.astery.wildhack.remoteStorage

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class  RetrofitInstance @Inject constructor(){

    private val retrofit: Retrofit by lazy{

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .followRedirects(true)
            .build()


        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val api:ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }

    private val BASE_URL = "https://04a5-37-145-209-185.ngrok.io/api/"


}
