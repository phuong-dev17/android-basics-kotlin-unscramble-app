package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel :ViewModel() {
    init {
        Log.d("GameFragment", "GameViewModel created!")
    }
    private var _score = 0
    private var _currentWordCount = 0
    private var _currentScrambledWord = "test"
    private var usedWordsList = mutableListOf<String>()

    val score: Int
        get() {
            return _score
        }

    val currentScrambledWord: String
        get() {
            return _currentScrambledWord
        }

    init {
        Log.d("P123", "init game view model")
        getNextWord()
        Log.d("P123", _currentScrambledWord)
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    private fun getNextWord() {
        // lấy 1 từ random từ trong list allWordsList
        // neu usedWordsList khong chua randomWord thi xao tron tu do
        // kiem tra xem no da xao tron chua
        // neu roi thi cap nhat bien currentScrambled
        // neu chua thi xao tron lai roi so sanh lai roi cap nhat
        // them tu randomWord vao usedWordsList
        //neu usedWordsList chua randomWord thi chon tu khac
        var randomWord = allWordsList.random()
        if(randomWord !in usedWordsList) {
            var scrambledRandomWord: String
            do {
                val tempWord = randomWord.toCharArray()
                tempWord.shuffle()
                scrambledRandomWord = String(tempWord)
            } while (scrambledRandomWord == randomWord)
            usedWordsList.add(randomWord)
            _currentScrambledWord = scrambledRandomWord
        } else {
            getNextWord()
        }
    }

    fun nextWord(): Boolean {
        //neu chua choi het so luong tu thi se goi ham getNextWord de cap nhat scramble word
            // tra ra true
        // neu choi het so luong tu roi thi se tra ra false
        if (usedWordsList.size < MAX_NO_OF_WORDS ) {
            getNextWord()
            return true
        } else {
            return false
        }

    }

    fun isWordCorrect(submittedWord:String) :Boolean {
        val currentWord = usedWordsList.last()
        return currentWord == submittedWord
    }
}