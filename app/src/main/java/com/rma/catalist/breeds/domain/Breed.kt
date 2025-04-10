package com.rma.catalist.breeds.domain

data class Breed(
    // ovde pisem osnovne informacije

        val id: Int,
        val nameOfBreed: String,
        val alternativeNamesOfBreed: List<String>,
        val describeBreed: String, // max 250 karaktera, skratiti ako ima vise od toga
        val breedTraits: List<String>, // max 5 osobina

    // ovde su informacije koje se prikazuju na Breed Details Screen-u

        val urlForImage: String,
    // prikazati ceo describeBreed na Breed Details ekranu
        val originCountries: List<String>,
        // sve osobine temperamenta prikazati na ovom ekranu
        val expectedLifeDuration: Int,
        val breedAvgWeight: Double,
        val breedAvgHeight: Double,
        // za UI widgete
        val affectionLevel: Int,
        val childFriendly: Int,
        val energyLevel: Int,
        val healthIssues: Int,
        val vocalisation: Int,

        val isRareType: Boolean,
        val urlForWikipediaPage: String

) {
    companion object {
        const val INVALID_ID = -1
    }

}