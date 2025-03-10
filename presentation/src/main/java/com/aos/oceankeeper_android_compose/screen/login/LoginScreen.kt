package com.aos.oceankeeper_android_compose.screen.login

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aos.oceankeeper_android_compose.base.BaseViewModel
import com.aos.oceankeeper_android_compose.base.LoadingState
import com.aos.oceankeeper_android_compose.ui.UiEventHandler
import com.aos.oceankeeper_android_compose.ui.theme.Pretendard
import com.example.oceankeeper_android_compose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun LoginScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LoginUi()
    }
}

@Composable
fun LoginUi(viewModel: LoginViewModel = hiltViewModel()) {
    val event by viewModel.baseSharedFlow.collectAsState(BaseViewModel.Event.Nothing)
    val loginState by viewModel.login

    Box {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(top = 165.dp),
                text = "오션키퍼가 되어\n바다를 구해주세요!",
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color("#03A7B2".toColorInt()),
                fontSize = 28.sp,
                lineHeight = 38.sp
            )
            Spacer(modifier = Modifier.size(60.dp))
            LoginBtnUi(
                icon = R.drawable.naver_symbol,
                text = "네이버로 계속하기",
                textColor = Color.White,
                backgroundColor = Color("#03C75A".toColorInt())
            ) {
                // 네이버로 계속하기 클릭
            }
            Spacer(modifier = Modifier.size(12.dp))
            LoginBtnUi(
                icon = R.drawable.kakao_symbol,
                text = "카카오톡으로 계속하기",
                textColor = Color("#181600".toColorInt()),
                backgroundColor = Color("#FAE100".toColorInt())
            ) {
                Timber.e("LoginBtnUi")
                // 카카오톡으로 계속하기 클릭
                viewModel.onClickedKakaoLogin()
            }
            Spacer(modifier = Modifier.size(12.dp))
            LoginBtnUi(
                icon = R.drawable.apple_symbol,
                text = "Apple로 계속하기",
                textColor = Color.White,
                backgroundColor = Color("#000000".toColorInt())
            ) {
                // Apple 로 계속하기 클릭
            }
        }

        LaunchedEffect(Unit) {
            viewModel.baseSharedFlow.collectLatest { event ->
                Timber.e("LaunchedEffect received: $event")
                when (event) {
                    is BaseViewModel.Event.ShowToast -> { }
                    is BaseViewModel.Event.ShowErrorToast -> { }
                    is BaseViewModel.Event.ShowToastRes -> {}
                    is BaseViewModel.Event.ExpiredToken -> TODO()
                    is BaseViewModel.Event.ShowLoading -> LoadingState.show()
                    is BaseViewModel.Event.HideLoading -> LoadingState.hide()
                    is BaseViewModel.Event.Nothing -> {}
                }
            }
        }


//        if(loginState.text.isNotBlank()) {
//            Timber.e("login $loginState")
//            if(loginState.isError) {
//                ToastUi(text = loginState.text, icon = R.drawable.error_icon)
//            } else {
//                ToastUi(text = loginState.text, icon = R.drawable.success_icon)
//            }
//        }
    }
}

@Composable
fun LoginBtnUi(
    @DrawableRes icon: Int,
    text: String,
    textColor: Color,
    backgroundColor: Color,
    onClickLoginBtn: () -> Unit,
) {
    Button(
        onClick = onClickLoginBtn,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(start = 20.5.dp, end = 20.5.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = Color.White,
            disabledContainerColor = backgroundColor,
            disabledContentColor = Color.White
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(icon),
                contentDescription = "로그인 타입 아이콘",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = text,
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = textColor
            )
        }
    }
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
private fun ToastUi(text: String, @DrawableRes icon: Int) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(text) {
        isVisible = text.isNotBlank()
        delay(2000)
        isVisible = false
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
                painter = painterResource(icon), contentDescription = "toast icon"
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

@Composable
@Preview(showBackground = true)
fun LoginPreview() {
    LoginUi()
}