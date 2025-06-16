package com.rma.catalist.breeds.quiz

import com.rma.catalist.breeds.domain.Breed

interface QuizContract {

    data class Question(
        val allAnswers: List<String>,
        val solution: String,
        val breedId: String,
        var breedImageUrl: String? = "",
        var questionType: Boolean// stavio sam da je false za izbaci uljeza, a true za rasu macke
    )

    data class UiState(

        val loading: Boolean = true,
        val error: Throwable? = null,

        val questions: List<Question> = emptyList(),
        val tempQuestionInd: Int = 0,
        val correctAnswers: Int = 0,
        val remainingTime: Int = 300,
        val isQuizFinished: Boolean = false,

        val scoreInQuiz: Double = 0.0
    )

    sealed class UiEvent {
        data class AnswerSelected(val selectedAnswer: String) : UiEvent()
        data class NextQuestion(val selected: String) : UiEvent()
        data object TimeUp: UiEvent()
        data class SubmitResult(val score: Double) : UiEvent()
        object CancelQuiz : UiEvent()

    }

    sealed class SideEffect {

    }

}