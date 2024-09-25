package org.stkachenko.githubnextapi.dto


data class RepoDTO(
    val name: String,
    val owner: String,
    val branches: List<BranchDTO>
)