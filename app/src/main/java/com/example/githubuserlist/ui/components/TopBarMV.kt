package com.example.githubuserlist.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubuserlist.R
import com.example.githubuserlist.ui.theme.Primary

@Composable
fun TopBarMV(
    modifier: Modifier = Modifier,
    title: String = "",
    showBackArrow: Boolean = false,
    onClickNavigation: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(64.dp)
            .background(Primary),
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackArrow) {
                Box(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = false),
                    ) { onClickNavigation.invoke() }
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_navigation_back),
                        contentDescription = "navigation_back",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Spacer(Modifier.width(12.dp))
            }
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray,
        )
    }
}

@Preview
@Composable
private fun TopBarMVPreview() {
    TopBarMV(
        title = "Title",
        showBackArrow = true
    )
}