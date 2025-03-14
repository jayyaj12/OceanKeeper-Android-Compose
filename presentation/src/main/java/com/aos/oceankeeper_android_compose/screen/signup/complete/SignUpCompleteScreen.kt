package com.aos.oceankeeper_android_compose.screen.signup.complete

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aos.oceankeeper_android_compose.ui.Screen
import com.aos.oceankeeper_android_compose.ui.theme.Pretendard
import com.letspl.oceankeeper.R

@Composable
fun SignUpCompleteScreen(navController: NavController) {
    Box(modifier = Modifier.background(Color.White)) {
        SignUpCompleteUi(navController)
    }
}

@Composable
fun SignUpCompleteUi(navController: NavController, viewModel: SignUpCompleteViewModel = hiltViewModel()) {
    val navState = viewModel.signUpCompleteNavigation
    LaunchedEffect(navState) {
        if(navState) {
            navController.navigate(
                Screen.HomeScreen.route
            ) {
                popUpTo(Screen.SignUpCompleteScreen.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.padding(top = 75.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "반갑습니다.\n오션키퍼님 함께 바다를\n깨끗하게 만들어요!",
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = colorResource(R.color.gray_900),
            lineHeight = 30.sp
        )
        Image(
            modifier = Modifier.padding(top = 24.dp),
            painter = painterResource(R.drawable.icon_sign_up_whale),
            contentDescription = "고래 이미지"
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary_500)
            ),
            shape = RoundedCornerShape(30.dp),
            onClick = {
                viewModel.onClickCompleteBtn()
            }) {
            Text(
                text = "확인",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Pretendard,
                lineHeight = 28.sp,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpCompleteUiPreView() {
    Box(modifier = Modifier.background(Color.White)) {
        SignUpCompleteUi(rememberNavController())
    }
}