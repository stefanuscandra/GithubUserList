package com.example.githubuserlist.data.repository

import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.data.model.user.GithubUsersResponse
import com.example.githubuserlist.data.model.user.UserRepositoryResponse
import com.example.githubuserlist.data.network.ApiService
import com.example.githubuserlist.domain.repository.GithubUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GithubUserRepositoryImpl(private val apiService: ApiService) : GithubUserRepository {

    override fun getGithubUsers(): Flow<Result<List<GithubUsersResponse>>> = flow {
        val response = apiService.getGithubUserList()
        emit(Result.success(response))
    }.catch { e ->
        println("userDetail = ${e.message}")
        emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)

    override fun getGithubUser(id: String): Flow<Result<GithubUserDetailResponse>> = flow {
        val response = apiService.getGithubUser(id)
        emit(Result.success(response))
    }.catch { e ->
        println("userDetail = ${e.message}")
        emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)

    override fun getUserRepository(id: String): Flow<Result<List<UserRepositoryResponse>>> = flow {
        val response = apiService.getUserRepository(id)
        emit(Result.success(response))
    }.catch { e ->
        println("userDetail = ${e.message}")
        emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)
}
