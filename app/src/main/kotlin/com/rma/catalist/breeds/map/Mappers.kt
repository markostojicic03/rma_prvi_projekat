package com.rma.catalist.breeds.map

import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.db.entities.BreedDb

fun BreedApiModel.asBreedDb(): BreedDb {
    return BreedDb(
        id = id,
        name = name?: "",
        altNames = alt_names?: "",
        description = description?: "",
        temperament = temperament?: "",
        origin = origin?: "",
        lifeSpan = life_span?: "",
        weightImperial = weight?.imperial ?: "",
        weightMetric = weight?.metric ?: "",

        // traits
        energy_level = energy_level ?: 0,
        affection_level = affection_level ?: 0,
        child_friendly = child_friendly ?: 0,
        health_issues = health_issues ?: 0,
        vocalisation = vocalisation,
        rare = rare ?: -1,

        wikipedia_url = wikipedia_url ?:"",
        reference_image_id = reference_image_id ?:""
    )
}

