package com.rma.catalist.breeds.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.breeds.domain.BreedRepository
import com.rma.catalist.navigation.breedIdOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val breedRepository: BreedRepository,
): ViewModel() {


    private val breedId = savedStateHandle.breedIdOrThrow


    private val _state = MutableStateFlow(BreedDetailsScreenContract.UiState(breedId = breedId))
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedDetailsScreenContract.UiState.() -> BreedDetailsScreenContract.UiState) = _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<BreedDetailsScreenContract.UiEvent>()
    fun setEvent(event: BreedDetailsScreenContract.UiEvent) = viewModelScope.launch { events.emit(event) }



    init {
        observeEvents()
        loadBreeds(breedId)
    }


    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                //

            }
        }
    }

    private fun loadBreeds(breedId: Int) = viewModelScope.launch {
        try {
            setState { copy(loading = true) }
            val breedData = breedRepository.getBreedById(breedId = breedId)
            setState {
                copy(
                    data = breedData,
                    loading = false
                )
            }
        } catch (error: Exception) {
            //
        }
    }



}