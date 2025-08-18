package com.example.githubuserlist.ui.screens.home

import com.example.githubuserlist.data.model.user.GithubUsersResponse
import com.example.githubuserlist.domain.repository.GithubUserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: GithubUserRepository

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk()
    }

    @Test
    fun `test getUserData() with success result should update data is correct `() = runTest {
        // Given
        val mockResult = listOf(
            GithubUsersResponse(
                id = 1,
                login = "mojodo",
                htmlUrl = "",
                avatarUrl = ""
            )
        )
        coEvery { repository.getGithubUsers() } returns flowOf(Result.success(mockResult))

        // When
        viewModel = HomeViewModel(repository = repository)
        viewModel.getUserData()

        // Then
        advanceUntilIdle()
        val expected = mockResult.first().login
        val actual = viewModel.userData.value.getStateData()?.first()?.login.orEmpty()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test getUserData() with error result should update data is correct `() = runTest {
        // Given
        coEvery { repository.getGithubUsers() } returns flowOf(Result.failure(Exception()))

        // When
        viewModel = HomeViewModel(repository = repository)
        viewModel.getUserData()

        // Then
        advanceUntilIdle()
        val actual = viewModel.userData.value.isError()
        Assert.assertEquals(true, actual)
    }

    @Test
    fun `test searchUser() with matching query should update state is correct`() = runTest {
        // Given
        val mockUsers = listOf(
            GithubUsersResponse(1, "mojodo", "url1", "avatar1"),
            GithubUsersResponse(2, "octocat", "url2", "avatar2")
        )
        coEvery { repository.getGithubUsers() } returns flowOf(Result.success(mockUsers))
        viewModel = HomeViewModel(repository)
        advanceUntilIdle()

        // When
        viewModel.searchUser("octo")

        // Then
        val actual = viewModel.searchedUserData.value
        Assert.assertEquals(1, actual.size)
        Assert.assertEquals("octocat", actual.first().login)
    }

    @Test
    fun `test searchUser() with not match query should update state is correct`() = runTest {
        // Given
        val mockUsers = listOf(
            GithubUsersResponse(1, "mojodo", "url1", "avatar1"),
            GithubUsersResponse(2, "octocat", "url2", "avatar2")
        )
        coEvery { repository.getGithubUsers() } returns flowOf(Result.success(mockUsers))
        viewModel = HomeViewModel(repository)
        advanceUntilIdle()

        // When
        viewModel.searchUser("random")

        // Then
        val actual = viewModel.searchedUserData.value
        Assert.assertTrue(actual.isEmpty())
    }
}