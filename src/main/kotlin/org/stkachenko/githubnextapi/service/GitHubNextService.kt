package org.stkachenko.githubnextapi.service

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.stkachenko.githubnextapi.dto.BranchDTO
import org.stkachenko.githubnextapi.dto.RepoDTO
import org.stkachenko.githubnextapi.dto.RequestData
import org.stkachenko.githubnextapi.logger.log

@Service
class GitHubNextService(
    private val gitHubApiService: GitHubApiService
) {
    suspend fun getReposWithBranches(req: RequestData): List<RepoDTO> = coroutineScope {
        val repos = gitHubApiService.getUserRepos(req.username)
        log.info("${req.username}: loaded ${repos.size} repos")

        val deferreds: List<Deferred<RepoDTO>> = repos
            .filter { !it.fork }
            .map { repo ->
                async {
                    log.info("staring loading branches for ${repo.name}")

                    val branches = gitHubApiService.getRepoBranches(req.username, repo.name)
                        .map { branch ->
                            BranchDTO(branch.name, branch.commit.sha)
                        }

                    log.info("${repo.name}: loaded ${branches.size} branches")

                    RepoDTO(repo.name, req.username, branches)
                }
            }

        deferreds.awaitAll()
    }
}