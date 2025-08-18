package com.example.githubuserlist.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserlist.data.model.DataState
import com.example.githubuserlist.data.model.user.GithubUsersResponse
import com.example.githubuserlist.domain.repository.GithubUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GithubUserRepository,
) : ViewModel() {

    private val _userData = MutableStateFlow<DataState<List<GithubUsersResponse>>>(DataState.Idle)
    val userData = _userData.asStateFlow()

    private val _searchedUserData = MutableStateFlow<List<GithubUsersResponse>>(emptyList())
    val searchedUserData = _searchedUserData.asStateFlow()

    init {
        getUserData()
    }

    internal fun getUserData() {
        viewModelScope.launch {
            repository.getGithubUsers()
                .onStart {
                    _userData.update { DataState.Loading }
                }
                .collect { result ->
                    result.onSuccess { successResult ->
                        _userData.update { DataState.Success(successResult) }
                    }.onFailure { errorResult ->
                        DataState.Error(errorResult.message.orEmpty())
                    }
                }
        }
    }

    internal fun searchUser(query: String) {
        val result = _userData.value.getStateData()?.let { user ->
            user.filter { it.login.contains(query, ignoreCase = true) }
        }

        _searchedUserData.update { result.orEmpty() }
    }
}