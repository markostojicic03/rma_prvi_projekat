package com.rma.catalist.breeds.quiz

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.rma.catalist.breeds.list.BreedListScreenContract


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    navController: NavController,
) {

    val uiState = viewModel.state.collectAsState()
    val state = uiState.value


    val showCancelDialog = rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                QuizContract.SideEffect.NavigateCancelQuiz -> {
                    navController.navigate("start")
                }
            }
        }
    }

    // Fizički back taster
    BackHandler(enabled = !state.isQuizFinished) {
        showCancelDialog.value = true
    }

    // Alert dialog za potvrdu cancel-a
    if (showCancelDialog.value) {
        AlertDialog(
            onDismissRequest = { showCancelDialog.value = false },
            title = { Text("Cancel Quiz") },
            text = { Text("Are you sure you want to cancel the quiz? Your progress will be lost.") },
            confirmButton = {
                TextButton(onClick = {
                    showCancelDialog.value = false
                    viewModel.setEvent(QuizContract.UiEvent.CancelQuiz)
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showCancelDialog.value = false
                }) {
                    Text("No")
                }
            }
        )
    }

    if (state.loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    else if(state.isQuizFinished){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Quiz Finished!",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Correct answers: ${state.correctAnswers} / ${state.questions.size}",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Score: ${"%.2f".format(state.scoreInQuiz)}%",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.setEvent(QuizContract.UiEvent.CancelQuiz)
                    }
                ) {
                    Text("Return")
                }
            }
        }
    }
    else {
        val question = state.questions[state.tempQuestionInd]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // razmak od vrha


            Text(
                text = "Time left: ${state.remainingTime} sec",
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )


            // Tekst pitanja
            Text(
                text = if (question.questionType) "What is the name of this breed?" else "There is one wrong temperament for this breed. Take the odd-one out!",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Slika mačke
            question.breedImageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Cat",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }


            val answers = question.allAnswers.chunked(2)
            answers.forEach { rowAnswers ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowAnswers.forEach { answer ->
                        Button(
                            onClick = {
                                viewModel.setEvent(QuizContract.UiEvent.AnswerSelected(answer))
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp) // fiksna visina za sve dugmiće
                        ) {
                            Text(
                                text = answer,
                                textAlign = TextAlign.Center,
                                maxLines = 2 // ako su dugački odgovori
                            )
                        }
                    }

                    // Ako ima samo 1 dugme u redu, drugi je prazan Spacer da sve ostane centrirano
                    if (rowAnswers.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Button(
                onClick = {showCancelDialog.value = true},
//                modifier = Modifier
//                    .weight(1f)
//                    .height(56.dp),
            ) {

                Text(
                    text = "Cancel quiz",
                    textAlign = TextAlign.Center,
                    maxLines = 2,


                )


            }
        }
    }
}