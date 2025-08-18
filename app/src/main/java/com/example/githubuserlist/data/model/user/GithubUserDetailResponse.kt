package com.example.githubuserlist.data.model.user

import com.squareup.moshi.Json

data class GithubUserDetailResponse(
    val id: Int,
    val name: String?,
    val login: String? = "",
    val company: String? = "",
    val blog: String? = "",
    val location: String? = "",
    val email: String? = "",
    val bio: String? = "",
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "html_url") val htmlUrl: String,
)
