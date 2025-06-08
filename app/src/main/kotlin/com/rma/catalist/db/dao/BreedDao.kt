package com.rma.catalist.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rma.catalist.db.entities.BreedDb
import kotlinx.coroutines.flow.Flow


@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<BreedDb>)

    @Query("SELECT * FROM Breed")
    suspend fun getAll(): List<BreedDb>

    @Query("SELECT * FROM Breed")
    fun observeAllBreedsDao(): Flow<List<BreedDb>>

    @Query("SELECT * FROM Breed WHERE id = :breedId")
    fun observeBreedById(breedId: String): Flow<BreedDb?>
}