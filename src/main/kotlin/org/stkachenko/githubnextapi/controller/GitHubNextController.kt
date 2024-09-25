package org.stkachenko.githubnextapi.controller

import org.springframework.web.bind.annotation.*
import org.stkachenko.githubnextapi.dto.RepoDTO
import org.stkachenko.githubnextapi.dto.RequestData
import org.stkachenko.githubnextapi.service.GitHubNextService

@RestController
@RequestMapping("/api/githubnext")
class GitHubNextController(
    private val service: GitHubNextService
) {
    @GetMapping("/users/{username}/repos")
    suspend fun getUserRepos(@PathVariable username: String): List<RepoDTO> {
        val requestData = RequestData(username)
        return service.getReposWithBranches(requestData)
    }
}