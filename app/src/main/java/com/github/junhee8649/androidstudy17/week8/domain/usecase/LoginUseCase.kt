package com.github.junhee8649.androidstudy17.week8.domain.usecase

import com.github.junhee8649.androidstudy17.week8.domain.model.User
import com.github.junhee8649.androidstudy17.week8.domain.repository.UserRepository

/**
 * 로그인을 수행하는 유스케이스
 */
class LoginUseCase(private val userRepository: UserRepository) {
    /**
     * 학번과 비밀번호로 로그인 실행
     */
    suspend operator fun invoke(studentId: String, password: String): Result<User> {
        return userRepository.login(studentId, password)
    }
}