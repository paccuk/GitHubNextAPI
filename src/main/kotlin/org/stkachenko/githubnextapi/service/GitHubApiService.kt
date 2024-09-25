package org.stkachenko.githubnextapi.service

import io.netty.handler.timeout.TimeoutException
import kotlinx.coroutines.TimeoutCancellationException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.stkachenko.githubnextapi.model.Branch
import org.stkachenko.githubnextapi.model.Repo

@Service
class GitHubApiService(
    private val webClient: WebClient
) {
    @Retryable(
        retryFor = [TimeoutException::class, TimeoutCancellationException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 2000)
    )
    suspend fun getUserRepos(username: String): List<Repo> =
        webClient.get()
            .uri("/users/$username/repos")
            .retrieve()
            .awaitBody<List<Repo>>()

    @Retryable(
        retryFor = [TimeoutException::class, TimeoutCancellationException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 2000)
    )
    suspend fun getRepoBranches(owner: String, repo: String): List<Branch> =
        webClient.get()
            .uri("/repos/$owner/$repo/branches")
            .retrieve()
            .awaitBody<List<Branch>>()
}