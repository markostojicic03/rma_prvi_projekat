package com.rma.catalist.networking.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rma.catalist.networking.serialization.NetworkingJson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        /*
         * Order of okhttp interceptors is important.
         * If logging was first it would not log the custom header.
         */
        .addInterceptor {
            val updatedRequest = it.request().newBuilder()
                .addHeader("x-api-key", "live_p3R34PXrOOcunXlvMUa1nAHwC9tEUS5t15kbzlqZ2kVRSKVbIG0i4mQ0vSoWcpHu")
                .build()

            it.proceed(updatedRequest)
        }
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        )
        .build()

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(okHttpClient)
            .addConverterFactory(NetworkingJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
