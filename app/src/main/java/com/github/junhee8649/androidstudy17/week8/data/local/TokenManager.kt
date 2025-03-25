package com.github.junhee8649.androidstudy17.week8.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.github.junhee8649.androidstudy17.week8.data.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Context의 확장 프로퍼티로 DataStore 인스턴스 생성
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

/**
 * 토큰 및 사용자 정보의 로컬 저장소 접근을 담당하는 데이터 소스
 */
class TokenManager(private val context: Context) {

    companion object {
        // 키 정의
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val STUDENT_ID_KEY = stringPreferencesKey("student_id")
    }

    /**
     * 토큰을 DataStore에 저장합니다.
     */
    suspend fun saveToken(token: String) {
        Log.d("TokenDebug", "TokenDataSource: 토큰 저장 시작")
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
        Log.d("TokenDebug", "TokenDataSource: 토큰 저장 완료")
    }

    /**
     * 학번을 DataStore에 저장합니다.
     */
    suspend fun saveStudentId(studentId: String) {
        Log.d("TokenDebug", "TokenDataSource: 학번 저장 시작")
        context.dataStore.edit { preferences ->
            preferences[STUDENT_ID_KEY] = studentId
        }
        Log.d("TokenDebug", "TokenDataSource: 학번 저장 완료")
    }

    /**
     * 저장된 토큰을 Flow로 조회합니다.
     */
    fun getToken(): Flow<String?> {
        Log.d("TokenDebug", "TokenDataSource: 토큰 조회 Flow 생성")
        return context.dataStore.data.map { preferences ->
            val token = preferences[TOKEN_KEY]
            Log.d("TokenDebug", "TokenDataSource: 토큰 조회 - $token")
            token
        }
    }

    /**
     * 저장된 학번을 Flow로 조회합니다.
     */
    fun getStudentId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[STUDENT_ID_KEY]
        }
    }

    /**
     * 저장된 토큰과 학번을 삭제합니다. (로그아웃 시 호출)
     */
    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(STUDENT_ID_KEY)
        }
    }
}