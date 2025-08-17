package com.example.githubuserlist.data.model.user

import com.squareup.moshi.Json

data class GithubUsersResponse(
    val id: Int,
    val login: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "html_url") val htmlUrl: String
)
