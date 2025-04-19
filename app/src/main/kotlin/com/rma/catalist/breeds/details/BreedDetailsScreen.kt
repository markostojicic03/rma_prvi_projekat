package com.rma.catalist.breeds.details

import android.util.Log
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
import androidx.compose.foundation.text.handwriting.handwritingDetector
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
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
            val data = state.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    modifier = Modifier
                    .fillMaxWidth()
                   // .padding(padding)
                ){
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp),
                         //   .padding(start = 5.dp, top = 15.dp),
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
                            Log.d("BreedListInfo", "Image didnt load for id: "+ data?.id)
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
                    modifier = Modifier.padding(10.dp)
                ) {

                    Text(
                        text = buildAnnotatedString{
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, )) { append(data?.name + "\n") }
                        },
                        fontSize = 30.sp,
                        modifier =
                            Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                    )
                    if(data?.alt_names != null && data.alt_names.isNotEmpty() && (!data.alt_names.isBlank())){
                        Text(
                            text = buildAnnotatedString{
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                                    append("(")
                                    append(data.alt_names)
                                    append(")")
                                    append("\n")
                                }
                            },
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 20.dp),
                        )

                    }



                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(padding)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ){
                        Text(
                            text = data?.description.toString(),
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                }


            }
        }

    }



}