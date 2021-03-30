package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {
    private val TAG = ScoreViewModel::class.java.simpleName
    private val _score = MutableLiveData<Int>()
    private val _eventPlayAgain= MutableLiveData<Boolean>()
    val score : LiveData<Int>
        get() {
            return _score
        }
    val  eventPlayGame: LiveData<Boolean>
        get() {
            return _eventPlayAgain
        }

    init {
        Log.i(TAG, "Final score is: $finalScore")
        _score.value = finalScore
    }

    fun onPlayAgain(){
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete(){
        _eventPlayAgain.value = false
    }
}