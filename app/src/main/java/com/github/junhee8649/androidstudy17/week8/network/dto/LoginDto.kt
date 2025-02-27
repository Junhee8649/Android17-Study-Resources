package com.github.junhee8649.androidstudy17.week8.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 로그인 요청 DTO
 */
@Serializable
data class LoginRequestDto(
    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String
)

/**
 * 로그인 응답 DTO
 */
@Serializable
data class LoginResponseDto(
    @SerialName("token")
    val token: String,

    @SerialName("user_id")
    val userId: String,

    @SerialName("username")
    val username: String,

    @SerialName("email")
    val email: String
)