package com.rma.catalist.breeds.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatImageApiModel(
    @SerialName("url")val imageUrl : String?
)