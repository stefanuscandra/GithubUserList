package com.example.githubuserlist.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.githubuserlist.data.model.user.GithubUsersResponse

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    val userData by viewModel.userData.collectAsStateWithLifecycle()
    HomeContent(userData = userData.getStateData().orEmpty())
}

@Composable
private fun HomeContent(
    userData: List<GithubUsersResponse> = emptyList()
) {
    Scaffold { pad ->
        LazyColumn(
            modifier = Modifier
                .background(color = Color.White)
                .padding(pad)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(userData) { data ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(shape = RoundedCornerShape(8.dp), color = Color.White)
                        .border(
                            border = BorderStroke(width = 1.dp, color = Color.LightGray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(65.dp)
                            .clip(CircleShape),
                        model = data.avatarUrl,
                        contentDescription = "avatar"
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