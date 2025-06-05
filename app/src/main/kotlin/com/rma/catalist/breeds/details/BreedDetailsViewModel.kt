package com.rma.catalist.breeds.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.breeds.repository.BreedRepositoryNetworking
import com.rma.catalist.navigation.breedIdOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BreedDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val breedRepository: BreedRepositoryNetworking,
): ViewModel() {


    private val breedId = savedStateHandle.breedIdOrThrow


    private val _state = MutableStateFlow(BreedDetailsScreenContract.UiState(breedId = breedId))
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedDetailsScreenContract.UiState.() -> BreedDetailsScreenContract.UiState) = _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<BreedDetailsScreenContract.UiEvent>()
    fun setEvent(event: BreedDetailsScreenContract.UiEvent) = viewModelScope.launch { events.emit(event) }



    init {
        observeEvents()

    }


    private fun observeEvents(){
        viewModelScope.launch {
            events.collect { event ->
                when(event){
                    is BreedDetailsScreenContract.UiEvent.OpenedScreen -> loadBreedDetails()
                }

            }
        }
    }



    private fun loadBreedDetails() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val breedApiModel = breedRepository.fetchBreedById(breedId)
                delay(500L)
                val imageUrl = try{
                    breedRepository.fetchCatImage(breedApiModel?.reference_image_id)

                }catch (e: Exception){
                    Log.e("ImageLoad", "Error loading image for ${breedApiModel?.name}")
                    null
                }

                val breed = breedApiModel?.asBreedUiModel(imageUrl)

                setState { copy(data = breed) }
            } catch (error: Exception) {
                Log.d("test", "Failed to fetch.", error)
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun BreedApiModel.asBreedUiModel(imageForModel: String?) = Breed(
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
        imageUrl = imageForModel,

        )




//    private fun loadBreeds(breedId: Int) = viewModelScope.launch {
//        try {
//            setState { copy(loading = true) }
//            val breedData = breedRepository.getBreedById(breedId = breedId)
//            setState {
//                copy(
//                    data = breedData,
//                    loading = false
//                )
//            }
//        } catch (error: Exception) {
//            //
//        }
//    }



}