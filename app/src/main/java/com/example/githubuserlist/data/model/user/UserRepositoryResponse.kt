package com.example.githubuserlist.data.model.user

import com.squareup.moshi.Json

data class UserRepositoryResponse(
    val id: Int,
    val name: String? = "",
    val description: String? = "",
    @Json(name = "html_url") val url: String? = "",
    @Json(name = "stargazers_count") val stars: Int = 0,
)
