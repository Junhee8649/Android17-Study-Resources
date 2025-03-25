package com.github.junhee8649.androidstudy17.week8.data.repository

import android.util.Log
import com.github.junhee8649.androidstudy17.week8.data.local.TokenManager
import com.github.junhee8649.androidstudy17.week8.data.remote.api.ApiService
import com.github.junhee8649.androidstudy17.week8.data.remote.dto.LoginRequestDto
import com.github.junhee8649.androidstudy17.week8.domain.model.User
import com.github.junhee8649.androidstudy17.week8.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * 사용자 관련 데이터 처리를 담당하는 Repository 구현체
 */
class UserRepositoryImpl(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : UserRepository {
    /**
     * 로그인 기능 수행 및 토큰 저장
     */
    override suspend fun login(studentId: String, password: String): Result<User> {
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
            // 에러 발생 시 실패 결과 반환
            Result.failure(e)
        }
    }

    /**
     * 저장된 토큰 및 학번 조회하여 사용자 정보 반환
     */
    override suspend fun getCurrentUser(): User? {
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
    override suspend fun logout() {
        tokenManager.clearAll()
    }

    /**
     * 학번 상태 관찰
     */
    override fun observeStudentId(): Flow<String?> {
        return tokenManager.getStudentId()
    }
}