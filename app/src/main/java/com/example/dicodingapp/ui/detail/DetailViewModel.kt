package com.example.dicodingapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingapp.data.local.entity.FavoriteEntity
import com.example.dicodingapp.data.local.room.FavoriteRepository
import com.example.dicodingapp.data.remote.response.DetailEventResponse
import com.example.dicodingapp.data.remote.response.Event
import com.example.dicodingapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val repository: FavoriteRepository) : ViewModel() {

    private val _eventDetail = MutableLiveData<Event>()
    val eventDetail: LiveData<Event> get() = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> get() = _errorMessage


    fun fetchEventDetail(eventId: String) {
        _isLoading.value = true
        _errorMessage.value = null

        val client = ApiConfig.getApiService().getDetailEvent(eventId)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.event?.let {
                        _eventDetail.value = it
                    } ?: run {
                        _errorMessage.value = "Data event tidak ditemukan"
                    }
                } else {
                    _errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "onFailure: ${t.message}"
            }
        })
    }

    fun addToFavorite(event: FavoriteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.addToFavorite(event)
                _errorMessage.postValue(null)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to add to favorites: ${e.message}")
            }
        }
    }

    fun removeFromFavorite(event: FavoriteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.removeFromFavorite(event)
                _errorMessage.postValue(null)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to remove from favorites: ${e.message}")
            }
        }
    }

    suspend fun checkIfFavorite(eventId: Int): FavoriteEntity? {
        return try {
            val favorite = repository.checkIfFavorite(eventId)
            _errorMessage.postValue(null)
            favorite
        } catch (e: Exception) {
            _errorMessage.postValue("Failed to check favorite status: ${e.message}")
            null
        }
    }
}
