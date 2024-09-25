package org.stkachenko.githubnextapi.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.stkachenko.githubnextapi.dto.BranchDTO
import org.stkachenko.githubnextapi.dto.RepoDTO
import org.stkachenko.githubnextapi.dto.RequestData
import org.stkachenko.githubnextapi.service.GitHubNextService
import kotlin.test.Test


@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [GitHubNextController::class])
class GitHubNextControllerTests {
    @MockkBean
    private lateinit var gitHubNextService: GitHubNextService

    @Autowired
    private lateinit var controller: GitHubNextController

    @Test
    fun `should return repos and branches DTOs delegating to the gitHubNext service`() = runTest {
        val username = "testuser"
        val requestData = RequestData(username)

        val dtoBranchesRepo1 = listOf(
            BranchDTO("branch-1", "sha-1"),
        )
        val dtoBranchesRepo2 = listOf(
            BranchDTO("branch-2", "sha-2"),
            BranchDTO("branch-3", "sha-3")
        )
        val dtoBranchesRepo3 = emptyList<BranchDTO>()

        val dtoRepos = listOf(
            RepoDTO("repo-1", username, dtoBranchesRepo1),
            RepoDTO("repo-2", username, dtoBranchesRepo2),
            RepoDTO("repo-3", username, dtoBranchesRepo3),
        )

        coEvery { gitHubNextService.getReposWithBranches(requestData) } returns dtoRepos

        assertThat(controller.getUserRepos(username)).isEqualTo(dtoRepos)
        coVerify { gitHubNextService.getReposWithBranches(requestData) }
    }
}