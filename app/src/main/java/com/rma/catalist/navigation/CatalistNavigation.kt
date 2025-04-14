package com.rma.catalist.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rma.catalist.breeds.list.BreedListViewModel
import com.rma.catalist.breeds.list.BreedListScreen


@Composable
fun CatalistNavigation(){
    val navController = rememberNavController()// cuva nam instancu NavControlera i omogucava navigaciju izmedju razlicitih ekrana

    NavHost(
        navController = navController,
        startDestination = "start"
    ){
        breedList(route = "start", navController = navController)

    }
}


private fun NavGraphBuilder.breedList(
    route: String,
    navController: NavController,
) = composable(route = route) {
    val viewModel = hiltViewModel<BreedListViewModel>()
    BreedListScreen(
        viewModel = viewModel,
        clickOnBreed = null,

    )


}
