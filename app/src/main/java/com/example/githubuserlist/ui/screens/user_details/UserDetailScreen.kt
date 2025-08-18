package com.example.githubuserlist.ui.screens.user_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.example.githubuserlist.data.model.user.GithubUserDetailResponse
import com.example.githubuserlist.data.model.user.UserRepositoryResponse
import com.example.githubuserlist.ui.components.LoadingMV
import com.example.githubuserlist.ui.components.TopBarMV
import com.example.githubuserlist.ui.theme.LightGray
import com.example.githubuserlist.ui.theme.Primary
import com.example.githubuserlist.ui.theme.Yellow

@Composable
fun UserDetailScreen(
    navController: NavController? = null,
    viewModel: UserDetailViewModel = hiltViewModel(),
) {
    val userDetail by viewModel.userDetail.collectAsStateWithLifecycle()
    val userRepo by viewModel.userRepo.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBarMV(
                title = "Github User",
                showBackArrow = true,
                onClickNavigation = {
                    navController?.navigateUp()
                }
            )
        }
    ) { pad ->
        if (userDetail.isLoading()) {
            LoadingMV()
        } else if (userDetail.isSuccess()) {
            UserDetailContent(
                modifier = Modifier.padding(pad),
                userDetail = userDetail.getStateData(),
                userRepos = userRepo.getStateData().orEmpty()
            )
        }
    }
}

@Composable
private fun UserDetailContent(
    modifier: Modifier = Modifier,
    userDetail: GithubUserDetailResponse?,
    userRepos: List<UserRepositoryResponse> = emptyList(),
) {
    userDetail?.let { user ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // header
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    model = user.avatarUrl,
                    contentDescription = "avatar",
                    placeholder = painterResource(R.drawable.ic_launcher_background)
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                text = user.name ?: "-",
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(color = Primary, thickness = 2.dp)
            Spacer(modifier = Modifier.size(24.dp))

            // bio
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                BioItem(
                    label = "Bio",
                    value = user.bio ?: "-"
                )
                BioItem(
                    label = "Location",
                    value = user.location ?: "-"
                )
                BioItem(
                    label = "Email",
                    value = user.email ?: "-"
                )
                BioItem(
                    label = "Company",
                    value = user.company ?: "-"
                )
                Spacer(modifier = Modifier.size(12.dp))
            }

            // repos
            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = "Repositories",
                color = Color.Gray,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.size(4.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                userRepos.forEach { repo ->
                    RepoItem(repo)
                }
            }
        }
    }
}

@Composable
private fun BioItem(modifier: Modifier = Modifier, label: String = "", value: String = "") {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(text = label, color = Color.Gray, fontSize = 12.sp)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = value, fontSize = 14.sp)
    }
}

@Composable
private fun RepoItem(repo: UserRepositoryResponse) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(color = LightGray, shape = RoundedCornerShape(8.dp))
            .padding(10.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 8.dp),
                text = repo.name ?: "",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
            Text(modifier = Modifier.padding(end = 4.dp), text = repo.stars.toString())
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Star,
                contentDescription = "stars",
                tint = Yellow,
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Url : ",
                fontSize = 14.sp,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                text = repo.url ?: "-",
                fontSize = 14.sp,
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            text = repo.description ?: "",
            fontSize = 14.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserDetailPreview() {

    UserDetailContent(
        userDetail = GithubUserDetailResponse(
            id = 1, name = "username", htmlUrl = "", avatarUrl = "", company = "company name", bio = "bio value"
        ),
        userRepos = List(3) { idx ->
            UserRepositoryResponse(id = idx, name = "repo $idx", description = "desc $idx")
        }
    )
}

@Preview
@Composable
private fun BioItemPreview() {
    BioItem(label = "company : ", value = "@abc, @def")
}