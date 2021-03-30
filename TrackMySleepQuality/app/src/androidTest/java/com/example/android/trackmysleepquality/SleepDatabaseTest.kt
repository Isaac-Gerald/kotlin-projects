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

package com.example.android.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun insertAndGetNight() {
        val night = SleepNight()
        sleepDao.insert(night)
        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality, -1)
    }

    @Test
    @Throws(Exception::class)
    suspend fun getNightUsingKey() {

        val night = SleepNight()
        val night2 = SleepNight(endTimeMilli = night.startTimeMilli + 60, sleepQuality = 5)
        sleepDao.insert(night)
        sleepDao.insert(night2)
        val nightId = night.nightId
        val nightId2 = night2.nightId
        val tonight = sleepDao.get(nightId2)
        assertEquals(5, tonight?.sleepQuality)
        sleepDao.clear()
    }

    @Test
    suspend fun insertGetAndClearNight() {
        val night = SleepNight()
        val newNight = SleepNight(endTimeMilli = night.startTimeMilli + 60, sleepQuality = 3)
        sleepDao.insert(newNight)
        val tonight = sleepDao.get(1)
        assertEquals(3, tonight?.sleepQuality)
        sleepDao.clear()
        val sleepNight = sleepDao.get(1)
        assertEquals(null, sleepNight?.sleepQuality)
    }

    @Test
    suspend fun update(){
        val night = SleepNight()
        val firstNight = SleepNight(endTimeMilli = night.startTimeMilli + 60, sleepQuality = 3)
        val secondNight = SleepNight(endTimeMilli = night.startTimeMilli + 60, sleepQuality = 4)
        sleepDao.insert(firstNight)
        sleepDao.insert(secondNight)
        val sleepNight = sleepDao.get(2)
        assertEquals(4, sleepNight?.sleepQuality)
        val updatedSecondNight = SleepNight(nightId = 2,  sleepQuality = 5)
        sleepDao.update(updatedSecondNight)
        val newSleepNight = sleepDao.get(2)
        assertEquals(5, newSleepNight?.sleepQuality)
        sleepDao.clear()
        val sleepNightNull = sleepDao.get(2)
        assertEquals(null, sleepNightNull?.sleepQuality)
    }


}