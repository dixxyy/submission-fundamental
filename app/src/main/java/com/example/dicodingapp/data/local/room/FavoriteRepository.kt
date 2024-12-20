package com.example.dicodingapp.data.local.room

import androidx.lifecycle.LiveData
import com.example.dicodingapp.data.local.entity.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository(
    private val favoriteDao: FavoriteDao
) {

    fun getFavoriteEvents(): LiveData<List<FavoriteEntity>> = favoriteDao.getFavoriteEvents()

    suspend fun addToFavorite(event: FavoriteEntity) = withContext(Dispatchers.IO) {
        favoriteDao.addToFavorite(event)
    }

    suspend fun removeFromFavorite(event: FavoriteEntity) = withContext(Dispatchers.IO) {
        favoriteDao.removeFromFavorite(event)
    }

    suspend fun checkIfFavorite(eventId: Int): FavoriteEntity? = withContext(Dispatchers.IO) {
        favoriteDao.checkIfFavorite(eventId)
    }
}