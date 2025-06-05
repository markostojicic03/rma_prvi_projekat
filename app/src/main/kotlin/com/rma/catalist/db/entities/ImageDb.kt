package com.rma.catalist.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey



@Entity(
    tableName = "Image",
    foreignKeys = [
        ForeignKey(
            entity = BreedDb::class,
            parentColumns = ["id"],
            childColumns = ["breedId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ImageDb(
    @PrimaryKey val id: String,
    val url: String,
    val breedId: String,
    val width: Int,
    val height: Int,

){
    init {
        require(url.isNotBlank()) { "Image url can not be blank" }
    }
}
