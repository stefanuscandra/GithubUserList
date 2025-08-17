package com.example.githubuserlist.data.repository

import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.data.model.user.GithubUsersResponse
import com.example.githubuserlist.data.network.ApiService
import com.example.githubuserlist.domain.repository.GithubUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GithubUserRepositoryImpl(private val apiService: ApiService) : GithubUserRepository {

    override fun getGithubUsers(): Flow<Result<List<GithubUsersResponse>>> = flow {
        val response = apiService.getGithubUserList()
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }

    override fun getGithubUser(id: Int): Flow<Result<GithubUserDetailResponse>> = flow {
        val response = apiService.getGithubUser(id.toString())
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
    }
}
