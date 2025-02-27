package com.github.junhee8649.androidstudy17.week8.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.junhee8649.androidstudy17.week8.data.repository.UserRepository
import com.github.junhee8649.androidstudy17.week8.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 로그인 화면의 ViewModel
 */
class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    // 로그인 상태를 나타내는 StateFlow
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    // 로그인 상태를 나타내는 sealed class
    sealed class LoginState {
        object Initial : LoginState()
        object Loading : LoginState()
        data class Success(val user: User) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    init {
        // 앱 시작 시 저장된 토큰이 있는지 확인하고 유효한지 검증
        checkSavedToken()
    }

    /**
     * 저장된 토큰 확인 및 자동 로그인
     */
    private fun checkSavedToken() {
        viewModelScope.launch {
            try {
                val currentUser = userRepository.getCurrentUser()
                if (currentUser != null) {
                    _loginState.value = LoginState.Success(currentUser)
                }
            } catch (e: Exception) {
                // 토큰이 없거나, 만료되었거나, 유효하지 않은 경우
                // 초기 상태를 유지하고 사용자에게 로그인 요청
            }
        }
    }

    /**
     * 로그인 함수
     */
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val result = userRepository.login(username, password)

            result.fold(
                onSuccess = { user ->
                    _loginState.value = LoginState.Success(user)
                },
                onFailure = { e ->
                    _loginState.value = LoginState.Error(e.message ?: "로그인에 실패했습니다.")
                }
            )
        }
    }

    /**
     * 로그아웃 함수
     */
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _loginState.value = LoginState.Initial
        }
    }
}