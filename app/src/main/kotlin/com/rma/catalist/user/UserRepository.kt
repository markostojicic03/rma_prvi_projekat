package com.rma.catalist.user

import com.rma.catalist.datastore.UserAccount
import com.rma.catalist.datastore.UserAccountStore
import jakarta.inject.Inject
import kotlinx.coroutines.flow.StateFlow


class UserRepository @Inject constructor(
    private val userAccountStore: UserAccountStore
) {
     val userAccount: StateFlow<UserAccount?>
        get() = userAccountStore.userAccount

     suspend fun saveUserAccount(userAccount: UserAccount) {
        userAccountStore.replaceUserAccount(userAccount)
    }

     suspend fun clearUserAccount() {
        userAccountStore.clearUserAccount()
    }
}