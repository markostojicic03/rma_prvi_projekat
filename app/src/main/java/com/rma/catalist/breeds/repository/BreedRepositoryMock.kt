package com.rma.catalist.breeds.repository

import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.breeds.domain.BreedRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class BreedRepositoryMock @Inject constructor(

): BreedRepository {
    override suspend fun getAllBreeds(): List<Breed> {
        delay(5.seconds)
        return listOf(
            Breed(
                id = 1,
                nameOfBreed = "zutaMacka",
                alternativeNamesOfBreed = listOf("pozutela", "basPozutela"),
                describeBreed = "lepa i umiljata",
                breedTraits = listOf("nervozna", "luda"),
                urlForImage = TODO(),
                originCountries = listOf("srbija", "republika srpska"),
                expectedLifeDuration = 10,
                breedAvgWeight = 7.5,
                breedAvgHeight = 20.2,
                affectionLevel = 5,
                childFriendly = 5,
                energyLevel = 5,
                healthIssues = 5,
                vocalisation = 5,
                isRareType = true,
                urlForWikipediaPage = TODO(),
            ),
            Breed(
                id = 2,
                nameOfBreed = "sivaMacka",
                alternativeNamesOfBreed = listOf("posivela", "basSiva"),
                describeBreed = "lepa i umiljata",
                breedTraits = listOf("brza", "ruzna"),
                urlForImage = TODO(),
                originCountries = listOf("spanija", "rusija"),
                expectedLifeDuration = 12,
                breedAvgWeight = 17.5,
                breedAvgHeight = 22.2,
                affectionLevel = 4,
                childFriendly = 4,
                energyLevel = 4,
                healthIssues = 4,
                vocalisation = 4,
                isRareType = true,
                urlForWikipediaPage = TODO(),
            )

        )
    }
}