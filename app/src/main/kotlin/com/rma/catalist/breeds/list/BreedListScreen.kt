package com.rma.catalist.breeds.list

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.core.compose.CatalistAppTopBar
import com.rma.catalist.core.compose.LoadingIndicator
import com.rma.catalist.core.compose.NoDataContent
import com.rma.catalist.theme.PurpleGrey80
import com.rma.catalist.R
import com.rma.catalist.core.compose.DrawerContent
import com.rma.catalist.theme.OrangeLogoColor
import kotlinx.coroutines.launch

@Composable
fun BreedListScreen(
    navController: NavController,
    viewModel: BreedListViewModel,
    clickOnBreed: ((String) -> Unit)?
){

    val uiState = viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is BreedListScreenContract.BreedsSideEffect.NavigateToQuiz -> {
                    navController.navigate("quiz/start")
                }

                is BreedListScreenContract.BreedsSideEffect.NavigateToEditProfile -> TODO()
                is BreedListScreenContract.BreedsSideEffect.NavigateToLeaderboard -> TODO()
            }
        }
    }


    BreedListScreen(
        viewModel = viewModel,
        state = uiState.value,
        eventPublisher = {viewModel.setEvent(it)},
        onBreedClick = clickOnBreed,


    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BreedListScreen(
    viewModel: BreedListViewModel,
    state: BreedListScreenContract.BreedListUiState,
    eventPublisher: (BreedListScreenContract.BreedListUiEvent) -> Unit,
    onBreedClick: ((String) -> Unit)?
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer (
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface,
            ) {
                DrawerContent(
                    onItemClick = { action ->
                        when (action) {
                            "start_quiz" -> viewModel.setEvent(BreedListScreenContract.BreedListUiEvent.OnStartQuizClicked)
                            "edit_profile" -> viewModel.setEvent(BreedListScreenContract.BreedListUiEvent.OnEditProfileClicked)
                            "leaderboard" -> viewModel.setEvent(BreedListScreenContract.BreedListUiEvent.OnLeaderboardClicked)
                        }
                        scope.launch { drawerState.close() }
                    }
                )

            }
        },
        drawerState = drawerState
    ){
        Scaffold(
            topBar = {
                CatalistAppTopBar(
                    text = "Breed List",
                    navigationIcon = ImageVector.vectorResource(id = R.drawable.svgpersan),
                    navigationOnClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    actionIcon = Icons.Default.Search,
                    actionOnClick = {
                        if (state.isSearching) {
                            eventPublisher(BreedListScreenContract.BreedListUiEvent.OnSearchClosed)
                        } else {
                            eventPublisher(BreedListScreenContract.BreedListUiEvent.OnToggleSearchClick)
                        }
                    },
                    isSearching = state.isSearching,
                    searchQuery = state.search,
                    onSearchQueryChange = {
                        eventPublisher(BreedListScreenContract.BreedListUiEvent.SearchFilter(it))
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
                    text =  "There is no data yet (loading data...)"
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize() // >>>>>> ovo rešava problem beskonačne visine
                        .verticalScroll(rememberScrollState())
                        .padding(padding)

                ) {
                    val listToDisplay = if (state.isSearching && state.search.isNotBlank()) {
                        state.searchData
                    } else {
                        state.data
                    }

                    listToDisplay.forEach { breed ->
                        BreedListItem(
                            data = breed,
                            onClick = { onBreedClick?.invoke(breed.id) }
                        )
                    }

                }
            }
        }
    }

}



@Composable
fun BreedListItem(
    data: Breed,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            //.height(350.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(CardDefaults.shape)
            .clickable { onClick(data.id) },
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, start = 3.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(

                text = buildAnnotatedString{
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append(data.name) }

                },
                fontWeight = FontWeight.Bold,
            )

            if(data.alt_names != null && data.alt_names.isNotEmpty() && (!data.alt_names.isBlank())){
                Text(
                    fontWeight = FontWeight.Bold,
                    text = buildAnnotatedString{
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                            append("(")
                            append(data.alt_names)
                            append(")")
                            append("\n")

                        }
                    }
                )

            }


        }


        Row {

            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(170.dp)
                    .padding(start = 5.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 3.dp,
                        color = if(isSystemInDarkTheme()){ androidx.compose.ui.graphics.Color(0xFFFFB74D)} else{ androidx.compose.ui.graphics.Color(0xFFFFCC80)},
                        // ne prima ovde boje kao varijablu(one koje sam definisao u Colors, pa sam definisao opet)
                        shape = RoundedCornerShape(12.dp)
                    ),
                model = data.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
//                                contentScale = ContentScale.Fit,
//                                contentScale = ContentScale.FillWidth,
//                                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(36.dp),
                        )
                    }
                },
                error = {
                    Log.d("BreedListInfo", "Image didnt load for id: "+ data.id)
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            imageVector = Icons.Default.Downloading,
                            contentDescription = null
                        )
                    }
                }
            )
            Column(
                modifier = Modifier.padding(horizontal = 10.dp),
            ){
                ItemContent(data)
            }


        }
        Row {
            if(!data.temperament.isEmpty()){
                var i = 0;
                for(trait in data.temperament){
                    if(i > 2) break;
                    SuggestionChipForTraits(trait)
                    i++
                }
            }
            else{
                Text(text = "PRAZNA TEMPERAMENT LISTA!!!")
            }
        }
    }

}





@Composable
fun SuggestionChipForTraits(
    textForChip : String
) {
    SuggestionChip(
        modifier = Modifier.padding(5.dp),
        onClick = { Log.d("Suggestion chip", textForChip) },
        label = { Text(textForChip) }
    )
}

@Composable
fun ItemContent(
    data:Breed,

){

    Text(
        text = buildAnnotatedString{
            var i = 1

            for(chr in data.description.toCharArray()){
                if(i > 100){
                    append("...")
                    break
                }
                append(chr)
                i++
            }
        }
    )

}




