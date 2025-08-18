package com.example.githubuserlist.domain.repository

import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.data.model.user.GithubUsersResponse
import com.example.githubuserlist.data.model.user.UserRepositoryResponse
import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {

    fun getGithubUsers(): Flow<Result<List<GithubUsersResponse>>>

    fun getGithubUser(id: String): Flow<Result<GithubUserDetailResponse>>

    fun getUserRepository(id: String): Flow<Result<List<UserRepositoryResponse>>>
}