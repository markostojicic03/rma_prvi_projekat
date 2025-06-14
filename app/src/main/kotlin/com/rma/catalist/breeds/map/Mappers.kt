package com.rma.catalist.breeds.map

import androidx.compose.ui.Modifier
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.api.model.CatImageApiModel
import com.rma.catalist.breeds.api.model.Weight
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.db.entities.BreedDb
import com.rma.catalist.db.entities.ImageDb

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

        energy_level = energy_level ?: 0,
        affection_level = affection_level ?: 0,
        child_friendly = child_friendly ?: 0,
        health_issues = health_issues ?: 0,
        vocalisation = vocalisation,
        rare = rare ?: -1,

        wikipedia_url = wikipedia_url ?:"",
        reference_image_id = reference_image_id ?:"",
        imageUrl =  imageUrl.toString(),

    )
}
fun BreedDb.asBreed(): Breed {
    return Breed(
        id = id,
        name = name,
        alt_names = altNames,
        description = description,
        temperament = temperament.split(", "),
        origin = origin,
        life_span = lifeSpan,
        vocalisation = vocalisation,
        affection_level = affection_level,
        child_friendly = child_friendly,
        energy_level = energy_level,
        health_issues = health_issues,
        rare = rare,
        wikipedia_url = wikipedia_url,
        reference_image_id = reference_image_id,
        weight = Weight(weightImperial, weightMetric),
        imageUrl = imageUrl,
    )
}


fun CatImageApiModel.asImageDb(): ImageDb {
    return ImageDb(
        id = id,
        url = imageUrl.toString(),
        breedId = breeds.firstOrNull()?.id.toString(),
        width = width,
        height = height
    )
}

fun ImageDb.asImage(): CatImageApiModel{
    return CatImageApiModel(
        id = id,
        imageUrl = url,
        breeds = emptyList(),
        width = width,
        height = height,
    )


}



