package com.example.zayatspexelsapp.app.ui.presentation.splash

import android.app.Activity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.zayatspexelsapp.R
import com.example.zayatspexelsapp.app.navigation.Screen
import com.example.zayatspexelsapp.app.ui.theme.Red
import com.example.zayatspexelsapp.app.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000
        ),
        label = "alpha animation"
    )

    val sizeAnim = animateDpAsState(
        targetValue = if (startAnimation) 120.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 2000
        ),
        label = "dp animation"
    )

    val window = (LocalContext.current as Activity).window
    window.statusBarColor = MaterialTheme.colorScheme.tertiary.toArgb()

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }
    Splash(alpha = alphaAnim.value, size = sizeAnim.value)
}

@Preview
@Composable
fun Splash(
    alpha: Float = 5F,
    size: Dp = 120.dp
) {
    Box(
        modifier = Modifier
            .background(Red)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .alpha(alpha = alpha),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
            )
            Text(
                textAlign = TextAlign.End,
                style = TextStyle(
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(600)
                ),
                text = stringResource(R.string.splash_screen_title)
            )
        }
    }
}