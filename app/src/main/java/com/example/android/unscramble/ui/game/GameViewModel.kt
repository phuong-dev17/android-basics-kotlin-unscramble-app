package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel :ViewModel() {
    init {
        Log.d("GameFragment", "GameViewModel created!")
    }
    private var _score = MutableLiveData<Int>(0)
    private var _currentWordCount = MutableLiveData<Int>(1)
    private var _currentScrambledWord = MutableLiveData<String>()
    private var usedWordsList = mutableListOf<String>()

    val score: LiveData<Int>
        get() {
            return _score
        }

    val currentWordCount : LiveData<Int>
        get() {
            return _currentWordCount
        }

    val currentScrambledWord: LiveData<String>
        get() {
            return _currentScrambledWord
        }

    init {
        getNextWord()
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
            _currentScrambledWord.value = scrambledRandomWord
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
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            return true
        } else {
            return false
        }
    }

    fun isWordCorrect(submittedWord:String) :Boolean {
        val currentWord = usedWordsList.last()
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
        return currentWord == submittedWord
    }

    fun resetData () {
        _score.value = 0
        _currentWordCount.value = 0
        usedWordsList = mutableListOf<String>()
        getNextWord()
    }
}