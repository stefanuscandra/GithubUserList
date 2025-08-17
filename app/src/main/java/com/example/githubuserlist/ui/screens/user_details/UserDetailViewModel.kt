package com.example.githubuserlist.ui.screens.user_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserlist.data.model.DataState
import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
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
    private val repository: GithubUserRepository
) : ViewModel() {

    private val _userDetail = MutableStateFlow<DataState<GithubUserDetailResponse>>(DataState.Idle)
    val userDetail = _userDetail.asStateFlow()

    init {
        getUserDetailData(1)
    }

    internal fun getUserDetailData(id: Int) {
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
}
