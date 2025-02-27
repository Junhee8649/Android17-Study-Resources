package com.github.junhee8649.androidstudy17.week8.data.repository

import com.github.junhee8649.androidstudy17.week8.data.TokenManager
import com.github.junhee8649.androidstudy17.week8.model.User
import com.github.junhee8649.androidstudy17.week8.network.MockLoginService
import com.github.junhee8649.androidstudy17.week8.network.dto.LoginRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * 사용자 관련 데이터 처리를 담당하는 Repository
 */
class UserRepository(
    private val loginService: MockLoginService,
    private val tokenManager: TokenManager
) {
    /**
     * 로그인 기능 수행 및 토큰 저장
     */
    suspend fun login(username: String, password: String): Result<User> {
        return try {
            // 요청 DTO 생성
            val request = LoginRequestDto(username, password)

            // 모의 API 호출
            val response = loginService.login(request)

            // 토큰 저장
            tokenManager.saveToken(response.token)

            // 사용자 정보 반환
            Result.success(
                User(
                    id = response.userId,
                    username = response.username,
                    email = response.email
                )
            )
        } catch (e: Exception) {
            // 에러 발생 시 실패 결과 반환
            Result.failure(e)
        }
    }

    /**
     * 저장된 토큰 조회 및 유효성 검증
     */
    suspend fun getCurrentUser(): User? {
        val token = tokenManager.getToken().first() ?: return null
        return loginService.validateToken(token)
    }

    /**
     * 로그아웃 (토큰 삭제)
     */
    suspend fun logout() {
        tokenManager.clearToken()
    }

    /**
     * 토큰 상태 관찰
     */
    fun observeToken(): Flow<String?> {
        return tokenManager.getToken()
    }
}