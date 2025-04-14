package com.rma.catalist.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rma.catalist.breeds.details.BreedDetailsScreen
import com.rma.catalist.breeds.list.BreedListViewModel
import com.rma.catalist.breeds.list.BreedListScreen


const val BREED_ID_ARG = "breedId"
val NavBackStackEntry.breedIdOrThrow: Int
    get() = this.arguments?.getInt(BREED_ID_ARG) ?: error("$BREED_ID_ARG not found.")

private fun NavController.navigateToDetails(breedId: Int) {
    this.navigate(route = "details/$breedId")
}

@Composable
fun CatalistNavigation(){
    val navController = rememberNavController()// cuva nam instancu NavControlera i omogucava navigaciju izmedju razlicitih ekrana

    NavHost(
        navController = navController,
        startDestination = "start"
    ){
        breedList(route = "start", navController = navController)




        composable(
            route = "details/{$BREED_ID_ARG}",
            arguments = listOf(
                navArgument(name = BREED_ID_ARG) {
                    type = NavType.IntType
                    nullable = false
                }
            ),
        ) { navBackStackEntry ->
            val breedId = navBackStackEntry.breedIdOrThrow

            BreedDetailsScreen(
                breedId = breedId,
                onClose = {
                    navController.navigateUp()
                }
            )
        }
    }
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
