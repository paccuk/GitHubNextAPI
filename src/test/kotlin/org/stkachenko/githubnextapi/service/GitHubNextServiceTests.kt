package org.stkachenko.githubnextapi.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.stkachenko.githubnextapi.dto.RequestData
import org.stkachenko.githubnextapi.model.Branch
import org.stkachenko.githubnextapi.model.Commit
import org.stkachenko.githubnextapi.model.Owner
import org.stkachenko.githubnextapi.model.Repo
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(SpringExtension::class)
@SpringBootTest
class GitHubNextServiceTests {
    @MockkBean
    private lateinit var gitHubApiService: GitHubApiService

    @Autowired
    private lateinit var gitHubNextService: GitHubNextService

    @Test
    fun `getReposWithBranches should return list of RepoDTO delegating to the gitHubApi service successfully`() =
        runTest {
            val username = "testuser"
            val requestData = RequestData(username)

            val owner = Owner(username)
            val repos = listOf(
                Repo("repo-1", owner, false),
                Repo("repo-2", owner, true),
                Repo("repo-3", owner, false),
            )

            val branchesRepo1 = listOf(
                Branch("branch-1", Commit("sha-1")),
            )
            val branchesRepo2 = listOf(
                Branch("branch-2", Commit("sha-2")),
                Branch("branch-3", Commit("sha-3"))
            )
            val branchesRepo3 = emptyList<Branch>()

            coEvery { gitHubApiService.getUserRepos(username) } returns repos

            coEvery { gitHubApiService.getRepoBranches(username, "repo-1") } returns branchesRepo1
            coEvery { gitHubApiService.getRepoBranches(username, "repo-3") } returns branchesRepo3

            val result = gitHubNextService.getReposWithBranches(requestData)

            assertEquals(2, result.size)

            assertEquals("repo-1", result[0].name)
            assertEquals(branchesRepo1.size, result[0].branches.size)

            assertEquals("repo-3", result[1].name)
            assertEquals(branchesRepo3.size, result[1].branches.size)

            coVerify(exactly = 1) { gitHubApiService.getUserRepos(username) }
            coVerify(exactly = 1) { gitHubApiService.getRepoBranches(username, "repo-1") }
            coVerify(exactly = 0) { gitHubApiService.getRepoBranches(username, "repo-2") }
            coVerify(exactly = 1) { gitHubApiService.getRepoBranches(username, "repo-3") }
        }
}