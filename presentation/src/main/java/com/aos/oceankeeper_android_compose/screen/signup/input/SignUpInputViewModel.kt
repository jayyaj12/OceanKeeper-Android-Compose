package com.aos.oceankeeper_android_compose.screen.signup.input

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.core.util.FileUtil
import com.aos.core.util.UserInfoUtil
import com.aos.oceankeeper_android_compose.base.ToastHandler
import com.aos.oceankeeper_android_compose.base.ToastType
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
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class SignUpInputViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    var nickname by mutableStateOf("")
    var lineColor by mutableStateOf(R.color.blue_gray_100)
    var isVisibleWarning by mutableStateOf(false)
    var isVisibleProfileDialog by mutableStateOf(false)

    var profileFile: File? = null

    // 이미지
    private var _selectImageUri = mutableStateOf<String>(UserInfoUtil.getUserInfo().profileImgUrl)
    val selectImageUri: State<String> = _selectImageUri

    fun onClickCompleteBtn() {
        saveUriStringToFile()
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

    private fun saveUriStringToFile() {
        viewModelScope.launch {
            var file: File?
            withContext(Dispatchers.IO) {
                file = FileUtil.handleImageUri(context, Uri.parse(selectImageUri.value))
            }

            if (file != null) {
                Timber.d("변환된 파일 경로: ${file!!.absolutePath}")
                profileFile = file
            } else {
                Timber.e("파일 변환 실패")
                ToastHandler.show("프로필 사진 불러오기가 실패하였습니다", ToastType.ERROR)
            }
        }
    }


}