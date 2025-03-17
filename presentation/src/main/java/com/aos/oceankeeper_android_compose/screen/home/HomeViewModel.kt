package com.aos.oceankeeper_android_compose.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aos.core.util.UserInfoUtil
import com.aos.domain.model.activity.home.Activity
import com.aos.domain.model.activity.home.ActivityScheduleModel
import com.aos.domain.usecase.activity.GetActivityScheduleUseCase
import com.aos.oceankeeper_android_compose.base.LoadingHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    private val getActivityScheduleUseCase: GetActivityScheduleUseCase
): ViewModel() {

    private var _activityScheduleList = mutableStateListOf<Activity>()
    val activityScheduleList: SnapshotStateList<Activity> = _activityScheduleList

    val tabs = listOf("전체", "진행예정 활동", "종료된 활동")
    var selectedTabIndex by mutableStateOf(0)

    // 카테고리
    private var _category = mutableStateOf("지역")
    val category: State<String> = _category
    private var _categoryIndex = mutableStateOf(0)
    val categoryIndex: State<Int> = _categoryIndex

    val categories = listOf("서해번쩍", "동해번쩍", "남해번쩍", "제주번쩍", "기타")

    private var _selectCategoryIndex = mutableStateOf(0)
    val selectCategoryIndex: State<Int> = _selectCategoryIndex

    private var _isVisibleCategory = mutableStateOf(false)
    val isVisibleCategory: State<Boolean> = _isVisibleCategory

    init {
        getActivitySchedule()
    }

    // 다가오는 일정 조회
    private fun getActivitySchedule() {
        viewModelScope.launch(Dispatchers.IO) {
            LoadingHandler.show()
            getActivityScheduleUseCase(UserInfoUtil.getUserInfo().userId).onSuccess {
                Timber.e("it $it")
                _activityScheduleList.clear()
                _activityScheduleList.addAll(it.activities) // ✅ 리스트 자체를 바꾸지 않고 기존 리스트에 추가
                LoadingHandler.hide()
            }.onFailure {
                LoadingHandler.hide()
            }
        }
    }
    fun setCategory(index: Int) {
        _categoryIndex.value = index
        _category.value = categories[index - 1]
    }

    fun setCategoryIndex(index: Int) {
        _selectCategoryIndex.value = index
    }

    fun showCategory() {
        _isVisibleCategory.value = true
    }

    fun hideCategory() {
        _isVisibleCategory.value = false
    }

    fun revertCategoryIndex() {
        _selectCategoryIndex.value = categoryIndex.value
    }

}