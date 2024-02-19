package com.example.zayatspexelsapp.app.ui.presentation.details

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.zayatspexelsapp.R

@Composable
fun PhotoDetails(
    url: String?,
    viewModel: DetailsPageViewModel
) {
    if (url != null) {
        AsyncImage(
            modifier = Modifier
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(24.dp))
                .fillMaxSize(),
            model = url,
            contentDescription = "Image",
            onLoading = {
                   viewModel.changeLoadingStatus(true)
            },
            placeholder = if (isSystemInDarkTheme()) painterResource(id = R.drawable.pl2)
                            else painterResource(id = R.drawable.pl2_dark),
            onSuccess = {
                viewModel.changeLoadingStatus(false)
            }
        )
    }
}