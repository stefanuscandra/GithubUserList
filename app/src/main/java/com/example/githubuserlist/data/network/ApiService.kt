package com.example.githubuserlist.data.network

import com.example.githubuserlist.data.model.user.GithubUsersResponse
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getGithubUserList(): List<GithubUsersResponse>
}
