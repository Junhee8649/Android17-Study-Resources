package com.github.junhee8649.androidstudy17.week8.network

import com.github.junhee8649.androidstudy17.week8.network.api.ApiService
import com.github.junhee8649.androidstudy17.week8.util.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Retrofit 클라이언트 설정을 담당하는 객체
 */
object RetrofitClient {

    // kotlinx.serialization JSON 설정
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    // OkHttpClient 설정
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Retrofit 인스턴스 생성
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    // API 서비스 생성
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}