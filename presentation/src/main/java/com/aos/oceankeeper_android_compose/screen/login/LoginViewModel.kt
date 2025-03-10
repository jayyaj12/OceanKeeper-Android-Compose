package com.aos.oceankeeper_android_compose.screen.login

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.aos.core.util.NetworkUtils
import com.aos.oceankeeper_android_compose.base.BaseViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    // 카카오 로그인
    fun onClickedKakaoLogin() {
        if (NetworkUtils.isNetworkConnected(context)) {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Timber.e("error? ${error}")
                    baseEvent(Event.HideLoading)
                } else if (token != null) {
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            baseEvent(Event.ShowToast("카카오 로그인에 실패하였습니다."))
                            baseEvent(Event.HideLoading)
                        } else if (user != null) {
                            if (user.kakaoAccount != null) {

                            } else {
                                baseEvent(Event.ShowToast("카카오 로그인에 실패하였습니다."))
                                baseEvent(Event.HideLoading)
                            }
                        }
                    }
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                // 카카오톡으로 로그인
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    Timber.e("loginWithKakaoTalk error $error")
                    // token.accessToken 소셜 토큰

                    if (error != null) {
                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            Timber.e("error is ClientError error $error")
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(
                            context, callback = callback
                        )
                    } else {
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                baseEvent(Event.ShowToast("카카오 로그인에 실패하였습니다."))
                            } else if (user != null) {
                                if (token != null) {
                                    Timber.e("user $user")
                                    Timber.e("token $token")
                                    if (user.kakaoAccount != null) {
                                    } else {
                                        baseEvent(Event.ShowToast("카카오 로그인에 실패하였습니다."))
                                        baseEvent(Event.HideLoading)
                                    }
                                } else {
                                    baseEvent(Event.ShowToast("카카오 로그인에 실패하였습니다."))
                                    baseEvent(Event.HideLoading)
                                }
                            }
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    context, callback = callback
                )
            }
        } else {
            baseEvent(Event.ShowToast("네트워크 상태를 확인해주세요."))
        }
    }

}