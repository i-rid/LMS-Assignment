package com.lms.lmsassignment.data.remote.api

import com.lms.lmsassignment.data.model.BattingResponse
import com.lms.lmsassignment.data.model.BowlingResponse
import com.lms.lmsassignment.data.model.SquadResponse
import com.lms.lmsassignment.data.model.SummaryResponse
import com.lms.lmsassignment.utils.Const.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("GetWorldTeamProfileSummery")
    suspend fun getSummary(
        @Query("teamId") teamId: Int
    ): ResponseBody

    @GET("GetWorldTeamProfile")
    suspend fun getSquadList(
        @Query("typeId") typeId: Int,
        @Query("teamId") teamId: Int
    ): List<SquadResponse>

    @GET("GetWorldTeamProfile")
    suspend fun getBattingList(
        @Query("typeId") typeId: Int,
        @Query("teamId") teamId: Int
    ): List<BattingResponse>

    @GET("GetWorldTeamProfile")
    suspend fun getBowlingList(
        @Query("typeId") typeId: Int,
        @Query("teamId") teamId: Int
    ): List<BowlingResponse>


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