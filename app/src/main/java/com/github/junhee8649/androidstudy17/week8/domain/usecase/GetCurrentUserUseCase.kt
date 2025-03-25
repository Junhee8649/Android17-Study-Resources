package com.github.junhee8649.androidstudy17.week8.domain.usecase

import com.github.junhee8649.androidstudy17.week8.domain.model.User
import com.github.junhee8649.androidstudy17.week8.domain.repository.UserRepository

/**
 * 현재 로그인된 사용자 정보를 가져오는 유스케이스
 */
class GetCurrentUserUseCase(private val userRepository: UserRepository) {
    /**
     * 현재 로그인된 사용자 정보 조회 실행
     */
    suspend operator fun invoke(): User? {
        return userRepository.getCurrentUser()
    }
}