package com.github.junhee8649.androidstudy17.week8.data.remote.api

import com.github.junhee8649.androidstudy17.week8.data.remote.dto.LoginRequestDto
import com.github.junhee8649.androidstudy17.week8.data.remote.dto.LoginResponseDto
import com.github.junhee8649.androidstudy17.week8.data.util.Constants
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit API 서비스 인터페이스
 */
interface ApiService {
    @POST(Constants.LOGIN_ENDPOINT)
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto
}