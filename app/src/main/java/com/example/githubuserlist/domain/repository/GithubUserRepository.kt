package com.example.githubuserlist.domain.repository

import com.example.githubuserlist.data.model.user.GithubUsersResponse
import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {
    fun getGithubUsers(): Flow<Result<List<GithubUsersResponse>>>
}