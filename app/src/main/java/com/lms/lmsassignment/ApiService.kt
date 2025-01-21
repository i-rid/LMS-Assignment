package com.lms.lmsassignment

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://dummyjson.com"

interface ApiService {

    @GET("posts")
    suspend fun getPosts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Boolean

    object RetrofitInstance {
        val api: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }


        // interceptor
        private fun createOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY // Set log level as needed

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        private val retrofitWithInterceptor: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient()) // Set the custom OkHttpClient with the logging interceptor
                .build()
        }
        val apiWithInterceptor: ApiService by lazy {
            retrofitWithInterceptor.create(ApiService::class.java)
        }
    }
}