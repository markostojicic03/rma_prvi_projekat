package com.rma.catalist.breeds.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rma.catalist.core.compose.CatalistAppTopBar


@Composable
fun BreedDetailsScreen(
    viewModel: BreedDetailsViewModel,
    breedId :Int,
    onClose: ()-> Unit
){
    val uiState = viewModel.state.collectAsState()
    BreedDetailsScreen(
        state = uiState.value,
        breedId = breedId,
        onClose = onClose
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailsScreen(
    state : BreedDetailsScreenContract.UiState,
    breedId :Int,
    onClose: ()-> Unit
){
    Scaffold(
        topBar = {
            CatalistAppTopBar(
                text = "Breed Details",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationOnClick = onClose,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {


            Text(
                text = "Title: Naslov za $breedId"
            )

        }
    }
}