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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuizScreen(
    viewModel: QuizViewModel
) {

    val uiState = viewModel.state.collectAsState()
    val state = uiState.value

    if (state.loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val question = state.questions[state.tempQuestionInd]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pitanje
            Text(
                text = if (question.questionType) "What is the name of this breed?" else "Take the odd-one out!",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                ) {
                    rowAnswers.forEach { answer ->
                        Button(
                            onClick = {
                                viewModel.setEvent(QuizContract.UiEvent.AnswerSelected(answer))
                            },
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(answer, textAlign = TextAlign.Center)
                        }
                    }
                    if (rowAnswers.size == 1) {
                        Spacer(modifier = Modifier.weight(1f)) // Ako je neparan broj odgovora
                    }
                }
            }
        }
    }
}