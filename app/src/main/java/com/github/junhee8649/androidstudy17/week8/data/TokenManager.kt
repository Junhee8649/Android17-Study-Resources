package com.github.junhee8649.androidstudy17.week8.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.github.junhee8649.androidstudy17.week8.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Context의 확장 프로퍼티로 DataStore 인스턴스 생성
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

/**
 * TokenManager 클래스: 로그인 토큰의 저장, 조회, 삭제를 담당
 */
class TokenManager(private val context: Context) {

    companion object {
        // 토큰 저장을 위한 키
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    /**
     * 토큰을 DataStore에 저장합니다.
     * @param token 서버에서 받은 인증 토큰
     */
    suspend fun saveToken(token: String) {
        Log.d("TokenDebug", "TokenManager: 토큰 저장 시작")
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
        Log.d("TokenDebug", "TokenManager: 토큰 저장 완료")
    }

    /**
     * 저장된 토큰을 Flow로 조회합니다.
     * @return 토큰 Flow (없으면 null)
     */

    fun getToken(): Flow<String?> {
        Log.d("TokenDebug", "TokenManager: 토큰 조회 Flow 생성")
        return context.dataStore.data.map { preferences ->
            val token = preferences[TOKEN_KEY]
            Log.d("TokenDebug", "TokenManager: 토큰 조회 - ${token?.take(10)}...")
            token
        }
    }

    /**
     * 저장된 토큰을 삭제합니다. (로그아웃 시 호출)
     */
    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}