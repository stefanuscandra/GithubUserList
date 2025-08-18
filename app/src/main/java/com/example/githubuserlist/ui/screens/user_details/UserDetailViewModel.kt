package com.example.githubuserlist.ui.screens.user_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserlist.data.model.DataState
import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.data.model.user.UserRepositoryResponse
import com.example.githubuserlist.domain.repository.GithubUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: GithubUserRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val userId = savedStateHandle["id"] ?: ""

    private val _userDetail = MutableStateFlow<DataState<GithubUserDetailResponse>>(DataState.Idle)
    val userDetail = _userDetail.asStateFlow()

    private val _userRepo = MutableStateFlow<DataState<List<UserRepositoryResponse>>>(DataState.Idle)
    val userRepo = _userRepo.asStateFlow()

    init {
        getUserDetailData(userId)
        getUserRepository(userId)
    }

    internal fun getUserDetailData(id: String) {
        viewModelScope.launch {
            repository.getGithubUser(id)
                .onStart {
                    _userDetail.update { DataState.Loading }
                }
                .collect { result ->
                    result.onSuccess { successResult ->
                        _userDetail.update { DataState.Success(successResult) }
                    }.onFailure { errorResult ->
                        DataState.Error(errorResult.message.orEmpty())
                    }
                }
        }
    }

    internal fun getUserRepository(id: String) {
        viewModelScope.launch {
            repository.getUserRepository(id)
                .onStart {
                    _userDetail.update { DataState.Loading }
                }
                .collect { result ->
                    result.onSuccess { successResult ->
                        _userRepo.update { DataState.Success(successResult) }
                        println("userRepo ${_userRepo.value}")
                    }.onFailure { errorResult ->
                        DataState.Error(errorResult.message.orEmpty())
                    }
                }
        }
    }
}
