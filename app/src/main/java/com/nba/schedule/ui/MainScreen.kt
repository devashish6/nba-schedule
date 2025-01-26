package com.nba.schedule.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.nba.schedule.R
import com.nba.schedule.model.ScheduleResponse
import com.nba.schedule.model.UiModel
import com.nba.schedule.model.toScheduleUi
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
private fun MainScreenRoute(
) {


}

@Composable
fun MainScreen(
    uiModel: UiModel
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



@Preview
@Composable
private fun MainScreenPreview() {
    val context = LocalContext.current
    val localScheduleData =
        context.assets.open("schedule.json").bufferedReader().use { it.readText() }
    val finalSchedule = Gson().fromJson(localScheduleData, ScheduleResponse::class.java)
    val list = finalSchedule.data?.schedules?.map { it.toScheduleUi(emptyList()) }
    MainScreen(
        uiModel = UiModel(scheduleList = list)
    )
}