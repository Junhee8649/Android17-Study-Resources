package com.github.junhee8649.androidstudy17.week8.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * 메인 화면 Compose UI
 */
@Composable
fun MainScreen(
    studentId: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        // 환영 메시지
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${studentId}님 환영합니다!",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "로그인 성공! 토큰이 DataStore에 안전하게 저장되었습니다.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Text(
                text = "이제 앱을 종료하고 다시 실행해도 자동 로그인됩니다.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "JWT 인증 특징",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text("• 서버에 별도 요청 없이 로컬에서 인증 검증 가능")
                    Text("• 토큰 내에 사용자 정보와 권한 정보 포함")
                    Text("• DataStore에 안전하게 저장되어 앱 재시작 시에도 유지")
                    Text("• 만료 시간 설정으로 보안 강화")
                    Text("• 서버와 클라이언트 간 상태를 공유하지 않는 Stateless 인증")
                }
            }
        }

        // 로그아웃 버튼
        Button(
            onClick = onLogout,
            modifier = Modifier
                .padding(bottom = 64.dp)
                .width(200.dp)
                .height(50.dp)
        ) {
            Text("로그아웃")
        }
    }
}