package com.rma.catalist.user.register

import com.rma.catalist.datastore.UserAccount

interface RegisterContract {
    data class RegisterUiState(
        val success: Boolean = false
    )
}