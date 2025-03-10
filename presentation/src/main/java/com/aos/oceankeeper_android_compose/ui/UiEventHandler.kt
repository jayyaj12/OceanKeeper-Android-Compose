package com.aos.oceankeeper_android_compose.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.oceankeeper_android_compose.R
import androidx.core.graphics.toColorInt
import com.aos.oceankeeper_android_compose.base.BaseViewModel
import kotlinx.coroutines.delay
import timber.log.Timber

object UiEventHandler {

    @Composable
    fun EventUi(event: BaseViewModel.Event) {
        var isLoading by remember { mutableStateOf(false) }
        var toastText by remember { mutableStateOf("") }
        var isToastError by remember { mutableStateOf(false) }
        val currentEvent by rememberUpdatedState(event) // ✅ 항상 최신 이벤트 감지
        Timber.e("EventUi")

        LaunchedEffect(currentEvent) {
            when (event) {
                is BaseViewModel.Event.ShowToast -> {
                    toastText = event.message
                    isToastError = false
                    delay(2000)
                    toastText = ""
                }

                is BaseViewModel.Event.ShowErrorToast -> {
                    Timber.e("ShowErrorToast1")
                    isLoading = false
                    toastText = event.message
                    isToastError = true
                    delay(2000)
                    toastText = ""
                    Timber.e("ShowErrorToast2")
                }

                is BaseViewModel.Event.ShowToastRes -> toastText = event.message.toString()
                is BaseViewModel.Event.ExpiredToken -> TODO()
                is BaseViewModel.Event.ShowLoading -> isLoading = true
                is BaseViewModel.Event.HideLoading -> isLoading = false
                is BaseViewModel.Event.Nothing -> {}
            }
        }

        LoadingUi(isLoading)
        ToastUi(toastText, isToastError)
    }

    @Composable
    private fun LoadingUi(isLoading: Boolean) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }

    @Composable
    private fun ToastUi(text: String, isToastError: Boolean) {
        var isVisible by remember { mutableStateOf(false) }

        LaunchedEffect(text) {
            isVisible = text.isNotBlank()
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 84.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(
                        id = if (isToastError) {
                            R.drawable.error_icon
                        } else {
                            R.drawable.success_icon
                        }
                    ), contentDescription = "toast icon"
                )
                Text(
                    modifier = Modifier
                        .size(320.dp, 72.dp)
                        .background(color = Color("#99000000".toColorInt()))
                        .clip(RoundedCornerShape(8.dp))
                        .wrapContentHeight(Alignment.CenterVertically),
                    text = text,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}