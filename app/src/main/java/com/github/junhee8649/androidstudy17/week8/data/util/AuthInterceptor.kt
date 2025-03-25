package com.github.junhee8649.androidstudy17.week8.data.util

import com.github.junhee8649.androidstudy17.week8.data.local.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 모든 API 요청에 인증 토큰을 자동으로 추가하는 인터셉터
 */
class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 원본 요청
        val originalRequest = chain.request()

        // 토큰 가져오기 (동기식으로 Flow에서 첫 값 가져오기)
        val token = runBlocking { tokenManager.getToken().first() }

        // 토큰이 없으면 원래 요청 그대로 진행
        if (token == null) {
            return chain.proceed(originalRequest)
        }

        // 토큰이 있으면 Authorization 헤더 추가
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        // 변경된 요청으로 진행
        return chain.proceed(authorizedRequest)
    }
}