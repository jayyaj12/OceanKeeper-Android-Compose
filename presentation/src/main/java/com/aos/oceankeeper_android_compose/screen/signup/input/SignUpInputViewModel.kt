package com.aos.oceankeeper_android_compose.screen.signup.input

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.letspl.oceankeeper.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpInputViewModel @Inject constructor(): ViewModel() {

    var nickname = mutableStateOf("")
    var lineColor = mutableStateOf(R.color.blue_gray_100)
    var isVisibleWarning = mutableStateOf(false)

    fun onClickCompleteBtn() {

    }

    fun onNickValueChanged(text: String) {
        if (text.length <= 8) {
            nickname.value = text
            isVisibleWarning.value = false
            lineColor.value = R.color.blue_gray_100
        } else {
            isVisibleWarning.value = true
            lineColor.value = R.color.worrying
        }
    }

}