package com.aos.oceankeeper_android_compose.screen.signup.input

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aos.core.util.UserInfoUtil
import com.aos.oceankeeper_android_compose.custom_ui.CustomTextField
import com.aos.oceankeeper_android_compose.ui.theme.OceanKeeperAndroidComposeTheme
import com.aos.oceankeeper_android_compose.ui.theme.Pretendard
import com.letspl.oceankeeper.R

@Composable
fun SingUpInputScreen(navController: NavController) {
    OceanKeeperAndroidComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            SingUpUi(navController)
        }
    }
}

@Composable
fun SingUpUi(navController: NavController, viewModel: SignUpInputViewModel = hiltViewModel()) {
    Column {
        topBarUi(onClickBackBtn = {
            navController.popBackStack()
        })
        InputUi(
            viewModel = viewModel,
            onClickBtn = {
                viewModel.onClickCompleteBtn()
            }, onNickValueChanged = { text ->
                viewModel.onNickValueChanged(text)
            })
    }
}

@Composable
fun topBarUi(onClickBackBtn: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icon_back),
            contentDescription = "뒤로가기",
            modifier = Modifier
                .size(32.dp)
                .padding(start = 12.dp)
                .align(Alignment.CenterStart)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClickBackBtn()
                }
        )

        Text(
            text = "기본 정보 입력",
            fontFamily = Pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = colorResource(R.color.gray_900),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun InputUi(
    viewModel: SignUpInputViewModel,
    onClickBtn: () -> Unit,
    onNickValueChanged: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 20.dp)
    ) {
        Text(
            text = "오션키퍼님의\n기본정보를 입력해주세요.",
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.gray_900),
            fontSize = 20.sp,
            lineHeight = 28.sp
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                modifier = Modifier
                    .size(112.dp)
                    .clip(RoundedCornerShape(44.dp)),
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(UserInfoUtil.getUserInfo().profileImgUrl)
                        .placeholder(R.drawable.default_profile)
                        .crossfade(true).build()
                ),
                contentDescription = "프로필 이미지 설정",
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(R.drawable.icon_add_profile),
                contentDescription = "프로필 추가 버튼"
            )
        }

        Text(
            text = "닉네임",
            fontSize = 14.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.gray_900),
            lineHeight = 20.sp
        )

        Box(modifier = Modifier.height(44.dp)) {
            CustomTextField(
                value = viewModel.nickname.value,
                lineColor = viewModel.lineColor.value,
                onValueChange = { text ->
                    onNickValueChanged(text)
                }
            )

            this@Column.AnimatedVisibility(
                viewModel.isVisibleWarning.value, enter = fadeIn(tween(500)), exit = fadeOut(
                    tween(500)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.icon_worning),
                        contentDescription = "텍스트가 8자 넘으면 경고"
                    )
                }
            }
        }

        AnimatedVisibility(
            viewModel.isVisibleWarning.value, enter = fadeIn(tween(500)), exit = fadeOut(
                tween(500)
            )
        ) {
            Text(
                text = "공백을 포함한 최대 8자까지 입력 가능합니다.",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = Pretendard,
                fontWeight = FontWeight.Medium,
                color = colorResource(R.color.worrying)
            )
        }

        Text(
            text = "• 프로필 사진 및 닉네임은 수정 가능합니다\n• 수정하지 않을 경우 SNS 계정 정보로 자동 등록됩니다. ",
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.blue_gray_400),
            modifier = Modifier.padding(top = 24.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary_500)
            ),
            onClick = {
                onClickBtn()
            }) {
            Text(
                text = "회원가입 완료", fontFamily = Pretendard, fontWeight = FontWeight.Bold, fontSize = 16.sp, lineHeight = 28.sp, color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpInputPreView() {
    OceanKeeperAndroidComposeTheme {
        Box {
            SingUpUi(rememberNavController())
        }
    }
}