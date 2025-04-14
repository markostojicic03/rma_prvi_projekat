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
                urlForImage = "primerUrl",
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
                urlForWikipediaPage = "primerUrl"
            ),
            Breed(
                id = 2,
                nameOfBreed = "sibirska macka",
                alternativeNamesOfBreed = listOf("posivela", "basSiva"),
                describeBreed = "Sibirska mačka je srednje do velike rase, poznata po svojoj gustoj, vodootpornoj dlaci i prijateljskoj, razigranoj prirodi. Inteligentna i privržena, često se vezuje za jednog člana porodice. Dobro se slaže sa decom i drugim ljubimcima, ali zahteva redovno četkanje.",
                breedTraits = listOf("brza", "ruzna", "prgava", "dosadna", "debela", "dugodlaka", "ogromna"),
                urlForImage = "primerUrl",
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
                urlForWikipediaPage = "primerUrl",
            ),

        )
    }
}