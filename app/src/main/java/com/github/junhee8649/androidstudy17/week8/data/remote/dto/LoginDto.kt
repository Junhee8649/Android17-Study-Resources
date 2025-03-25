package com.github.junhee8649.androidstudy17.week8.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 로그인 요청 DTO
 */
@Serializable
data class LoginRequestDto(
    @SerialName("studentId")
    val studentId: String,
    @SerialName("password")
    val password: String
)

/**
 * 로그인 응답 DTO
 */
@Serializable
data class LoginResponseDto(
    @SerialName("message")
    val message: String,

    @SerialName("response")
    val token: String
)