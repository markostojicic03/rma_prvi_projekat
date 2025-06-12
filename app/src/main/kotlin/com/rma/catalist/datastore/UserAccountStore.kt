package com.rma.catalist.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserAccountStore@Inject constructor(
    private val persistence: DataStore<UserAccount?>,
)  {
    private val scope = CoroutineScope(Dispatchers.IO)

    val userAccount = persistence.data
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = runBlocking { persistence.data.first() },
        )


    suspend fun replaceUserAccount(userAccount: UserAccount) {
        persistence.updateData { existing ->
            userAccount
        }
    }

//    suspend fun updateUserAccount(reducer: UserAccount.() -> UserAccount) {
//        persistence.updateData(reducer)
//    }

    suspend fun clearUserAccount() {
        persistence.updateData { null }
    }

}