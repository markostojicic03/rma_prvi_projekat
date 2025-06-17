package com.rma.catalist.breeds.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.breeds.domain.Breed
import com.rma.catalist.breeds.list.BreedListScreenContract
import com.rma.catalist.breeds.map.asBreed
import com.rma.catalist.breeds.map.asImage
import com.rma.catalist.breeds.repository.BreedRepositoryNetworking
import com.rma.catalist.db.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val breedRepository: BreedRepositoryNetworking,
) : ViewModel(){

    private val _state = MutableStateFlow(QuizContract.UiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: QuizContract.UiState.() -> QuizContract.UiState) = _state.getAndUpdate(reducer)
    private val events = MutableSharedFlow<QuizContract.UiEvent>()
    fun setEvent(event: QuizContract.UiEvent) = viewModelScope.launch { events.emit(event) }

    private val _sideEffect : Channel<QuizContract.SideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()
    private fun setEffect(effect: QuizContract.SideEffect) = viewModelScope.launch { _sideEffect.send(effect) }

    init{
        fetchAndPrepareQuiz()
        observeEvents()
        startTimer()
    }

    private fun observeEvents(){
        viewModelScope.launch {
            events.collect { event->
                when(event){
                    is QuizContract.UiEvent.AnswerSelected -> {
                        val currentQuestion = _state.value.questions[_state.value.tempQuestionInd]
                        val isCorrect = event.selectedAnswer == currentQuestion.solution
                        val newCorrect = if (isCorrect) _state.value.correctAnswers + 1 else _state.value.correctAnswers
                        val newIndex = _state.value.tempQuestionInd + 1
                        val finished = newIndex >= 2
                        val newScore = calculateScore(newCorrect, _state.value.remainingTime)

                        setState {
                            copy(
                                correctAnswers = if (isCorrect) correctAnswers + 1 else correctAnswers,
                                tempQuestionInd = tempQuestionInd + 1,
                                isQuizFinished = if(tempQuestionInd+1 >= 20){true}else{false},
                                scoreInQuiz = newScore


                            )
                        }
                    }
                    is QuizContract.UiEvent.CancelQuiz -> {
                        setEffect(QuizContract.SideEffect.NavigateCancelQuiz)

                    }
                    is QuizContract.UiEvent.NextQuestion -> {}
                    is QuizContract.UiEvent.SubmitResult -> {}
                    is QuizContract.UiEvent.TimeUp -> {

                        setState {
                            copy(
                                isQuizFinished = true

                            )
                        }
                    }
                }
            }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_state.value.remainingTime > 0 && !_state.value.isQuizFinished) {
                kotlinx.coroutines.delay(1000L)
                setState { copy(remainingTime = remainingTime - 1) }
            }

            if (_state.value.remainingTime == 0) {
                setEvent(QuizContract.UiEvent.TimeUp)
            }
        }
    }


    private fun fetchAndPrepareQuiz(){
        viewModelScope.launch {
            //breedRepository.fetchAndStoreImagesForAllBreeds()
            withContext(Dispatchers.IO) {
                val allBreeds = breedRepository
                    .observeAllBreedsRepository()
                    .first().filter { it.imageUrl != null }.shuffled()
                    .map { it.asBreed() }
                val selectedBreeds = allBreeds.take(20)


                val allQuestions = mutableListOf<QuizContract.Question>()
                for(breedElement in selectedBreeds){
                    val randomImageUrl = breedElement.imageUrl
                    if (randomImageUrl == null){
                        Log.d("QuizDebug", "Breed ${breedElement.id} (${breedElement.name}) nema slika — preskačem.")

                        continue
                    }
                    Log.d("QuizDebug", "Breed ${breedElement.id} (${breedElement.name}) IMAAA SLIKU.")

                    if(kotlin.random.Random.nextBoolean()){

                        val allBreedsInQuestion = (allBreeds - breedElement).shuffled().take(3).map { it.name } + breedElement.name
                        allQuestions.add(QuizContract.Question(allBreedsInQuestion, breedElement.name, breedElement.id, randomImageUrl, true))

                    }
                    else{
                        val currentTemperaments = breedElement.temperament

                        val otherTemperaments = allBreeds
                            .filter { it.id != breedElement.id }
                            .flatMap { it.temperament }
                            .filterNot { currentTemperaments.contains(it) }
                            .distinct()

                        val wrongTemperament = otherTemperaments.randomOrNull() ?: "Easygoing"
                        val correctTemperaments = currentTemperaments.shuffled().take(3)
                        val allTemperaments = (correctTemperaments + wrongTemperament).shuffled()




                        allQuestions.add(QuizContract.Question(allTemperaments, wrongTemperament, breedElement.id, randomImageUrl, false))
                    }
                }


                setState {
                    copy(
                        loading = false,
                        questions = allQuestions
                    )
                }
            }
        }


    }


    suspend fun waitForAtLeastOneImage(breedId: String): String? {
        return withTimeoutOrNull(4000) {
            breedRepository.observeAllImagesForBreedAlbum(breedId)
                .distinctUntilChanged()
                .first { it.isNotEmpty() }
                .shuffled()
                .map { it.asImage() }
                .firstOrNull()
                ?.imageUrl
        }
    }

    private fun calculateScore(correctAnswers: Int, remainingTime: Int): Double {
        val MVT = 300.0
        val BTO = correctAnswers.toDouble()
        val PVT = remainingTime.toDouble()

        return BTO * 2.5 * (1 + (PVT + 120) / MVT)
    }


}