package com.aos.oceankeeper_android_compose.screen.login

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.core.util.JsonUtil
import com.aos.core.util.NetworkUtil
import com.aos.domain.usecase.PostLoginUseCase
import com.aos.oceankeeper_android_compose.base.LoadingHandler
import com.aos.oceankeeper_android_compose.base.ToastHandler
import com.aos.oceankeeper_android_compose.base.ToastType
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
) : ViewModel() {

    // 파베 디바이스 토큰
    private var deviceToken = ""

    private var _loginNavigation = mutableStateOf(LoginNavigation.DEFAULT)
    val loginNavigation: State<LoginNavigation> = _loginNavigation

    init {
        getRegisterFcmToken()
    }

    // 서버 로그인
    private fun postLogin(provider: String, providerId: String) {
        viewModelScope.launch {
            postLoginUseCase(deviceToken, provider, providerId).onSuccess {
                LoadingHandler.hide()
                _loginNavigation.value = LoginNavigation.HOME
            }.onFailure {
                LoadingHandler.hide()
                if(JsonUtil.extractErrorDetail(it.message).equals("provider id와 일치하는 회원이 없습니다. 회원가입을 진행해주세요.")){
                    _loginNavigation.value = LoginNavigation.SIGNUP
                } else {
                    ToastHandler.show(JsonUtil.extractErrorDetail(it.message), ToastType.ERROR)
                }
            }
        }
    }

    // 네이버 로그인
    fun onClickNaverLogin(activity: Activity) {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                NidOAuthLogin().getProfileMap(object : NidProfileCallback<NidProfileMap> {
                    override fun onSuccess(result: NidProfileMap) {
                        Timber.e("result $result")
                        postLogin(
                            provider = "naver",
                            providerId = result.profile?.get("id") as String
                        )
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                        val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                        Timber.e("errorCode $errorCode, errorDescription $errorDescription")
                        ToastHandler.show(text = errorDescription ?: "", toastType = ToastType.ERROR)
                    }

                    override fun onError(errorCode: Int, message: String) {
                        onFailure(errorCode, message)
                    }
                })
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Timber.e("errorCode $errorCode, errorDescription $errorDescription")
                ToastHandler.show(text = errorDescription ?: "", toastType = ToastType.ERROR)
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }


        NaverIdLoginSDK.authenticate(activity, oauthLoginCallback)
    }

    // 카카오 로그인
    fun onClickedKakaoLogin(activity: Activity) {
        LoadingHandler.show()
        if (NetworkUtil.isNetworkConnected(activity)) {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Timber.e("error? ${error}")
                    LoadingHandler.hide()
                } else if (token != null) {
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            LoadingHandler.hide()
                        } else if (user != null) {
                            if (user.kakaoAccount != null) {
                                // 정보 가져오기
                                postLogin(
                                    provider = "kakao",
                                    providerId = user.id.toString()
                                )
                            } else {
                                LoadingHandler.hide()
                            }
                        }
                    }
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
                // 카카오톡으로 로그인
                UserApiClient.instance.loginWithKakaoTalk(activity) { token, error ->
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
                            activity, callback = callback
                        )
                    } else {
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                //에러가 있을 경우
                            } else if (user != null) {
                                if (token != null) {
                                    Timber.e("user $user")
                                    Timber.e("token $token")
                                    if (user.kakaoAccount != null) {
                                        // 정보 가져오기
                                        LoadingHandler.hide()
                                        postLogin(
                                            provider = "kakao",
                                            providerId = user.id.toString()
                                        )
                                    } else {
                                        LoadingHandler.hide()
                                    }
                                } else {
                                    LoadingHandler.hide()
                                }
                            }
                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    activity, callback = callback, prompts = listOf(Prompt.SELECT_ACCOUNT)
                )
            }
        } else {
            viewModelScope.launch {
                ToastHandler.show(text = "네트워크 상태를 확인해주세요", toastType = ToastType.ERROR)

                delay(2000)
                LoadingHandler.hide()
            }
        }
    }

    // firebase fcm token 가져오기 및 저장
    private fun getRegisterFcmToken(){
        // 등록된 토큰 가져오기
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            Timber.e("register Token ${task.result}")

            deviceToken = task.result
        })
    }

    fun resetNavigationState() {
        _loginNavigation.value = LoginNavigation.DEFAULT
    }
}