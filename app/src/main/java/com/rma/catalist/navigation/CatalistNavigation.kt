package com.rma.catalist.navigation


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun CatalistNavigation(){
    val navController = rememberNavController()// cuva nam instancu NavControlera i omogucava navigaciju izmedju razlicitih ekrana

    NavHost(
        navController = navController,
        startDestination = "start"
    ){

    }
}

/*
private fun NavGraphBuilder.passwordList(
    route: String,
    navController: NavController,
) = composable(route = route) {
    val viewModel = hiltViewModel<PasswordListViewModel>()

    PasswordListScreen(
        viewModel = viewModel,
        onAddPasswordClick = {
            navController.navigateToEditor()
        },
        onPasswordListClick = { passwordId ->
            navController.navigateToDetails(passwordId = passwordId)
        }
    )
}
*/