package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val TAG = GameViewModel::class.java.simpleName
    private val _word = MutableLiveData<String>()
    private var _eventGameFinish = MutableLiveData<Boolean>()
    private val _score = MutableLiveData<Int>()
    private val _currentTime = MutableLiveData<Long>()
   private val timer: CountDownTimer
    val eventGameFinish: LiveData<Boolean>
        get() {
            return _eventGameFinish
        }

    val score: LiveData<Int>
        get() {
            return _score
        }

    val word: LiveData<String>
        get() {
            return _word
        }

    val currentTime: LiveData<Long>
        get() {
            return _currentTime
        }

    val currentTimeScreen: LiveData<String> = Transformations.map(currentTime){ time ->
        DateUtils.formatElapsedTime(time)

    }

    val currentWord  =  Transformations.map(word){ word ->
        val wordLength = word.length
        val randoms = (1..wordLength).random()
       "Current word has $wordLength letters, \nThe letter at position $randoms is ${word[randoms - 1].toUpperCase()}"
    }


    init {
        Log.i(TAG, ": GameViewModel started ")
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
              _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
              _currentTime.value = DONE
                onGameFinish()
            }

        }
        timer.start()

    }


    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>


    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    companion object{
        //Time when game is over
        private const val DONE = 0L
        //Count down time interval
        private const val ONE_SECOND = 1000L
        //Total time for the game
        private const val COUNTDOWN_TIME = 6000L
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    fun onGameFinish() {
        _eventGameFinish.value = true
    }


    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isNotEmpty()) {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
            Log.i(TAG, "nextWord: $word")
        } else {
            resetList()
        }


    }

    override fun onCleared() {
        Log.i(TAG, "onCleared: GameViewModelDestroyed")
        super.onCleared()
        timer.cancel()

    }
}