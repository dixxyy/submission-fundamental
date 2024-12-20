package com.example.dicodingapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dicodingapp.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(event: FavoriteEntity)

    @Delete
    suspend fun removeFromFavorite(event: FavoriteEntity)

    @Query("SELECT * FROM favorite_events")
    fun getFavoriteEvents(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorite_event WHERE id = :eventId")
    suspend fun checkIfFavorite(eventId: Int): FavoriteEntity?
}
