package com.github.junhee8649.androidstudy17.week4.Unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.github.junhee8649.androidstudy17.week4.Unscramble.data.MAX_NO_OF_WORDS
import com.github.junhee8649.androidstudy17.week4.Unscramble.data.SCORE_INCREASE
import com.github.junhee8649.androidstudy17.week4.Unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // private set은 getter / setter가 자동 적용되는 Kotlin에서 setter를 private로 바꿔줌
    // 즉, 어디에서든 읽을수는 있으나, 쓰는 것은 해당 변수가 선언된 클래스에서만 가능
    var userGuess by mutableStateOf("")
        private set

    // Set of words used in the game
    // 게임 진행 로직에서만 사용되고 UI에 노출될 필요가 없으므로 UIState에서 만들지 않음
    private var usedWords: MutableSet<String> = mutableSetOf()
    // 초기 단어가 ""라 필요 없고 pickRandom~함수가 호출될 때 실제 단어로 초기화 되니까 선언 시점을 미룬 것
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    /*
     * Re-initializes the game data to restart the game.
     */
    fun resetGame() {
        usedWords.clear()
        // MutableStateFlow의 값은 .value 속성을 통해 업데이트할 수 있음
        // _uiState.value = GameUiState(...)를 호출하면,
        // 이전에 저장되어 있던 상태 값들을 모두 버리고 새롭게 생성된 GameUiState 객체가 UI에 반영
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    /*
     * Update the user's guess
     */
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    /*
     * Checks if the user's guess is correct.
     * Increases the score accordingly.
     */
    fun checkUserGuess() {
        // ignoreCase는 대소문자  무시 옵션
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            // User's guess is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                // 사용자의 추측이 틀렸으면 isGuessedWordWrong을 true로 설정
                // MutableStateFlow<T>. update()는 지정된 값을 사용하여 MutableStateFlow.value를 업데이트
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Reset user guess
        updateUserGuess("")
    }

    /*
     * Skip to next word
     */
    fun skipWord() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserGuess("")
    }

    /*
     * Picks a new currentWord and currentScrambledWord and updates UiState according to
     * current game state.
     */
    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleCurrentWord(currentWord)
        }
    }
}