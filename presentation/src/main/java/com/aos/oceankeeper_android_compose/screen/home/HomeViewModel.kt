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

    private var _activityScheduleList = mutableStateListOf<Activity>(
            Activity(
                1,
                "2",
                "제주도 금능ㄴ해면sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss",
                "2023.03.20",
                "금능해변 플로깅 프로젝트"
            ),
            Activity(1, "2", "제주도 금능ㄴ해면", "2023.03.20", "금능해변 플로깅 프로젝트2"),
            Activity(1, "2", "제주도 금능ㄴ해면", "2023.03.20", "금능해변 플로깅 프로젝트3"))
    val activityScheduleList: SnapshotStateList<Activity> = _activityScheduleList
    // 탭
    val tabs = listOf("전체", "진행예정 활동", "종료된 활동")
    var selectedTabIndex by mutableStateOf(0)
    // 카테고리
    var category = mutableStateOf("지역")
    val categories = listOf("서해번쩍", "동해번쩍", "남해번쩍", "제주번쩍", "기타")
    private var selectCategoryIndex = mutableStateOf(0)
    var isVisibleCategory = mutableStateOf( false )

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

    fun selectCategory(index: Int): String {
        category.value = categories[index]
        return category.value
    }

    fun getCategoryTitle(index: Int): String {
        return categories[index]
    }

    fun getCategory(): String {
        return category.value
    }

    fun getSelectCategoryIndex(): Int {
        return selectCategoryIndex.value
    }

    fun setSelectCategoryIndex(index: Int) {
        selectCategoryIndex.value = index
    }

    fun showCategory() {
        isVisibleCategory.value = true
    }

    fun hideCategory() {
        isVisibleCategory.value = false
    }

    fun isVisibleCategory(): Boolean {
        return isVisibleCategory.value
    }

}