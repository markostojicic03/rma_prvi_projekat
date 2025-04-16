package com.rma.catalist.breeds.details

import com.rma.catalist.breeds.domain.Breed

interface BreedDetailsScreenContract {

    data class UiState(
        val breedId: String,
        val loading: Boolean = true,
        val data: Breed? = null,
    )

    sealed class UiEvent {
        //
    }

    sealed class SideEffect {
        // No side effects here
    }
}