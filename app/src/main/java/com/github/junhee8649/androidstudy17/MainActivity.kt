package com.github.junhee8649.androidstudy17

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.junhee8649.androidstudy17.ui.theme.AndroidStudy17Theme
import com.github.junhee8649.androidstudy17.week8.data.local.TokenManager
import com.github.junhee8649.androidstudy17.week8.domain.repository.UserRepository
import com.github.junhee8649.androidstudy17.week8.data.remote.RetrofitClient
import com.github.junhee8649.androidstudy17.week8.data.repository.UserRepositoryImpl
import com.github.junhee8649.androidstudy17.week8.domain.usecase.GetCurrentUserUseCase
import com.github.junhee8649.androidstudy17.week8.domain.usecase.LoginUseCase
import com.github.junhee8649.androidstudy17.week8.domain.usecase.LogoutUseCase
import com.github.junhee8649.androidstudy17.week8.ui.login.LoginScreen
import com.github.junhee8649.androidstudy17.week8.ui.login.LoginViewModel
import com.github.junhee8649.androidstudy17.week8.ui.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TokenDebug", "MainActivity: 앱 시작")

        // TokenManager 초기화
        val tokenManager = TokenManager(applicationContext)

        // RetrofitClient 초기화 (AuthInterceptor 포함)
        RetrofitClient.initialize(applicationContext)

        // 리포지토리 초기화
        val userRepository = UserRepositoryImpl(
            RetrofitClient.apiService,
            tokenManager
        )

        // 유스케이스 초기화
        val loginUseCase = LoginUseCase(userRepository)
        val getCurrentUserUseCase = GetCurrentUserUseCase(userRepository)
        val logoutUseCase = LogoutUseCase(userRepository)

        // ViewModel 초기화
        val loginViewModel = LoginViewModel(
            loginUseCase,
            getCurrentUserUseCase,
            logoutUseCase
        )

        setContent {
            AndroidStudy17Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoginApp(loginViewModel)
                }
            }
        }
    }
}

/**
 * 앱의 메인 Composable 함수
 */
@Composable
fun LoginApp(loginViewModel: LoginViewModel) {
    // 로그인 상태 관리
    var isLoggedIn by remember { mutableStateOf(false) }
    var studentId by remember { mutableStateOf("") }

    // 로그인 상태 관찰
    val loginState by loginViewModel.loginState.collectAsState()

    // 로그인 상태에 따른 화면 전환 처리
    LaunchedEffect(loginState) {
        if (loginState is LoginViewModel.LoginState.Success) {
            val user = (loginState as LoginViewModel.LoginState.Success).user
            studentId = user.studentId
            isLoggedIn = true
        } else {
            isLoggedIn = false
        }
    }

    // 화면 전환 처리
    if (isLoggedIn) {
        MainScreen(
            studentId = studentId,
            onLogout = {
                loginViewModel.logout()
            }
        )
    } else {
        LoginScreen(
            viewModel = loginViewModel,
            onLoginSuccess = { loggedInStudentId ->
                studentId = loggedInStudentId
                isLoggedIn = true
            }
        )
    }
}