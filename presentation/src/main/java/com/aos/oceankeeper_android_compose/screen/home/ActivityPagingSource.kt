package com.aos.oceankeeper_android_compose.screen.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aos.domain.model.activity.home.ActivityItem
import com.aos.domain.usecase.activity.GetActivityUseCase
import javax.inject.Inject

class ActivityPagingSource(private val getActivityUseCase: GetActivityUseCase) : PagingSource<Int, ActivityItem>() {
    private var activityId: String? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ActivityItem> {
        val page = params.key ?: 1

        return try {
            val result = getActivityUseCase(
                activityId = activityId,
                garbageCategory = null,
                locationTag = null,
                size = 5,
                status = null
            )

            result.fold(
                onSuccess = {
                    activityId = it.activityList.lastOrNull()?.activityId
                    val data = it.activityList
                    val isLast = it.isLast
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (isLast) null else page + 1
                    )
                },
                onFailure = { LoadResult.Error(it) }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ActivityItem>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}