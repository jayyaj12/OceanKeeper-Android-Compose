package com.aos.oceankeeper_android_compose.screen.home

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.aos.core.util.UserInfoUtil
import com.aos.domain.model.activity.home.Activity
import com.aos.domain.usecase.activity.GetActivityScheduleUseCase
import com.aos.domain.usecase.activity.GetActivityUseCase
import com.aos.oceankeeper_android_compose.base.LoadingHandler
import com.aos.oceankeeper_android_compose.base.ToastHandler
import com.aos.oceankeeper_android_compose.base.ToastType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    private val getActivityScheduleUseCase: GetActivityScheduleUseCase,
    private val getActivityUseCase: GetActivityUseCase,
) : ViewModel() {

    private var _activityScheduleList = mutableStateListOf<Activity>()
    val activityScheduleList: SnapshotStateList<Activity> = _activityScheduleList

    val tabs = listOf("전체", "진행예정 활동", "종료된 활동")
    var selectedTabIndex by mutableStateOf(0)

    // 활동
    val activityPager = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            ActivityPagingSource(
                getActivityUseCase = getActivityUseCase
            )
        }
    ).flow.cachedIn(viewModelScope)

    // 카테고리
    val categories = listOf("서해번쩍", "동해번쩍", "남해번쩍", "제주번쩍", "기타")
    private var _category = mutableStateOf("지역")
    val category: State<String> = _category
    private var _categoryIndex = mutableStateOf(0)
    val categoryIndex: State<Int> = _categoryIndex
    private var _selectCategoryIndex = mutableStateOf(0)
    val selectCategoryIndex: State<Int> = _selectCategoryIndex
    private var _isVisibleCategory = mutableStateOf(false)
    val isVisibleCategory: State<Boolean> = _isVisibleCategory

    // 종류
    val types = listOf("연안쓰레기", "부유쓰레기", "침적쓰레기", "기타")
    private var _type = mutableStateOf("종류")
    val type: State<String> = _type
    private var _typeIndex = mutableStateOf(0)
    val typeIndex: State<Int> = _typeIndex
    private var _selectTypeIndex = mutableStateOf(0)
    val selectTypeIndex: State<Int> = _selectTypeIndex
    private var _isVisibleType = mutableStateOf(false)
    val isVisibleType: State<Boolean> = _isVisibleType

    init {
        getActivitySchedule()
//        getActivities()
    }

    // 다가오는 일정 조회
    private fun getActivitySchedule() {
        viewModelScope.launch(Dispatchers.IO) {
            LoadingHandler.show()
            getActivityScheduleUseCase(UserInfoUtil.getUserInfo().userId).onSuccess {
                _activityScheduleList.clear()
                _activityScheduleList.addAll(it.activities) // ✅ 리스트 자체를 바꾸지 않고 기존 리스트에 추가
                LoadingHandler.hide()
            }.onFailure {
                LoadingHandler.hide()
                ToastHandler.show(it.message.toString(), ToastType.ERROR)
            }
        }
    }

    // 활동 목록 조회
    private fun getActivities() {
        viewModelScope.launch(Dispatchers.IO) {
            getActivityUseCase(
                activityId = null,
                garbageCategory = null,
                locationTag = null,
                size = 5,
                status = null
            ).onSuccess {
                Timber.e("it $it")
                LoadingHandler.hide()
            }.onFailure {
                LoadingHandler.hide()
                ToastHandler.show(it.message.toString(), ToastType.ERROR)
            }
        }
    }

    fun setCategory(index: Int) {
        if (index == 0) return
        _categoryIndex.value = index
        _category.value = categories[index - 1]
    }

    fun setCategoryIndex(index: Int) {
        _selectCategoryIndex.value = index
    }

    fun setType(index: Int) {
        if (index == 0) return
        _typeIndex.value = index
        _type.value = types[index - 1]
    }

    fun setTypeIndex(index: Int) {
        _selectTypeIndex.value = index
    }

    fun showCategory() {
        _isVisibleCategory.value = true
    }

    fun hideCategory() {
        _isVisibleCategory.value = false
        Handler(Looper.getMainLooper()).postDelayed({
            revertCategoryIndex()
        }, 300)
    }

    fun showType() {
        _isVisibleType.value = true
    }

    fun hideType() {
        _isVisibleType.value = false
        Handler(Looper.getMainLooper()).postDelayed({
            revertTypeIndex()
        }, 300)
    }

    private fun revertCategoryIndex() {
        _selectCategoryIndex.value = categoryIndex.value
    }

    private fun revertTypeIndex() {
        _selectTypeIndex.value = typeIndex.value
    }

}