package com.github.junhee8649.androidstudy17.week8.domain.repository


import com.github.junhee8649.androidstudy17.week8.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * 사용자 관련 데이터 접근을 위한 도메인 계층 리포지토리 인터페이스
 */
interface UserRepository {
    /**
     * 학번과 비밀번호로 로그인
     */
    suspend fun login(studentId: String, password: String): Result<User>

    /**
     * 현재 로그인된 사용자 정보 조회
     */
    suspend fun getCurrentUser(): User?

    /**
     * 로그아웃 (저장된 토큰 및 사용자 정보 삭제)
     */
    suspend fun logout()

    /**
     * 학번 데이터 관찰
     */
    fun observeStudentId(): Flow<String?>
}