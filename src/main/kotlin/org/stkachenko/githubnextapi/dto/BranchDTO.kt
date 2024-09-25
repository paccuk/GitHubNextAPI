package org.stkachenko.githubnextapi.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class BranchDTO(
    val name: String,
    @JsonProperty("last_commit_sha") val lastCommitSha: String,
)
