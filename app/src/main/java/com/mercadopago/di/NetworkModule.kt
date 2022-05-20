package com.mercadopago.di

import com.mercadopago.BuildConfig
import com.mercadopago.net.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun cocktailApiService(): ApiService {
        val converterFactory = GsonConverterFactory.create()

        val retrofitBuilder =  Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(converterFactory)

        val okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(5, TimeUnit.SECONDS)

            addInterceptor {
                val original: Request = it.request()
                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("public_key", BuildConfig.API_KEY)
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder().url(url)

                val request: Request = requestBuilder.build()
                it.proceed(request)
            }

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                addInterceptor(httpLoggingInterceptor)
            }
        }.build()
        retrofitBuilder.client(okHttpClient)



        return retrofitBuilder.build().create(ApiService::class.java)
    }

}