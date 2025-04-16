package com.aos.oceankeeper_android_compose.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.aos.domain.model.activity.home.Activity
import com.aos.domain.model.activity.home.ActivityItem
import com.aos.oceankeeper_android_compose.ui.theme.Pretendard
import com.letspl.oceankeeper.R

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HomeScreenUi(navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenUi(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val activityItems = viewModel.activityPager.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                TopAppBarUi(
                    onClickNotification = { /* TODO */ },
                    onClickSetting = { /* TODO */ },
                    modifier = modifier.fillMaxWidth()
                )
            }

            item {
                ScheduleUi(viewModel.activityScheduleList)
            }

            item {
                MenuUi(
                    modifier = Modifier.padding(top = 16.dp, start = 42.dp, end = 42.dp),
                    onClickNotice = { /* TODO */ },
                    onClickGuide = { /* TODO */ },
                    onClickTerms = { /* TODO */ }
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(colorResource(R.color.gray_200))
                        .padding(top = 14.dp)
                )
            }

            // ✅ Sticky Header - Tab 고정
            stickyHeader {
                Surface(
                    color = Color.White,
                    tonalElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f)
                ) {
                    TabUi(viewModel) { index ->
                        viewModel.selectedTabIndex = index
                        activityItems.refresh()
                    }
                }
            }

            item {
                FilterUi(
                    category = viewModel.category.value,
                    type = viewModel.type.value,
                    onClickCategory = {
                        viewModel.hideType()
                        viewModel.showCategory()
                    },
                    onClickType = {
                        viewModel.hideCategory()
                        viewModel.showType()
                    }
                )
            }

            item {
                if (activityItems.itemCount == 0) {
                    Spacer(modifier = Modifier.size(18.dp))
                } else {
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }

            item {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 2000.dp) // ✅ 높이 제한 필수
                        .padding(horizontal = 16.dp),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(0.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(count = activityItems.itemCount) { index ->
                        val item = activityItems[index]
                        if (item != null) {
                            ActivityItem(item = item)
                        }
                    }
                }
            }

            item {
                Column {
                    if (activityItems.itemCount == 0) {
                        EmptyActivitiesUi()
                    }
                }
            }
        }

        IconAddActivityUi {
            // 활동 추가 페이지로 이동
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = viewModel.isVisibleCategory.value,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            FilterItem(
                modifier = Modifier.align(Alignment.BottomCenter),
                title = "지역",
                selectIndex = viewModel.selectCategoryIndex.value,
                itemList = viewModel.categories,
                onClickItem = { viewModel.setCategoryIndex(it) },
                onClickConfirm = {
                    viewModel.setCategory(viewModel.selectCategoryIndex.value)
                    viewModel.hideCategory()
                    activityItems.refresh()
                },
                onClickClose = { viewModel.hideCategory() }
            )
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = viewModel.isVisibleType.value,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            FilterItem(
                modifier = Modifier.align(Alignment.BottomCenter),
                title = "종류",
                selectIndex = viewModel.selectTypeIndex.value,
                itemList = viewModel.types,
                onClickItem = { viewModel.setTypeIndex(it) },
                onClickConfirm = {
                    viewModel.setType(viewModel.selectTypeIndex.value)
                    viewModel.hideType()
                    activityItems.refresh()
                },
                onClickClose = { viewModel.hideType() }
            )
        }
    }
}

@Composable
fun TopAppBarUi(
    onClickNotification: () -> Unit,
    onClickSetting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.height(44.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier
                .size(108.dp, 16.dp)
                .padding(start = 16.dp),
            painter = painterResource(R.drawable.icon_logo_horizontal),
            contentDescription = "앱 로고"
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(36.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClickNotification()
                }, contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(16.dp, 19.dp),
                painter = painterResource(R.drawable.icon_notification),
                contentDescription = "알림"
            )
        }
        Box(
            modifier = Modifier
                .size(36.dp)
                .padding(end = 4.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClickSetting()
                }, contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(19.dp, 20.dp),
                painter = painterResource(R.drawable.icon_setting),
                contentDescription = "설정"
            )
        }
    }
}

@Composable
fun ScheduleUi(activityList: List<Activity>, modifier: Modifier = Modifier) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            text = "다가오는 일정",
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp
        )

        ScheduleViewPagerUi(
            activityList,
            modifier = Modifier.padding(top = 16.dp, start = (8.5).dp, end = (8.5).dp)
        )
    }
}

@Composable
fun ScheduleViewPagerUi(activityList: List<Activity>, modifier: Modifier = Modifier) {
    Box(modifier) {
        if (activityList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(color = colorResource(R.color.blue_gray_50)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "참여 예정인 활동이 없습니다.",
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = colorResource(R.color.blue_gray_600)
                )
            }
        } else {
            val pagerState = rememberPagerState(pageCount = { activityList.size })
            Column {
                Column(
                    modifier = Modifier
                        .border(
                            1.dp,
                            colorResource(R.color.blue_gray_100),
                            RoundedCornerShape(10.dp)
                        )
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    HorizontalPager(pagerState) { page ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(122.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 16.dp),
                                text = "D-${activityList[page].dday}",
                                fontFamily = Pretendard,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                color = colorResource(R.color.primary_500)
                            )
                            Text(
                                modifier = Modifier.padding(top = 6.dp),
                                text = activityList[page].title,
                                fontFamily = Pretendard,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                lineHeight = 22.sp,
                                color = colorResource(R.color.blue_gray_900),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(modifier = Modifier.padding(top = 6.dp)) {
                                Image(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(R.drawable.icon_calendar),
                                    contentDescription = "캘린더 아이콘"
                                )
                                Text(
                                    text = activityList[page].startDay,
                                    fontFamily = Pretendard,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = colorResource(R.color.blue_gray_200)
                                )
                            }
                            Row(modifier = Modifier.padding(top = 6.dp)) {
                                Image(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(R.drawable.icon_location),
                                    contentDescription = "위치 아이콘"
                                )
                                Text(
                                    text = activityList[page].location,
                                    fontFamily = Pretendard,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = colorResource(R.color.blue_gray_200),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp), horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { index ->
                        val color =
                            if (pagerState.currentPage == index) colorResource(R.color.gray_700) else colorResource(
                                R.color.gray_300
                            )
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuUi(
    modifier: Modifier = Modifier,
    onClickNotice: () -> Unit,
    onClickGuide: () -> Unit,
    onClickTerms: () -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(R.drawable.icon_notice),
            contentDescription = "공지사항 아이콘 버튼"
        )
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(R.drawable.icon_guide),
            contentDescription = "이용가이드 아이콘 버튼"
        )
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(R.drawable.icon_terms),
            contentDescription = "이용약관 아이콘 버튼"
        )
    }
}


@Composable
fun TabUi(viewModel: HomeViewModel, modifier: Modifier = Modifier, onClickTab: (Int) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        TabRow(selectedTabIndex = viewModel.selectedTabIndex,
            containerColor = Color.White,
            contentColor = Color("#545454".toColorInt()),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[viewModel.selectedTabIndex]),
                    color = Color("#545454".toColorInt())
                )
            },
            divider = {
                Divider(color = colorResource(R.color.gray_300), thickness = 1.dp)
            }) {

            viewModel.tabs.forEachIndexed { index, s ->
                Tab(
                    selected = viewModel.selectedTabIndex == index,
                    onClick = { onClickTab(index) },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    Text(
                        text = viewModel.tabs[index],
                        fontFamily = Pretendard,
                        fontWeight = if (viewModel.selectedTabIndex == index) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Medium
                        },
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        color = if (viewModel.selectedTabIndex == index) {
                            Color("#545454".toColorInt())
                        } else {
                            Color("#7A7A7A".toColorInt())
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterUi(
    category: String,
    type: String,
    modifier: Modifier = Modifier,
    onClickCategory: () -> Unit,
    onClickType: () -> Unit,
) {
    Row {
        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp)
                .height(32.dp)
                .border(1.dp, Color("#E6E6E6".toColorInt()), RoundedCornerShape(12.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClickCategory()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 7.dp, bottom = 7.dp),
                text = category,
                fontFamily = Pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = if (category == "지역") {
                    colorResource(R.color.gray_700)
                } else {
                    colorResource(R.color.primary_600)
                }
            )
            Image(
                painter = painterResource(R.drawable.icon_drop_down),
                contentDescription = "드롭다운 아이콘",
                modifier = Modifier
                    .padding(start = 9.dp, end = 12.dp)
                    .size(10.dp, 5.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 10.dp)
                .height(32.dp)
                .border(1.dp, Color("#E6E6E6".toColorInt()), RoundedCornerShape(12.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClickType()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 7.dp, bottom = 7.dp),
                text = type,
                fontFamily = Pretendard,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = if (type == "종류") {
                    colorResource(R.color.gray_700)
                } else {
                    colorResource(R.color.primary_600)
                }
            )
            Image(
                painter = painterResource(R.drawable.icon_drop_down),
                contentDescription = "드롭다운 아이콘",
                modifier = Modifier
                    .padding(start = 9.dp, end = 12.dp)
                    .size(10.dp, 5.dp)
            )
        }
    }
}

@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    title: String,
    selectIndex: Int,
    itemList: List<String>,
    onClickItem: (Int) -> Unit,
    onClickConfirm: () -> Unit,
    onClickClose: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onClickClose()
                    }
            ) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = title,
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.gray_900),
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
                Image(
                    painter = painterResource(R.drawable.icon_close),
                    contentDescription = "닫기 버튼",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp)
                        .align(Alignment.CenterEnd)
                )
            }

            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = colorResource(R.color.gray_300)
            )

            itemList.forEachIndexed { index, type ->
                BottomSheetItemUi(
                    selectIndex = selectIndex,
                    selectedValue = index + 1,
                    title = type,
                    onClickItem = {
                        onClickItem(it)
                    })
            }

            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = colorResource(R.color.gray_300)
            )

            Button(
                onClick = {
                    onClickConfirm()
                },
                modifier = Modifier
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(42.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.primary_500)
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "확인",
                    fontFamily = Pretendard,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ActivityItem(
    modifier: Modifier = Modifier,
    item: ActivityItem,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(117.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.activity_placeholder)
                        .placeholder(R.drawable.activity_placeholder)
                        .transformations(RoundedCornersTransformation(8f))
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .crossfade(true).build(),
                ), contentDescription = "활동 썸네일 이미지",
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color("#99131313".toColorInt())),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.size(4.dp))
                    Image(
                        painter = painterResource(R.drawable.icon_person),
                        modifier = Modifier.size(14.dp),
                        contentDescription = "인원 아이콘"
                    )
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                        text = "${item.participants}/${item.quota}명",
                        color = Color.White,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontFamily = Pretendard,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                }
            }
        }

        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = item.hostNickname,
            fontSize = 12.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.blue_gray_600),
            lineHeight = 18.sp
        )
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = item.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(R.color.gray_900),
            fontSize = 14.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            lineHeight = 20.sp
        )

        Row {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.icon_location_home),
                contentDescription = "위치 아이콘"
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = item.location,
                color = colorResource(R.color.blue_gray_600),
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontFamily = Pretendard,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Row {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.icon_calendar_home),
                contentDescription = "캘린더 아이콘"
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "${item.recruitStartAt}~${item.recruitEndAt}",
                color = colorResource(R.color.blue_gray_600),
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontFamily = Pretendard,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Row {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.icon_start_time),
                contentDescription = "일정 아이콘"
            )
            Spacer(modifier = Modifier.size(2.dp))
            Text(
                text = "${item.startAt}",
                color = colorResource(R.color.blue_gray_600),
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontFamily = Pretendard,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun EmptyActivitiesUi(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
            .background(colorResource(R.color.blue_gray_50)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(80.dp))

        Text(
            text = "조건에 맞는 프로젝트가 없습니다.",
            fontSize = 14.sp,
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            lineHeight = 20.sp,
            color = colorResource(R.color.blue_gray_500)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Image(
            painter = painterResource(R.drawable.icon_empty_activity),
            contentDescription = "활동 비어있음 아이콘"
        )
    }
}

@Composable
fun BottomSheetItemUi(
    selectIndex: Int,
    selectedValue: Int,
    title: String,
    onClickItem: (Int) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 20.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClickItem(selectedValue)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontFamily = Pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = if (selectIndex == selectedValue) {
                colorResource(R.color.primary_500)
            } else {
                colorResource(R.color.gray_500)
            }
        )
        Image(
            painter = if (selectIndex == selectedValue) {
                painterResource(R.drawable.icon_category_checked)
            } else {
                painterResource(R.drawable.icon_category_not_checked)
            }, contentDescription = "체크 표시 아이콘"
        )
    }
}

@Composable
fun IconAddActivityUi(modifier: Modifier = Modifier, onClickBtn: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Image(
            painter = painterResource(id = R.drawable.icon_add_activity_btn),
            contentDescription = "활동 추가 버튼",
            modifier = Modifier
                .size(56.dp)
                .padding(end = 20.dp, bottom = 20.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null
                ) {
                    onClickBtn()
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
//    CategoryUi()
//    ScheduleViewPagerUi(
//        listOf(
//            Activity(
//                1,
//                "2",
//                "제주도 금능ㄴ해면sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss",
//                "2023.03.20",
//                "금능해변 플로깅 프로젝트"
//            ),
//            Activity(1, "2", "제주도 금능ㄴ해면", "2023.03.20", "금능해변 플로깅 프로젝트2"),
//            Activity(1, "2", "제주도 금능ㄴ해면", "2023.03.20", "금능해변 플로깅 프로젝트3")
//        )
//    )
}
