package com.github.junhee8649.androidstudy17.week8.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

/**
 * 로그인 화면 Compose UI
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit
) {
    // 상태 관찰
    val loginState by viewModel.loginState.collectAsState()

    // UI 상태 변수
    var studentId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // 로그인 성공 시 콜백 호출
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginViewModel.LoginState.Success -> {
                val user = (loginState as LoginViewModel.LoginState.Success).user
                onLoginSuccess(user.studentId)
            }
            is LoginViewModel.LoginState.Error -> {
                errorMessage = (loginState as LoginViewModel.LoginState.Error).message
            }
            else -> { /* 다른 상태는 처리하지 않음 */ }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 타이틀
        Text(
            text = "인천대학교 로그인",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 학번 입력
        OutlinedTextField(
            value = studentId,
            onValueChange = {
                studentId = it
                errorMessage = null // 입력 시 오류 메시지 초기화
            },
            label = { Text("학번") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // 비밀번호 입력
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null // 입력 시 오류 메시지 초기화
            },
            label = { Text("비밀번호") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // 로그인 버튼
        Button(
            onClick = {
                if (validateInputs(studentId, password)) {
                    viewModel.login(studentId, password)
                } else {
                    errorMessage = "학번과 비밀번호를 입력해주세요."
                }
            },
            enabled = loginState !is LoginViewModel.LoginState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("로그인")
        }

        // 로딩 상태 표시
        if (loginState is LoginViewModel.LoginState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }

        // 에러 메시지 표시
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

/**
 * 입력값 유효성 검사
 */
private fun validateInputs(studentId: String, password: String): Boolean {
    return studentId.isNotBlank() && password.isNotBlank()
}