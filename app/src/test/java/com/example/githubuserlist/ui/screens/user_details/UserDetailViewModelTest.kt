package com.example.githubuserlist.ui.screens.user_details

import androidx.lifecycle.SavedStateHandle
import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.data.model.user.UserRepositoryResponse
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
class UserDetailViewModelTest {

    private lateinit var viewModel: UserDetailViewModel
    private lateinit var repository: GithubUserRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        savedStateHandle = SavedStateHandle()
        repository = mockk()
    }

    @Test
    fun `test getUserData() with success result should update data is correct `() = runTest {
        // Given
        val mockResult = GithubUserDetailResponse(
            id = 1,
            name = "mojodo",
            avatarUrl = "",
            htmlUrl = ""
        )
        coEvery { repository.getGithubUser(any()) } returns flowOf(Result.success(mockResult))
        coEvery { repository.getUserRepository(any()) } returns flowOf(Result.success(emptyList()))

        // When
        viewModel = UserDetailViewModel(repository = repository, savedStateHandle = savedStateHandle)
        viewModel.getUserDetailData("12")

        // Then
        advanceUntilIdle()
        val expected = mockResult.name
        val actual = viewModel.userDetail.value.getStateData()?.name.orEmpty()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test getUserData() with error result should update data is correct `() = runTest {
        // Given
        coEvery { repository.getGithubUser(any()) } returns flowOf(Result.failure(Exception()))
        coEvery { repository.getUserRepository(any()) } returns flowOf(Result.success(emptyList()))

        // When
        viewModel = UserDetailViewModel(repository = repository, savedStateHandle = savedStateHandle)
        viewModel.getUserDetailData("12")

        // Then
        advanceUntilIdle()
        val actual = viewModel.userDetail.value.isError()
        Assert.assertEquals(true, actual)
    }

    @Test
    fun `test getUserRepository() with success result should update data is correct `() = runTest {
        // Given
        val mockResult = GithubUserDetailResponse(
            id = 1,
            name = "mojodo",
            avatarUrl = "",
            htmlUrl = ""
        )
        val mockList = listOf(
            UserRepositoryResponse(
                id = 1,
                name = "repo 1",
            )
        )
        coEvery { repository.getGithubUser(any()) } returns flowOf(Result.success(mockResult))
        coEvery { repository.getUserRepository(any()) } returns flowOf(Result.success(mockList))

        // When
        viewModel = UserDetailViewModel(repository = repository, savedStateHandle = savedStateHandle)
        viewModel.getUserRepository("12")

        // Then
        advanceUntilIdle()
        val expected = mockList.first().name
        val actual = viewModel.userRepo.value.getStateData()?.first()?.name.orEmpty()
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `test getUserRepository() with error result should update data is correct `() = runTest {
        // Given
        coEvery { repository.getGithubUser(any()) } returns flowOf(Result.failure(Exception()))
        coEvery { repository.getUserRepository(any()) } returns flowOf(Result.failure(Exception()))

        // When
        viewModel = UserDetailViewModel(repository = repository, savedStateHandle = savedStateHandle)
        viewModel.getUserRepository("12")

        // Then
        advanceUntilIdle()
        val actual = viewModel.userRepo.value.isError()
        Assert.assertEquals(true, actual)
    }
}