package com.example.githubuserlist.data.network

import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.data.model.user.GithubUsersResponse
import com.example.githubuserlist.data.model.user.UserRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getGithubUserList(): List<GithubUsersResponse>

    @GET("user/{account_id}")
    suspend fun getGithubUser(
        @Path("account_id") id: String,
    ): GithubUserDetailResponse

    @GET("user/{username}/repos")
    suspend fun getUserRepository(
        @Path("username") id: String,
    ): List<UserRepositoryResponse>
}
