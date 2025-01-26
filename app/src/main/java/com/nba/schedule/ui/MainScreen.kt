package com.nba.schedule.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.gson.Gson
import com.nba.schedule.R
import com.nba.schedule.model.ScheduleResponse
import com.nba.schedule.model.UiModel
import com.nba.schedule.model.toScheduleUi
import com.nba.schedule.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
1. H -> Team 1 -> Home team right
2. V -> Team 2 -> Visitor team -> left
3. S -> Score (inside H and V obj)
4. st -> 1,2,3 (Next, Live and Past)
5. year -> year
6. gametime -> game time
7. buy_ticket_url -> if null hide else show button
 **/

@Composable
fun MainScreenRoute(
    viewModel: MainViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadJsonData()
    }

    val uiModel by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreen(
        uiModel = uiModel,
        onSearch = {
            viewModel.search(it)
        }
    )
}

@Composable
private fun MainScreen(
    uiModel: UiModel,
    onSearch: (String) -> Unit
) {
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    val tabNames =
        listOf(stringResource(id = R.string.schedules), stringResource(id = R.string.games))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ToolBar()
        CustomSearchBar(
            onSearch = onSearch
        )
        Spacer(modifier = Modifier.height(4.dp))
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Black,
            contentColor = Color.White,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color.Red
                )
            },
            divider = {
                HorizontalDivider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = Color.DarkGray
                )
            },
            tabs = {
                tabNames.forEachIndexed { index, title ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .clickable {
                                scope.launch {
                                    pagerState.scrollToPage(index)
                                }
                            }
                    ) {
                        Text(
                            text = title,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        HorizontalPager(
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(0.dp),
            pageSize = PageSize.Fill,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            key = null,
            pageContent = { page ->
                when (page) {
                    //Schedules Screen
                    0 -> {
                        SchedulesScreen(
                            schedules = uiModel.scheduleList
                        )
                    }

                    //Teams Screen
                    1 -> {
                        TeamsScreen(
                            teams = uiModel.teamsList
                        )

                    }
                }

            }
        )
    }

}

@Composable
fun ToolBar() {
    Text(
        text = "TEAM",
        fontSize = 24.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun CustomSearchBar(onSearch: (String) -> Unit) {
    val DEFAULT_SEARCH = "Search by arena, team or city"
    var searchText by remember { mutableStateOf("") }
    var isHintDisplayed by remember {
        mutableStateOf(true)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 12.dp, bottom = 12.dp, end = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 18.dp, bottom = 18.dp, end = 16.dp)
            ) {
                BasicTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        isHintDisplayed = searchText.isEmpty()
                        onSearch(it)
                    },
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                if (isHintDisplayed) {
                    Text(text = DEFAULT_SEARCH, color = Color.Black)
                }
            }
        }

    }
}


@Preview
@Composable
private fun MainScreenPreview() {
    val context = LocalContext.current
    val localScheduleData =
        context.assets.open("schedule.json").bufferedReader().use { it.readText() }
    val finalSchedule = Gson().fromJson(localScheduleData, ScheduleResponse::class.java)
    val list = finalSchedule.data?.schedules?.map { it.toScheduleUi(emptyList()) }
    MainScreen(
        uiModel = UiModel(scheduleList = list),
        onSearch = { }
    )
}