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
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilters
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
enum class MarsApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<MarsApiStatus>()
    private val _properties = MutableLiveData<List<MarsProperty>>()
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
    get() = _navigateToSelectedProperty

    // The external immutable LiveData for the response String
    val status: LiveData<MarsApiStatus>
        get() = _status

    val properties: LiveData<List<MarsProperty>>
    get() = _properties

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilters.SHOW_ALL)
    }

/*----------------------using coroutines to call data---------------------------------*/
    private fun getMarsRealEstateProperties(filter: MarsApiFilters) {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                val listResults = MarsApi.retrofitService.getProperties(filter.value)
                _status.value = MarsApiStatus.DONE
                _properties.value = listResults

            }catch (e: Exception){
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: MarsApiFilters){
        getMarsRealEstateProperties(filter)
    }


    fun displayPropertiesDetails(marsProperty: MarsProperty){
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete(){
        _navigateToSelectedProperty.value = null
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
/*-------------------using retrofit callback to enqueue data-------------------------------*/
//    private fun getMarsRealEstateProperties() {
//        _response.value =
//                MarsApi.retrofitService.getProperties().enqueue(
//                        object : Callback<List<MarsProperty>> {
//                            override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
//                                _response.value =
//                                        "Success: ${response.body()?.size} Mars properties retrieved"
//                            }
//
//                            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
//                                _response.value = "Failure " + t.message
//                            }
//
//                        }
//                ).toString()
//
//
//    }
}
