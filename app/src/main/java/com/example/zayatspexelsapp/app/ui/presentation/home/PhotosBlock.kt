package com.example.zayatspexelsapp.app.ui.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.zayatspexelsapp.R
import com.example.zayatspexelsapp.data.models.Photo

@Composable
fun PhotosBlock(
    photos: List<Photo>,
    viewModel: HomePageViewModel,
    onPhotoClicked: (String) -> Unit = {},
) {
    viewModel.changeLoadingStatus(true)

    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 64.dp),
        columns = StaggeredGridCells.Fixed(2)
    ) {
        items(photos) {
            PhotoItem(
                photo = it,
                viewModel = viewModel,
                onClick = onPhotoClicked,
            )
        }
    }
}

@Composable
fun PhotoItem(
    photo: Photo,
    viewModel: HomePageViewModel,
    onClick: (String) -> Unit = {},
) {
    AsyncImage(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                onClick(photo.id.toString())
            },
        model = photo.src.medium,
        contentDescription = "Image",
        contentScale = ContentScale.FillWidth,
        placeholder = if (isSystemInDarkTheme()) painterResource(id = R.drawable.placeholder_dark)
                        else painterResource(id = R.drawable.placeholder_light),
        onSuccess = {
            viewModel.changeLoadingStatus(false)
        }
    )
}