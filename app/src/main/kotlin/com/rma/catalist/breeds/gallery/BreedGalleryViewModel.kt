package com.rma.catalist.breeds.gallery

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.breeds.details.BreedDetailsScreenContract
import com.rma.catalist.breeds.list.BreedListScreenContract
import com.rma.catalist.breeds.map.asBreed
import com.rma.catalist.breeds.map.asImage
import com.rma.catalist.breeds.repository.BreedRepositoryNetworking
import com.rma.catalist.navigation.breedIdOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedGalleryViewModel@Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val breedRepository: BreedRepositoryNetworking,
): ViewModel() {

    private val breedId = savedStateHandle.breedIdOrThrow

    private val _state = MutableStateFlow(BreedGalleryContract.UiState())
    val state = _state.asStateFlow()


    private fun setState(reducer: BreedGalleryContract.UiState.() -> BreedGalleryContract.UiState) = _state.getAndUpdate(reducer)
    private val events = MutableSharedFlow<BreedGalleryContract.UiState>()
    fun setEvent(event: BreedGalleryContract.UiState) = viewModelScope.launch { events.emit(event) }

    init{
        observeAlbumForBreed()
    }


    private fun observeAlbumForBreed() {
        state.value.breedId = breedId
        viewModelScope.launch {
            setState { copy(loading = true) }
            breedRepository.observeAllImagesForBreedAlbum(breedId)
                .distinctUntilChanged()
                .collect {
                    setState {
                        copy(
                            loading = false,
                            images = it.map { it.asImage() },
                        )
                    }
                }
        }
    }
}