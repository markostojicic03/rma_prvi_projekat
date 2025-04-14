package com.rma.catalist.breeds.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.core.compose.CatalistAppTopBar
import com.rma.catalist.core.compose.LoadingIndicator
import com.rma.catalist.core.compose.NoDataContent

@Composable
fun BreedListScreen(
    viewModel: BreedListViewModel,
    clickOnBreed: ((Int) -> Unit)?
){

    val uiState = viewModel.state.collectAsState()

    BreedListScreen(
        state = uiState.value,
        eventPublisher = {viewModel.setEvent(it)},
        onBreedClick = clickOnBreed,


    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BreedListScreen(
    state: BreedListScreenContract.BreedListUiState,
    eventPublisher: (BreedListScreenContract.BreedListUiEvent) -> Unit,
    onBreedClick: ((Int) -> Unit)?
) {
    Scaffold(
        topBar = {
            CatalistAppTopBar(
                titleText = "Breed List",
                onMenuClick = {
                    // možeš da otvoriš meni, ili ostaviš prazno za sad
                },
                onSearchClick = {
                    // pokreni pretragu ili otvori search bar
                }
            )
        }
    ) { padding ->

        // ... ostatak UI kao i do sada
        if (state.loading) {
            LoadingIndicator()
        } else if (state.error != null) {
           /* NoDataContent(
                text = "Error = ${state.error.message}",
            )*/
        } else if (state.data.isEmpty()) {
            NoDataContent(
                text =  "Error, there is no data."
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize() // >>>>>> ovo rešava problem beskonačne visine
                    .verticalScroll(rememberScrollState())
                    .padding(padding)

            ) {
                state.data.forEach { breed ->
                    BreedListItem(
                        data = breed,
                        onClick = { onBreedClick?.invoke(breed.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BreedListColumn(
    modifier: Modifier,
    data: List<Breed>,
    onBreedClick: ((Int) -> Unit)?
) {
    Column(
        modifier = modifier
    ) {
        data.forEach { model ->
            BreedListItem(
                data = model,
                onClick = { onBreedClick?.invoke(model.id) },
            )
        }
    }
}

@Composable
fun BreedListItem(
    data: Breed,
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .clickable(onClick = onClick),
        text = data.nameOfBreed+"\n"+"marko",
    )

}


