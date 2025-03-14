package com.aos.oceankeeper_android_compose.screen.login

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aos.oceankeeper_android_compose.ui.Screen
import com.aos.oceankeeper_android_compose.ui.theme.Pretendard
import com.google.firebase.FirebaseApp
import com.letspl.oceankeeper.R
import timber.log.Timber

@Composable
fun LoginScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LoginUi(navController)
    }
}

@Composable
fun LoginUi(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return // ✅ Activity Context 변환

    val navState = viewModel.loginNavigation.value

    LaunchedEffect(navState) {
        when(navState) {
            // 회원가입으로 이동
            LoginNavigation.SIGNUP -> {
                Timber.e("SIGNUP")
                navController.navigate(Screen.SignUpInputScreen.route)
            }
            // 홈화면으로 이동
            LoginNavigation.HOME -> {
                navController.navigate(Screen.SignUpInputScreen.route) {
                    popUpTo(Screen.LoginScreen.route) { inclusive = true }
                }
            }
            else -> {}
        }

        viewModel.resetNavigationState()
    }

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
                viewModel.onClickNaverLogin(activity)
            }
            Spacer(modifier = Modifier.size(12.dp))
            LoginBtnUi(
                icon = R.drawable.kakao_symbol,
                text = "카카오톡으로 계속하기",
                textColor = Color("#181600".toColorInt()),
                backgroundColor = Color("#FAE100".toColorInt())
            ) {
                // 카카오톡으로 계속하기 클릭
                viewModel.onClickedKakaoLogin(activity)
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
@Preview(showBackground = true)
fun LoginPreview() {
    LoginUi(rememberNavController())
}