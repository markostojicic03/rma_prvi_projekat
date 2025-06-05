package com.rma.catalist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rma.catalist.db.dao.BreedDao
import com.rma.catalist.db.dao.ImageDao
import com.rma.catalist.db.entities.BreedDb
import com.rma.catalist.db.entities.ImageDb


@Database(
    entities = [
        BreedDb::class,
        ImageDb::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao

    abstract fun imageDao() : ImageDao

}
