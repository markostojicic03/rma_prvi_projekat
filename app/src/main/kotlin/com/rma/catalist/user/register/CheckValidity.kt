package com.rma.catalist.user.register

fun isUsernameValid(username: String): Boolean {
    val usernameRegex = "^[A-Za-z0-9_]+$".toRegex()
    return usernameRegex.matches(username)
}

fun isEmailValid(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return emailRegex.matches(email)
}