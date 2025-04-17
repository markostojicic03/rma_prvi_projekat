package com.rma.catalist.breeds.domain

import com.rma.catalist.breeds.api.model.Weight

data class Breed(
    // ovde pisem osnovne informacije
        val weight: Weight? = null,
        val id: String,
        val name : String,
        val temperament: List<String>,
        val origin: String = "",
        val description: String = "",
        val life_span : String = "",
        val alt_names : String? = null,

        // za UI widgete
        val affection_level: Int,
        val child_friendly: Int,
        val energy_level: Int,
        val health_issues: Int,
        val vocalisation: Int,

        val rare: Int,
        val wikipedia_url: String? = null,
        val reference_image_id: String? = null,
        val imageUrl: String? = null,

) {
    companion object {
        const val INVALID_ID = -1
    }

}