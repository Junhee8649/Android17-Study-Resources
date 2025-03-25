package com.github.junhee8649.androidstudy17.week8.data.repository

import android.util.Log
import com.github.junhee8649.androidstudy17.week8.data.local.TokenManager
import com.github.junhee8649.androidstudy17.week8.data.model.User
import com.github.junhee8649.androidstudy17.week8.data.remote.api.ApiService
import com.github.junhee8649.androidstudy17.week8.data.remote.dto.LoginRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * 사용자 인증 및 데이터를 관리하는 Repository
 * 사용자 데이터의 단일 진실 공급원(Single Source of Truth) 역할
 */
class UserRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    /**
     * 학번과 비밀번호로 로그인
     */
    suspend fun login(studentId: String, password: String): Result<User> {
        return try {
            // 요청 DTO 생성
            val request = LoginRequestDto(studentId, password)

            // API 호출
            val response = apiService.login(request)

            Log.d("TokenDebug", "로그인 성공: ${response.message}")
            Log.d("TokenDebug", "받은 토큰: ${response.token.take(20)}...")

            // 토큰 저장
            tokenManager.saveToken(response.token)
            // 학번 저장
            tokenManager.saveStudentId(studentId)

            // 사용자 정보 반환
            Result.success(User(studentId))
        } catch (e: Exception) {
            Log.e("TokenDebug", "로그인 실패: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * 현재 로그인된 사용자 정보 조회
     */
    suspend fun getCurrentUser(): User? {
        val token = tokenManager.getToken().first()
        val studentId = tokenManager.getStudentId().first()

        // 토큰과 학번이 모두 있으면 사용자 인증됨으로 간주
        return if (token != null && studentId != null) {
            User(studentId)
        } else {
            null
        }
    }

    /**
     * 로그아웃 (토큰 및 사용자 정보 삭제)
     */
    suspend fun logout() {
        tokenManager.clearAll()
    }

    /**
     * 학번 상태 관찰
     */
    fun observeStudentId(): Flow<String?> {
        return tokenManager.getStudentId()
    }
}