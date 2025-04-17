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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rma.catalist.core.compose.CatalistAppTopBar
import com.rma.catalist.core.compose.LoadingIndicator
import com.rma.catalist.core.compose.NoDataContent


@Composable
fun BreedDetailsScreen(
    viewModel: BreedDetailsViewModel,
    breedId : String,
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
    breedId : String,
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

        if (state.loading) {
            LoadingIndicator()
        }
        else if (state.error != null) {
            NoDataContent(
                text = "Error = ${state.error.message}",
            )
        }
        else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 32.dp)
            ) {


                Text(
                    text = "Stranica BreedDetails za id: $breedId"
                )

                Text(
                    text = "Ime rase: ${state.data?.name}"
                )


            }
        }

    }



}