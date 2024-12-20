package com.example.dicodingapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingapp.data.local.entity.FavoriteEntity
import com.example.dicodingapp.data.local.room.FavoriteRepository
import com.example.dicodingapp.ui.favorite.FavoriteViewModel
import kotlinx.coroutines.launch

class FavoriteViewModel(repository: FavoriteRepository) : ViewModel() {

    val favoriteEvent: LiveData<List<FavoriteEntity>> = repository.getFavoriteEvents()

}

class FavoriteViewModelFactory(private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
