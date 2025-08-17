package com.example.githubuserlist.ui.screens.user_details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.ui.components.LoadingMV
import com.example.githubuserlist.ui.components.TopBarMV

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel = hiltViewModel(),
) {
    val userDetail by viewModel.userDetail.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBarMV(title = "Github Users")
        }
    ) { pad ->
        if (userDetail.isLoading()) {
            LoadingMV()
        } else if (userDetail.isSuccess()) {
            UserDetailContent(modifier = Modifier.padding(pad), null)
        }
    }

}

@Composable
private fun UserDetailContent(
    modifier: Modifier = Modifier,
    userDetail: GithubUserDetailResponse?,
) {

}

@Preview(showBackground = true)
@Composable
private fun UserDetailPreview() {

    UserDetailContent(
        userDetail = GithubUserDetailResponse(
            id = 1, name = "username", htmlUrl = "", avatarUrl = ""
        )
    )
}