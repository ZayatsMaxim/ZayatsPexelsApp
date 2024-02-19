package com.example.zayatspexelsapp.app.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zayatspexelsapp.R

@Composable
fun ErrorScreen(
    errorType: ViewModelErrorType,
    onRetryClicked: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when(errorType) {
                is ViewModelErrorType.NoInternetConnection -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_no_connection),
                        contentDescription = stringResource(
                            id = R.string.no_connection
                        )
                    )
                }
                else -> {
                    Text(
                        text = stringResource(id = errorType.textResourceId ?: R.string.default_error),
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }

            Text(
                text = stringResource(id = errorType.buttonTextResourceId),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.clickable {
                    onRetryClicked()
                }
            )
        }
    }
}