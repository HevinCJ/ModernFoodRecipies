package com.example.modernfoodrecipe.di

import com.example.modernfoodrecipe.data.Network.FoodRecepiesApi
import com.example.modernfoodrecipe.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient():OkHttpClient{
        return OkHttpClient().newBuilder()
            .readTimeout(15,TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS).build()
    }


    @Singleton
    @Provides
    fun provideGsonConvertorFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient,gsonConverterFactory: GsonConverterFactory):Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(gsonConverterFactory).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FoodRecepiesApi {
         return  retrofit.create(FoodRecepiesApi::class.java)
    }

}