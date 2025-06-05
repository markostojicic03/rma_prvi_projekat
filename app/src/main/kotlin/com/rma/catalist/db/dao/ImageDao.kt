package com.rma.catalist.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rma.catalist.db.entities.ImageDb
import kotlinx.coroutines.flow.Flow


@Dao
interface ImageDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ImageDb>)

    @Query("SELECT * FROM Image WHERE breedId = :breedId")
    suspend fun getAllForBreed(breedId: String): List<ImageDb>

    @Query("SELECT * FROM Image WHERE breedId = :breedId")
    fun observeAllForBreed(breedId: String): Flow<List<ImageDb>>
    
}