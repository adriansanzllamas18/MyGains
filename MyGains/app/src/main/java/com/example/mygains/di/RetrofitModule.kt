package com.example.mygains.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule() {

    @Singleton
    @Provides
    fun getRetrofit():Retrofit{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Asegúrate de que está en nivel BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/api/v0/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}