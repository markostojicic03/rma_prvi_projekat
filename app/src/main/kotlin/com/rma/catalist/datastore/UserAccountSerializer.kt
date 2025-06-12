package com.rma.catalist.datastore

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import java.io.InputStream
import java.io.OutputStream

class UserAccountSerializer : androidx.datastore.core.Serializer<UserAccount> {

    private val jsonSerializer = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override val defaultValue: UserAccount = UserAccount()

    override suspend fun readFrom(input: InputStream): UserAccount {
        return withContext(Dispatchers.IO) {
            val inputString = input.bufferedReader().use { it.readText() }
            Json.decodeFromString(UserAccount.serializer(), inputString)
        }
    }

    override suspend fun writeTo(
        t: UserAccount,
        output: OutputStream
    ) {
        withContext(Dispatchers.IO) {
            val outputString = Json.encodeToString(t)
            output.write(outputString.toByteArray())
        }
    }


}