/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleepquality

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.launch

class SleepQualityViewModel(private val sleepNightKey: Long = 0L,
                            private val databaseDao: SleepDatabaseDao) : ViewModel() {
    private val TAG = SleepQualityViewModel::class.java.simpleName
    val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker: LiveData<Boolean?>
        get() {
            return _navigateToSleepTracker
        }

     fun onSetSleepQuality(quality: Int) {
        Log.d(TAG, "onSetSleepQuality: quality selected is $quality")

        viewModelScope.launch{
            val tonight = databaseDao.get(sleepNightKey)?: return@launch
            Log.d(TAG, "onSetSleepQuality: tonight is $tonight")
            tonight?.sleepQuality = quality
            databaseDao.update(tonight)
            val newNight = databaseDao.get(sleepNightKey)
            Log.d(TAG, "onSetSleepQuality: updated sleep is $newNight")
            _navigateToSleepTracker.value = true
        }



    }

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }
}