package com.example.githubuserlist.data.network

import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.data.model.user.GithubUsersResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getGithubUserList(): List<GithubUsersResponse>

    @GET("user/{account_id}")
    suspend fun getGithubUser(
        @Path("account_id") id: String
    ): GithubUserDetailResponse
}
