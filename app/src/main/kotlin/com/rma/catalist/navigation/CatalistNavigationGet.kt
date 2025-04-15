package com.rma.catalist.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry

const val BREED_ID_ARG = "breedId"
val NavBackStackEntry.breedIdOrThrow: Int
    get() = this.arguments?.getInt(BREED_ID_ARG) ?: error("$BREED_ID_ARG not found.")

val SavedStateHandle.bredId: Int?
    get() = this.get<Int>(BREED_ID_ARG)

val SavedStateHandle.breedIdOrThrow: Int
    get() = this.get<Int>(BREED_ID_ARG) ?: error("$BREED_ID_ARG not found.")