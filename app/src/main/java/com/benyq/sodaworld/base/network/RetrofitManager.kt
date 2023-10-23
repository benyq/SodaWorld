package com.benyq.sodaworld.base.network

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @author benyq
 * @date 8/30/2023
 *
 */
object RetrofitManager {
    fun <T> create(baseUrl: String, clazz: Class<T>, init: (OkHttpClient.Builder)->Unit = {}): T {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("benyq", "okhttp: $message")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            init(this)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }.build()

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
        val retrofit = retrofitBuilder.build()
        return retrofit.create(clazz)
    }

}