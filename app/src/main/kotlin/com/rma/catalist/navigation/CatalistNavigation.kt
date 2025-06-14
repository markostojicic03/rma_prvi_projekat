package com.rma.catalist.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.rma.catalist.breeds.details.BreedDetailsScreen
//import com.rma.catalist.breeds.details.BreedDetailsScreen
import com.rma.catalist.breeds.details.BreedDetailsScreenContract
import com.rma.catalist.breeds.details.BreedDetailsViewModel
import com.rma.catalist.breeds.gallery.BreedGalleryScreen
import com.rma.catalist.breeds.gallery.BreedGalleryViewModel
import com.rma.catalist.breeds.list.BreedListViewModel
import com.rma.catalist.breeds.list.BreedListScreen
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

    // Hilt sada zna da postoji argument u SavedStateHandle-u
    val viewModel = hiltViewModel<BreedGalleryViewModel>()

    BreedGalleryScreen(
        viewModel = viewModel,
        onImageClick = {
            // ovde možeš otvoriti full screen ako budeš dodavao
        }
    )
}


