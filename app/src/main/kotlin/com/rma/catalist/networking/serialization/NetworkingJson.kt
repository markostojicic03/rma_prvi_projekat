package com.rma.catalist.networking.serialization

import kotlinx.serialization.json.Json


val NetworkingJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
}