package com.rma.catalist.breeds.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.breeds.domain.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class BreedListViewModel @Inject constructor (
    private val breedRepository: BreedRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(BreedListScreenContract.BreedListUiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: BreedListScreenContract.BreedListUiState.() -> BreedListScreenContract.BreedListUiState) = _state.getAndUpdate(reducer)
    private val events = MutableSharedFlow<BreedListScreenContract.BreedListUiEvent>()
    fun setEvent(event: BreedListScreenContract.BreedListUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        loadBreeds()
    }


    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    is BreedListScreenContract.BreedListUiEvent.RefreshData -> refreshData()
                    is BreedListScreenContract.BreedListUiEvent.BreedClicked -> TODO()
                    is BreedListScreenContract.BreedListUiEvent.LoadBreeds -> TODO()
                    is BreedListScreenContract.BreedListUiEvent.SearchFilter -> TODO()
                }
            }
        }
    }

    private fun loadBreeds() = viewModelScope.launch {
        val data = breedRepository.getAllBreeds()

        setState { copy(data = data, loading = false) }
    }

    private fun refreshData() = viewModelScope.launch {
        delay(5.seconds)
    }
}