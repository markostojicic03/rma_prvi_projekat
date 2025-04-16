package com.rma.catalist.breeds.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.breeds.domain.BreedRepository
import com.rma.catalist.breeds.repository.BreedRepositoryNetworking
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
    private val breedRepository: BreedRepositoryNetworking,
) : ViewModel() {

    private val _state = MutableStateFlow(BreedListScreenContract.BreedListUiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: BreedListScreenContract.BreedListUiState.() -> BreedListScreenContract.BreedListUiState) = _state.getAndUpdate(reducer)
    private val events = MutableSharedFlow<BreedListScreenContract.BreedListUiEvent>()
    fun setEvent(event: BreedListScreenContract.BreedListUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
//        observeEvents()
//        loadBreeds()
        fetchAllBreeds()
    }

    private fun fetchAllBreeds() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val breeds = breedRepository.fetchAllBreeds().map { it.asBreedUiModel() }
                setState { copy(data = breeds) }
            } catch (error: Exception) {
                // TODO Handle error
                Log.d("test", "Failed to fetch.", error)
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun BreedApiModel.asBreedUiModel() = Breed(
        weight = this.weight,
        id = this.id,
        name = this.name,
        temperament = this.temperament.split(",").map { it.trim() },
        origin = this.origin,
        description = this.description,
        life_span = this.life_span,
        alt_names = this.alt_names,
        affection_level = this.affection_level,
        child_friendly = this.child_friendly,
        energy_level = this.energy_level,
        health_issues = this.health_issues,
        vocalisation = this.vocalisation,
        rare = this.rare,
        wikipedia_url = this.wikipedia_url,
        reference_image_id = this.reference_image_id,
    )
/*
    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
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
    }*/
}