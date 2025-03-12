package com.aos.oceankeeper_android_compose.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.aos.oceankeeper_android_compose.ui.theme.Pretendard
import com.letspl.oceankeeper.R
import kotlinx.coroutines.delay

@Composable
fun GlobalToastScreen() {
    val toastState by ToastHandler.toastState.collectAsState()
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(toastState) {
        isVisible = toastState.text.isNotBlank()
        delay(2000)
        isVisible = false
        ToastHandler.hide()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500)),
        modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(bottom = 84.dp), contentAlignment = Alignment.BottomCenter) {
            Row(
                modifier = Modifier
                    .size(320.dp, 52.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color("#99000000".toColorInt())),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.size(16.dp))
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(
                        if (
                            toastState.toastType == ToastType.SUCCESS) {
                            R.drawable.success_icon
                        } else {
                            R.drawable.error_icon
                        }
                    ), contentDescription = "toast icon"
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = toastState.text,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

