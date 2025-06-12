package com.rma.catalist.datastore

import kotlinx.serialization.Serializable


@Serializable
data class UserAccount(
    val name: String = "",
    val lastName: String = "",
    val username: String = "", // sadrzi samo slova, brojeve i underscore _
    val email: String = "",

    )
