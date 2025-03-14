package com.aos.oceankeeper_android_compose.screen.signup.complete

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpCompleteViewModel @Inject constructor(): ViewModel() {

    var signUpCompleteNavigation by mutableStateOf(false)

    fun onClickCompleteBtn() {
        signUpCompleteNavigation = true
    }

}