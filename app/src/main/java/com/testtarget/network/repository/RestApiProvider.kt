package com.testtarget.network.repository


import com.testtarget.network.api.ApiConstants
import com.testtarget.network.api.RestService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by kuljeetsingh on 12/6/18.
 */

object RestApiProvider {
    val restApi: RestService
        get() {
            val okHttpClient = okHttpClient
            val retrofit = Retrofit.Builder()
                    .baseUrl(ApiConstants.GITHUB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

            return retrofit.create(RestService::class.java)
        }

    val okHttpClient: OkHttpClient
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient.Builder()
            builder.interceptors().add(httpLoggingInterceptor)
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.connectTimeout(60, TimeUnit.SECONDS)
            return builder.build()
        }
}
