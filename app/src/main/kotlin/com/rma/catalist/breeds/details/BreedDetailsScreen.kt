package com.rma.catalist.breeds.details

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.handwriting.handwritingDetector
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil3.Uri
import coil3.compose.SubcomposeAsyncImage
import com.rma.catalist.breeds.list.SuggestionChipForTraits
import com.rma.catalist.core.compose.CatalistAppTopBar
import com.rma.catalist.core.compose.LoadingIndicator
import com.rma.catalist.core.compose.NoDataContent
import androidx.core.net.toUri
import com.rma.catalist.breeds.list.BreedListScreenContract


@Composable
fun BreedDetailsScreen(
    viewModel: BreedDetailsViewModel,
    breedId : String,
    onClose: ()-> Unit
){
    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(viewModel, onClose) {
        viewModel.sideEffect.collect {
            when (it) {
                is BreedDetailsScreenContract.SideEffect.NavigateToBreedGallery ->{

                }
                is BreedDetailsScreenContract.SideEffect.NavigateToWikipediaUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, it.url.toUri())
                    context.startActivity(intent)
                }
            }
        }
    }

    BreedDetailsScreen(
        state = uiState.value,
        breedId = breedId,
        onClose = onClose,
        eventPublisher = {viewModel.setEvent(it)}
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailsScreen(
    state : BreedDetailsScreenContract.UiState,
    breedId : String,
    onClose: ()-> Unit,
    eventPublisher: (BreedDetailsScreenContract.UiEvent) -> Unit,
) {
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
        } else if (state.error != null) {
            NoDataContent(
                text = "Error = ${state.error.message}",
            )
        } else {
            val data = state.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                    // .padding(padding)
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .height(600.dp)
                            .padding(top = 5.dp),
                        model = data?.imageUrl,
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
                            Log.d("BreedListInfo", "Image didnt load for id: " + data?.id)
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Image(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = null
                                )
                            }
                        }
                    )

                }
                Column(
                    // modifier = Modifier.padding(5.dp)
                ) {


                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(
                                    data?.name + "\n"
                                )
                            }
                        },
                        fontSize = 30.sp,
                        modifier =
                            Modifier.padding(bottom = 4.dp, top = 5.dp)
                    )
                    if (data?.alt_names != null && data.alt_names.isNotEmpty() && (!data.alt_names.isBlank())) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("(")
                                    append(data.alt_names)
                                    append(")")
                                    append("\n")
                                }
                            },
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 12.dp),
                        )

                    }

                    if(data?.wikipedia_url != null) FilledButtonWiki(data.wikipedia_url, eventPublisher)
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Description:") }
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 4.dp, top = 5.dp)
                    )


                    Text(
                        text = data?.description.toString(),
                        textAlign = TextAlign.Justify,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Origin:") }
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = data?.origin.toString(),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )


                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(
                                    "Temperament:"
                                )
                            }
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )


                    if(!(data?.temperament?.isEmpty()!!)) {

                        data.temperament
                            .chunked(3)
                            .forEach { rowTraits ->
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    rowTraits.forEach { trait ->
                                        SuggestionChipForTraitsDetailsScreen(trait)
                                    }
                                }
                            }
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Life span: ") }
                            append(data.life_span + " years")
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    if(data.weight?.metric != null){
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Weight: ") }
                                append(data.weight.metric.toString() + "kg")
                            },
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("Traits:") }
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    TraitDotsIndicator(label = "Affection Level", value = data.affection_level)
                    TraitDotsIndicator(label = "Child friendly", value = data.child_friendly)
                    TraitDotsIndicator(label = "Energy level", value = data.energy_level)
                    TraitDotsIndicator(label = "Health issues", value = data.health_issues)
                    TraitDotsIndicator(label = "Vocalisation", value = data.vocalisation)

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                if(data.rare == 1) append("Rare breed: "+"✅" )
                                else append("Rare breed: "+ "❌")
                            }
                        },
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                }
            }

        }


    }
}
    @Composable
    fun SuggestionChipForTraitsDetailsScreen(
        textForChip: String
    ) {
        SuggestionChip(
            modifier = Modifier.padding(5.dp),
            onClick = { Log.d("Suggestion chip", textForChip) },
            label = { Text(textForChip) }
        )
    }

@Composable
fun TraitDotsIndicator(
    label: String,
    value: Int, // od 1 do 5
    max: Int = 5,
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row {
            repeat(value) {
                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = null,
                    tint = androidx.compose.ui.graphics.Color(0xFFEA681C),
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 4.dp)
                )
            }
            val gray = Color.LTGRAY
            repeat(max - value) {
                Icon(
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = null,
                    tint =  androidx.compose.ui.graphics.Color(gray.toInt()),
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 4.dp)
                )
            }
        }
    }
}

@Composable
fun FilledButtonWiki(
    wikiUrl: String,
    eventPublisher: (BreedDetailsScreenContract.UiEvent) -> Unit,
    ) {
    androidx.compose.material3.Button(
        onClick = {eventPublisher(BreedDetailsScreenContract.UiEvent.OnWikipediaClicked)}
    ) {
        Text("Wikipedia")
    }
}

@Composable
fun FilledButtonGallery(onClick: () -> Unit) {
    androidx.compose.material3.Button(onClick = onClick) {
        Text("Open Gallery")
    }
}


