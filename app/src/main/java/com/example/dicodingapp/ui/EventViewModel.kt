package com.example.dicodingapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingapp.data.remote.response.EventResponse
import com.example.dicodingapp.data.remote.response.ListEventsItem
import com.example.dicodingapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> get() = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> get() = _errorMessage

    fun fetchEvents(active:Int) {
        _isLoading.value = true
        _errorMessage.value = null

        val client = ApiConfig.getApiService().getEvents(active)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _events.value = response.body()?.listEvents?.filterNotNull()
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "onFailure: ${t.message}"
            }

        })
    }
}
