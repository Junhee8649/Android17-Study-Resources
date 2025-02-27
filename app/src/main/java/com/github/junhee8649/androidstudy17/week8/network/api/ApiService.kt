package com.github.junhee8649.androidstudy17.week8.network.api

import com.github.junhee8649.androidstudy17.week8.network.dto.LoginRequestDto
import com.github.junhee8649.androidstudy17.week8.network.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit API 서비스 인터페이스
 */
interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}