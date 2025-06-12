package com.rma.catalist.user.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rma.catalist.datastore.UserAccount
import com.rma.catalist.datastore.UserAccountStore
import com.rma.catalist.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userAccountStore: UserAccountStore


): ViewModel() {

    private val _uiState = MutableStateFlow(RegisterContract.RegisterUiState())
    val uiState: StateFlow<RegisterContract.RegisterUiState> = _uiState.asStateFlow()

    val userAccount: StateFlow<UserAccount?> = userAccountStore.userAccount

    fun isRegistered(): Boolean {
        val account = userAccount.value
        return account != null &&
                account.name.isNotBlank() &&
                account.username.isNotBlank() &&
                account.email.isNotBlank()
    }

    fun registerUser(firstName: String, lastName: String, username: String, email: String) {
        if (
            firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            username.isNotBlank() &&
            email.isNotBlank()
        ) {
            val userAccount = UserAccount(
                name = firstName,
                lastName = lastName,
                username = username,
                email = email
            )

            viewModelScope.launch {
                userAccountStore.replaceUserAccount(userAccount)
                _uiState.value = RegisterContract.RegisterUiState(success = true)
            }
        }
    }
}
