package com.github.junhee8649.androidstudy17

import android.os.Bundle
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
import com.github.junhee8649.androidstudy17.week8.data.TokenManager
import com.github.junhee8649.androidstudy17.week8.data.repository.UserRepository
import com.github.junhee8649.androidstudy17.week8.network.MockLoginService
import com.github.junhee8649.androidstudy17.week8.ui.login.LoginScreen
import com.github.junhee8649.androidstudy17.week8.ui.login.LoginViewModel
import com.github.junhee8649.androidstudy17.week8.ui.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 의존성 수동 생성 (의존성 주입 없이)
        val mockLoginService = MockLoginService()
        val tokenManager = TokenManager(applicationContext)
        val userRepository = UserRepository(mockLoginService, tokenManager)
        val loginViewModel = LoginViewModel(userRepository)

        setContent {
            // A surface container using the 'background' color from the theme
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
    var username by remember { mutableStateOf("") }

    // 로그인 상태 관찰
    val loginState by loginViewModel.loginState.collectAsState()

    // 로그인 상태에 따른 화면 전환 처리
    LaunchedEffect(loginState) {
        if (loginState is LoginViewModel.LoginState.Success) {
            val user = (loginState as LoginViewModel.LoginState.Success).user
            username = user.username
            isLoggedIn = true
        } else {
            isLoggedIn = false
        }
    }

    // 화면 전환 처리
    if (isLoggedIn) {
        MainScreen(
            username = username,
            onLogout = {
                loginViewModel.logout()
            }
        )
    } else {
        LoginScreen(
            viewModel = loginViewModel,
            onLoginSuccess = { loggedInUsername ->
                username = loggedInUsername
                isLoggedIn = true
            }
        )
    }
}