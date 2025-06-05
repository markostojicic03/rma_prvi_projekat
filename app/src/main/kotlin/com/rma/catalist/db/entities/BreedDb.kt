package com.rma.catalist.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Breed")
data class BreedDb(

    @PrimaryKey val id: String,
    val name: String,
    val altNames: String,
    val description: String,
    val temperament: String,
    val origin: String,
    val lifeSpan: String,
    val weightImperial: String,
    val weightMetric: String,

    // za UI widgete
    val affection_level: Int,
    val child_friendly: Int,
    val energy_level: Int,
    val health_issues: Int,
    val vocalisation: Int,

    val rare: Int,
    val wikipedia_url: String,
    val reference_image_id: String,
//    val imageUrl: String,
)
