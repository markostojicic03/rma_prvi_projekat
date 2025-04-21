package com.rma.catalist.breeds.list

import com.rma.catalist.breeds.domain.Breed

interface BreedListScreenContract {

    data class BreedListUiState(
        val loading: Boolean = true,
        val data: List<Breed> = emptyList(),
        val error: Throwable? = null,



        val searchData: List<Breed> = emptyList(),
        val isSearching: Boolean = false,
        val search: String = "",
    )

    sealed class BreedListUiEvent {
        data class SearchFilter(val query: String) : BreedListUiEvent()// kada korisnik kuca u pretrazi ime rase macke
        object OnToggleSearchClick: BreedListUiEvent()
        data object OnSearchClosed : BreedListUiEvent()
    }

}