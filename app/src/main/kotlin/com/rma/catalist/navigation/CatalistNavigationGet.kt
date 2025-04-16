package com.rma.catalist.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry

const val BREED_ID_ARG = "breedId"
val NavBackStackEntry.breedIdOrThrow: String
    get() = this.arguments?.getString(BREED_ID_ARG) ?: error("$BREED_ID_ARG not found.")

val SavedStateHandle.bredId: String?
    get() = this.get<String>(BREED_ID_ARG)

val SavedStateHandle.breedIdOrThrow: String
    get() = this.get<String>(BREED_ID_ARG) ?: error("$BREED_ID_ARG not found.")