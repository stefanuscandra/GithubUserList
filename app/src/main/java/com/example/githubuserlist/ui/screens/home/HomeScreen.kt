@file:OptIn(ExperimentalFoundationApi::class)

package com.example.githubuserlist.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.githubuserlist.R
import com.example.githubuserlist.data.model.user.GithubUsersResponse
import com.example.githubuserlist.ui.NavigationItem
import com.example.githubuserlist.ui.components.LoadingMV
import com.example.githubuserlist.ui.components.SearchFieldMV
import com.example.githubuserlist.ui.components.TopBarMV
import com.example.githubuserlist.ui.theme.Primary

@Composable
fun HomeScreen(
    navController: NavController? = null,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val userData by viewModel.userData.collectAsStateWithLifecycle()
    val searchedData by viewModel.searchedUserData.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBarMV(title = "Github Users")
        }
    ) { pad ->
        if (userData.isLoading()) {
            LoadingMV()
        } else if (userData.isSuccess()) {
            HomeContent(
                modifier = Modifier.padding(pad),
                userData = userData.getStateData().orEmpty(),
                searchedData = searchedData,
                onClickUser = { id ->
                    navController?.navigate(NavigationItem.Detail.withArgs(id.toString()))
                },
                onSearch = { query ->
                    viewModel.searchUser(query)
                }
            )
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    userData: List<GithubUsersResponse> = emptyList(),
    searchedData: List<GithubUsersResponse> = emptyList(),
    onClickUser: (id: Int) -> Unit = {},
    onSearch: (query: String) -> Unit = {},
) {
    var query by remember { mutableStateOf("") }
    LazyColumn(
        modifier = modifier
            .background(color = Color.White)
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        stickyHeader {
            SearchFieldMV(
                modifier = Modifier.background(Color.White),
                query = query,
                onQueryChange = { newValue ->
                    query = newValue
                    onSearch.invoke(query)
                },
            )
        }
        val itemData = userData.takeIf { query.isEmpty() } ?: searchedData
        if (itemData.isEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = if (query.isEmpty()) "No users available" else "No results found for \"$query\"",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(itemData) { data ->
                val itemShape = RoundedCornerShape(8.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(itemShape)
                        .clickable { onClickUser.invoke(data.id) }
                        .background(shape = itemShape, color = Color.White)
                        .border(
                            border = BorderStroke(width = 1.dp, color = Primary),
                            shape = itemShape
                        )
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(65.dp)
                            .clip(CircleShape),
                        model = data.avatarUrl,
                        contentDescription = "avatar",
                        placeholder = painterResource(R.drawable.ic_launcher_background)
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            style = TextStyle(fontSize = 18.sp),
                            fontWeight = FontWeight.SemiBold,
                            text = data.login
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            style = TextStyle(fontSize = 14.sp),
                            text = data.htmlUrl
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    HomeContent(
        userData = List(5) {
            GithubUsersResponse(
                id = 1,
                login = "test",
                avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4",
                htmlUrl = "https://github.com/pjhyett"
            )
        }
    )
}