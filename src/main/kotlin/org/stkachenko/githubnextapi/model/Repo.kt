package org.stkachenko.githubnextapi.model

data class Repo(
    val name: String,
    val owner: Owner,
    val fork: Boolean
)