package com.github.junhee8649.androidstudy17.week8.data.remote

import android.content.Context
import com.github.junhee8649.androidstudy17.week8.data.local.TokenManager
import com.github.junhee8649.androidstudy17.week8.data.remote.api.ApiService
import com.github.junhee8649.androidstudy17.week8.data.util.Constants
import com.github.junhee8649.androidstudy17.week8.data.util.AuthInterceptor
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

    private var retrofit: Retrofit? = null

    /**
     * TokenManager를 사용하여 Retrofit 인스턴스를 초기화합니다.
     * 애플리케이션 시작 시 호출해야 합니다.
     */
    fun initialize(context: Context) {
        val tokenManager = TokenManager(context)

        // OkHttpClient에 AuthInterceptor 추가
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()

        // Retrofit 인스턴스 생성
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // API 서비스 생성
    val apiService: ApiService
        get() {
            if (retrofit == null) {
                throw IllegalStateException("RetrofitClient가 초기화되지 않았습니다. 앱 시작 시 initialize()를 호출해야 합니다.")
            }
            return retrofit!!.create(ApiService::class.java)
        }
}