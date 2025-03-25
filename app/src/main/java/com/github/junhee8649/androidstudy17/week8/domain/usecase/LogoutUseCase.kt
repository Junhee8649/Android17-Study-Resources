package com.github.junhee8649.androidstudy17.week8.domain.usecase

import com.github.junhee8649.androidstudy17.week8.domain.repository.UserRepository

/**
 * 로그아웃을 수행하는 유스케이스
 */
class LogoutUseCase(private val userRepository: UserRepository) {
    /**
     * 로그아웃 실행
     */
    suspend operator fun invoke() {
        userRepository.logout()
    }
}