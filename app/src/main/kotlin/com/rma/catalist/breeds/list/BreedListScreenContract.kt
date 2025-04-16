package com.rma.catalist.breeds.list

import com.rma.catalist.breeds.domain.Breed

interface BreedListScreenContract {

    data class BreedListUiState(
        val loading: Boolean = true,
        val data: List<Breed> = emptyList(),
        val error: Throwable? = null,
        // dodati za search i selektovanu macku?
        val search: String = "",
        val selectedBreed : Int? = null
    )

    sealed class BreedListUiEvent {
        data class SearchFilter(val query: String) : BreedListUiEvent()// kada korisnik kuca u pretrazi ime rase macke
        object LoadBreeds : BreedListUiEvent() // ucitavamo rase macaka
    }

}