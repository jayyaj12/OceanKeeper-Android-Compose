package com.aos.oceankeeper_android_compose.screen.signup.input

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aos.core.util.FileUtil
import com.aos.core.util.UserInfoUtil
import com.aos.domain.usecase.user.signup.PostAuthSignUpUseCase
import com.aos.domain.usecase.user.signup.PostImageProfileUseCase
import com.aos.oceankeeper_android_compose.base.LoadingHandler
import com.aos.oceankeeper_android_compose.base.ToastHandler
import com.aos.oceankeeper_android_compose.base.ToastType
import com.aos.oceankeeper_android_compose.screen.login.LoginNavigation
import com.letspl.oceankeeper.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignUpInputViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postImageProfileUseCase: PostImageProfileUseCase,
    private val postAuthSignUpUseCase: PostAuthSignUpUseCase,
) : ViewModel() {

    var nickname by mutableStateOf("")
    var lineColor by mutableStateOf(R.color.blue_gray_100)
    var isVisibleWarning by mutableStateOf(false)
    var isVisibleProfileDialog by mutableStateOf(false)

    var profileFile: File? = null

    // 이미지
    private var _selectImageUri = mutableStateOf<String>(UserInfoUtil.getUserInfo().profileImgUrl)
    val selectImageUri: State<String> = _selectImageUri

    // 스크린 이동
    var signUpNavigation by mutableStateOf(false)


    // 회원가입 완료 버튼 클릭
    fun onClickCompleteBtn() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                saveUriStringToFile()
            }

            if (profileFile != null) {
                LoadingHandler.show()
                postImageProfileUseCase(profileFile!!).onSuccess {
                    postAuthSignUp(it.url)
                }.onFailure {
                    ToastHandler.show(it.message.toString(), ToastType.ERROR)
                    LoadingHandler.hide()
                }
            } else {
                ToastHandler.show(text = "프로필 사진을 불러오는 실패하였습니다.\n프로필 사진을 다시 등록해주세요.", toastType = ToastType.ERROR)
            }
        }
    }

    // 회원가입 호출
    private fun postAuthSignUp(profileUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfo = UserInfoUtil.getUserInfo()
            postAuthSignUpUseCase(
                deviceToken = userInfo.deviceToken,
                email = userInfo.email,
                nickname = userInfo.nickname,
                profile = profileUrl,
                provider = userInfo.provider,
                providerId = userInfo.providerId,
            ).onSuccess {
                UserInfoUtil.setUserProfile(it.profile)
                signUpNavigation = true
                LoadingHandler.hide()
            }.onFailure {
                LoadingHandler.hide()
            }
        }
    }

    fun onNickValueChanged(text: String) {
        if (text.length <= 8) {
            nickname = text
            isVisibleWarning = false
            lineColor = R.color.blue_gray_100
        } else {
            isVisibleWarning = true
            lineColor = R.color.worrying
        }
    }

    fun updateSelectedImage(uri: Uri?) {
        Timber.e("uri $uri")
        uri?.let {
            _selectImageUri.value = it.toString()
        }
    }

    fun showProfileDialog() {
        isVisibleProfileDialog = true
    }

    fun hideProfileDialog() {
        isVisibleProfileDialog = false
    }

    fun saveBitmapToUri(bitmap: Bitmap) {
        val filesDir = context.cacheDir
        val imageFile = File(filesDir, "captured_image_${System.currentTimeMillis()}.jpg")

        try {
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            val uri = FileProvider.getUriForFile(
                context, "${context.packageName}.fileprovider", imageFile
            )
            _selectImageUri.value = uri.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            _selectImageUri.value = ""
        }
    }

    private suspend fun saveUriStringToFile() {
        withContext(Dispatchers.IO) {
            var file: File? = FileUtil.handleImageUri(context, Uri.parse(selectImageUri.value))

            if (file != null) {
                Timber.d("변환된 파일 경로: ${file!!.absolutePath}")
                profileFile = file
                Timber.e("profile $profileFile")
            } else {
                Timber.e("파일 변환 실패")
                ToastHandler.show("프로필 사진 불러오기가 실패하였습니다", ToastType.ERROR)
            }
        }
    }


}