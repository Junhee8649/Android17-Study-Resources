package com.github.junhee8649.androidstudy17.week8.network

import com.github.junhee8649.androidstudy17.week8.model.User
import com.github.junhee8649.androidstudy17.week8.network.dto.LoginRequestDto
import com.github.junhee8649.androidstudy17.week8.network.dto.LoginResponseDto
import com.github.junhee8649.androidstudy17.week8.util.Constants
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import kotlinx.coroutines.delay
import java.util.Date
import javax.crypto.SecretKey

/**
 * 모의 로그인 서비스 - 실제 서버 없이 JWT 토큰 생성
 */
class MockLoginService {

    // JWT 서명을 위한 비밀 키 생성
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    /**
     * 모의 로그인 - 네트워크 요청 없이 JWT 토큰 생성
     */
    suspend fun login(request: LoginRequestDto): LoginResponseDto {
        // 실제 네트워크 지연 시뮬레이션
        delay(1000)

        val username = request.username
        val password = request.password

        // 간단한 사용자 검증 (실제로는 서버에서 수행)
        if (username.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("사용자 이름과 비밀번호를 입력해주세요.")
        }

        // 특정 테스트 사용자 이외의 로그인 실패 시뮬레이션
        if (username != "test" && username != "admin" && username != "user") {
            throw IllegalArgumentException("사용자를 찾을 수 없습니다.")
        }

        // JWT 토큰 생성
        val userId = when (username) {
            "admin" -> "admin_123"
            "test" -> "test_456"
            else -> "user_789"
        }

        val email = "$username@example.com"
        val token = generateJwtToken(userId, username, email)

        // 로그인 응답 생성
        return LoginResponseDto(
            token = token,
            userId = userId,
            username = username,
            email = email
        )
    }

    /**
     * JWT 토큰 생성
     */
    private fun generateJwtToken(userId: String, username: String, email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + Constants.TOKEN_EXPIRATION_TIME)

        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .claim("username", username)
            .claim("email", email)
            .signWith(secretKey)
            .compact()
    }

    /**
     * JWT 토큰 검증 및 파싱
     */
    fun validateToken(token: String): User? {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body

            val userId = claims.subject
            val username = claims["username"] as String
            val email = claims["email"] as String

            User(
                id = userId,
                username = username,
                email = email
            )
        } catch (e: Exception) {
            // 토큰 검증 실패
            null
        }
    }
}
