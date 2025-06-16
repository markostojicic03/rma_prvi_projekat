package com.rma.catalist.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.Uri
import com.rma.catalist.breeds.details.BreedDetailsScreen
//import com.rma.catalist.breeds.details.BreedDetailsScreen
import com.rma.catalist.breeds.details.BreedDetailsScreenContract
import com.rma.catalist.breeds.details.BreedDetailsViewModel
import com.rma.catalist.breeds.gallery.BreedGalleryScreen
import com.rma.catalist.breeds.gallery.BreedGalleryViewModel
import com.rma.catalist.breeds.gallery.ImageFullscreenScreen
import com.rma.catalist.breeds.list.BreedListViewModel
import com.rma.catalist.breeds.list.BreedListScreen
import com.rma.catalist.breeds.quiz.QuizScreen
import com.rma.catalist.breeds.quiz.QuizStartScreen
import com.rma.catalist.breeds.quiz.QuizViewModel
import com.rma.catalist.user.register.RegisterScreen
import com.rma.catalist.user.register.RegisterViewModel


private fun NavController.navigateToDetails(breedId: String) {
    this.navigate(route = "details/$breedId")
}

@Composable
fun CatalistNavigation(){
    val navController = rememberNavController()// cuva nam instancu NavControlera i omogucava navigaciju izmedju razlicitih ekrana

    NavHost(
        navController = navController,
        startDestination = "register"
    ){
        registerScreen(route = "register", navController = navController)

        breedList(route = "start", navController = navController)

        breedDetails(

            route = "details/{$BREED_ID_ARG}",
            arguments = listOf(
                navArgument(name = BREED_ID_ARG){
                    type = NavType.StringType
                    nullable = false
                }
            ),
            navController = navController
        )

        breedGallery(
            route = "gallery/{$BREED_ID_ARG}",
            arguments = listOf(
                navArgument(name = BREED_ID_ARG){
                    type = NavType.StringType
                    nullable = false
                }
            ),
            navController = navController
        )

        imageFullscreen(
            route = "fullscreen/{$IMAGE_URL_ARG}",
            arguments = listOf(
                navArgument(name = IMAGE_URL_ARG) {
                    type = NavType.StringType
                }
            ),
            navController = navController
        )

        startQuizNavigate(
            route = "quiz/start",
            navController = navController,
        )

        quizMainScreen(
            route = "quiz",
            navController = navController,
        )

    }
}
private fun NavGraphBuilder.registerScreen(
    route: String,
    navController: NavController
) = composable(route = route) {
    val viewModel = hiltViewModel<RegisterViewModel>()

    RegisterScreen(
        viewModel = viewModel,
        onRegisterSuccess = {
            navController.navigate("start")
        }
    )
}

private fun NavGraphBuilder.breedList(
    route: String,
    navController: NavController,
) = composable(route = route) {
    val viewModel = hiltViewModel<BreedListViewModel>()
    BreedListScreen(
        navController = navController,
        viewModel = viewModel,
        clickOnBreed = { breedId ->
            navController.navigateToDetails(breedId = breedId)
        }

    )
}

private fun NavGraphBuilder.breedDetails(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController,
) = composable(route = route, arguments = arguments){
    navBackStackEntry : NavBackStackEntry->
    val viewModel = hiltViewModel<BreedDetailsViewModel>()
    val breedId = navBackStackEntry.arguments?.getString(BREED_ID_ARG)
        ?: error("Breed ID is missing!")

    val eventPublisherFromViewModel = viewModel::setEvent
    LaunchedEffect(key1 = breedId) {
        viewModel.setEvent(BreedDetailsScreenContract.UiEvent.OpenedScreen(breedId))
    }

    //eventPublisherFromViewModel(BreedDetailsScreenContract.UiEvent.OpenedScreen(breedId))
    BreedDetailsScreen(
        navController = navController,
        viewModel = viewModel,
        breedId = breedId,
        onClose =  {
            navController.navigateUp()
        },
    )
}

private fun NavGraphBuilder.breedGallery(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController,
) = composable(route = route, arguments) { navBackStackEntry: NavBackStackEntry ->

    val breedId = navBackStackEntry.arguments?.getString(BREED_ID_ARG)
        ?: error("Breed ID is missing!")


    val viewModel = hiltViewModel<BreedGalleryViewModel>()

    BreedGalleryScreen(
        viewModel = viewModel,
        onImageClick = {
            imageUrl ->
            val encodedUrl = android.net.Uri.encode(imageUrl)
            navController.navigate("fullscreen/$encodedUrl")
        }
    )
}

private fun NavGraphBuilder.imageFullscreen(
    route: String,
    arguments: List<NamedNavArgument>,
    navController: NavController
) = composable(route = route, arguments = arguments) { backStackEntry ->
    val imageUrl = backStackEntry.arguments?.getString(IMAGE_URL_ARG)
        ?: error("Image URL is missing!")

    ImageFullscreenScreen(
        imageUrl = imageUrl,
        onBack = { navController.navigateUp() }
    )
}


private fun NavGraphBuilder.startQuizNavigate(
    route: String,
    navController: NavController
) = composable(route = route) { backStackEntry ->

    QuizStartScreen(
        onStartQuizClick = { navController.navigate("quiz") },
        navController = navController
    )

}


fun NavGraphBuilder.quizMainScreen(
    route: String,
    navController: NavController,
) = composable(route = route) {

    val viewModel = hiltViewModel<QuizViewModel>()
    QuizScreen(viewModel)

}


