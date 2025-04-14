package com.rma.catalist.breeds.list

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.core.compose.CatalistAppTopBar
import com.rma.catalist.core.compose.LoadingIndicator
import com.rma.catalist.core.compose.NoDataContent
import com.rma.catalist.theme.PurpleGrey80

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
fun BreedListItem(
    data: Breed,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(width = 2.dp, color = PurpleGrey80, shape = RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp) // unutrašnji padding unutar bordera
    ){Text(
        modifier = Modifier.padding(bottom = 8.dp),
        text = ItemContent(data),
    )
        if(!data.breedTraits.isEmpty()){
            var i = 0;
            for(trait in data.breedTraits){
                if(i > 4) break;
                SuggestionChipForTraits(trait)
                i++
            }
        }

    }

}
@Composable
fun SuggestionChipForTraits(
    textForChip : String
) {
    SuggestionChip(
        onClick = { Log.d("Suggestion chip", textForChip) },
        label = { Text(textForChip) }
    )
}

fun ItemContent(
    data:Breed,

):String{
    val contentText = StringBuilder()
    contentText.append("Breed Name: "+ data.nameOfBreed + "\n")
    if(!data.alternativeNamesOfBreed.isEmpty()){
        contentText.append("Alternative Breed Names: ")
        data.alternativeNamesOfBreed.forEach {
            contentText.append(it)
            contentText.append(", ")
        }
        if (contentText.endsWith(", ")) {
            contentText.setLength(contentText.length - 2)
        }
        contentText.append("\n")
    }

    contentText.append("Breed Describe: "+"\n")
    var i = 1

    for(chr in data.describeBreed.toCharArray()){
        if(i > 250){
            contentText.append("...")
            break
        }
        contentText.append(chr)
        i++
    }



    return contentText.toString()
}


