package com.rma.catalist.breeds.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.breeds.api.model.BreedApiModel
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.breeds.map.asBreed
import com.rma.catalist.breeds.repository.BreedRepositoryNetworking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        observeEvents()
        fetchAllUsers()
        observeBreeds()
    }

    private fun fetchAllUsers() {
        viewModelScope.launch {

            ensureActive()

            setState { copy(loading = true) }
            try {
                breedRepository.fetchAllBreedsAndStore()
                breedRepository.fetchAndStoreImagesForAllBreeds() // <-- dodato
            } catch (error: Exception) {
                Log.e("BreedDebug", "Nesto je trulo u drzavi danskoj(fetchallUsers iz BreedListViewModel)", error)
            } finally {
                setState { copy(loading = false) }
            }
        }
    }
    private fun observeBreeds() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            breedRepository.observeAllBreedsRepository()
                .distinctUntilChanged()
                .collect {
                    setState {
                        copy(
                            loading = false,
                            data = it.map { it.asBreed() },
                        )
                    }
                }
        }
    }

/*
    private fun loadBreeds() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {

                val breedsFromApi = breedRepository.fetchAllBreeds()
                val breedList = breedsFromApi.map { it.asBreedUiModel(null) }
                allBreeds = breedList as MutableList<Breed>

                setState { copy(data = breedList, loading = false) }

                //Lazy
                breedList.forEachIndexed { index, breed ->

                        val imageUrl = try {
                            breedRepository.fetchCatImage(breed.reference_image_id)
                        } catch (e: Exception) {
                            Log.e("ImageLoad", "Error loading image for ${breed.name}", e)
                            null
                        }

                        imageUrl?.let {
                            val updatedBreed = breed.copy(imageUrl = it)

                            setState {
                                val newList = data.toMutableList()
                                newList[index] = updatedBreed
                                copy(data = newList)
                            }

                            // Ažuriraj i allBreeds za search
                            allBreeds = allBreeds.toMutableList().also {
                                it[index] = updatedBreed
                            }
                        }

                }
            } catch (error: Exception) {
                Log.d("test", "Failed to fetch.", error)
            } finally {
                //setState { copy(loading = false) }
            }
        }



    }
*/
    private fun observeEvents(){
        viewModelScope.launch {
            events.collect { event ->
                when(event){
                    is BreedListScreenContract.BreedListUiEvent.SearchFilter -> {
                        val queryTemp =  event.query
                     //   val breedsSearchFromApi = breedRepository.fetchSearchBreeds(queryTemp)
                        val breedSearchList = breedRepository.fetchSearchBreeds(queryTemp, state.value.data )

                        if (breedSearchList != null) {
                            setState {
                                copy(
                                    searchData = breedSearchList,
                                    search = queryTemp,
                                    loading = false
                                )
                            }


                        }


                        Log.d("BreedDebug","SEARCHED LISTA U MODEL VIEW: " +breedSearchList.toString())
                    }

                    is BreedListScreenContract.BreedListUiEvent.OnToggleSearchClick -> {
                        _state.update {
                           it.copy(isSearching = !it.isSearching, search = "")
                        }
                    }
                    is BreedListScreenContract.BreedListUiEvent.OnSearchClosed -> {
                        _state.update {
                            it.copy( isSearching = false, search = "", searchData = emptyList())
                        }
                    }

                }

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

        )


}