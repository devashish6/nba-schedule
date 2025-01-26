package com.nba.schedule.ui

import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.google.gson.Gson
import com.nba.schedule.R
import com.nba.schedule.model.ScheduleResponse
import com.nba.schedule.model.ScheduleUi
import com.nba.schedule.model.UiModel
import com.nba.schedule.model.toScheduleUi
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

                    //Games Screen
                    1 -> {

                    }
                }

            }
        )
    }

}

@Composable
private fun SchedulesScreen(
    schedules: List<ScheduleUi>?,
) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        CalendarView()
        if (schedules != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                userScrollEnabled = true
            ) {
                items(schedules.size) {
                    MatchCard(
                        item = schedules[it],
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

    }
}

@Composable
fun CalendarView() {
    var currentDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier
            .background(Color(0xFF1F1D1B))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.up),
                contentDescription = "Up",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.clickable {
                    currentDate = currentDate.plusMonths(1)
                }
            )
            Text(
                text = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                fontSize = 18.sp,
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.down),
                contentDescription = "Up",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.clickable {
                    currentDate = currentDate.minusMonths(1)
                }
            )
        }
    }
}


@Composable
fun MatchCard(
    item: ScheduleUi,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF27292B))
            .padding(12.dp)
            .fillMaxWidth()
    ) {

        //Match info.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (item.isHomeMatch) stringResource(id = R.string.home) else stringResource(
                    id = R.string.away
                ),
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                text = "|",
                color = Color.DarkGray,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = item.gameDate,
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                text = "|",
                color = Color.DarkGray,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = item.startTime,
                color = Color.White,
                fontSize = 16.sp
            )
        }


        Spacer(modifier = Modifier.height(12.dp))

        //Home team and Away team

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (item.gameStatus == 1) {
                HorizontalTeamCard(
                    teamLogo = item.awayTeamUi.teamLogo,
                    teamName = item.awayTeamUi.teamDisplayName
                )
            } else {
                VerticalTeamCard(
                    teamLogo = item.awayTeamUi.teamLogo,
                    teamName = item.awayTeamUi.teamDisplayName
                )
            }

            if (item.awayTeamUi.score != "0") {
                Text(
                    text = item.awayTeamUi.score,
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Text(
                text = if (item.isHomeMatch) "VS" else "@",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            if (item.homeTeamUi.score != "0") {
                Text(
                    text = item.homeTeamUi.score,
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            if (item.gameStatus == 1) {
                HorizontalTeamCard(
                    teamLogo = item.homeTeamUi.teamLogo,
                    teamName = item.homeTeamUi.teamDisplayName
                )
            } else {
                VerticalTeamCard(
                    teamLogo = item.homeTeamUi.teamLogo,
                    teamName = item.homeTeamUi.teamDisplayName
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (item.isHomeMatch && item.gameStatus != 3) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                )
            ) {
                Text(
                    text = "BUY TICKETS ON ticketmaster",
                    color = Color.Black
                )

            }
        }

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
fun VerticalTeamCard(
    teamLogo: String,
    teamName: String,
) {
    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(teamLogo)
                .crossfade(true)
                .build(),
            contentDescription = "",
            imageLoader = LocalContext.current.imageLoader,
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            error = painterResource(id = R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .size(48.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = teamName,
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun HorizontalTeamCard(
    teamLogo: String,
    teamName: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(teamLogo)
                .crossfade(true)
                .build(),
            contentDescription = "",
            imageLoader = LocalContext.current.imageLoader,
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            error = painterResource(id = R.drawable.ic_launcher_foreground),
            modifier = Modifier
                .size(48.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = teamName,
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
private fun ConcatMapXMainSubscriber() {
    val context = LocalContext.current
    val localScheduleData =
        context.assets.open("schedule.json").bufferedReader().use { it.readText() }
    val finalSchedule = Gson().fromJson(localScheduleData, ScheduleResponse::class.java)
    val list = finalSchedule.data?.schedules?.map { it.toScheduleUi(emptyList()) }
    MainScreen(
        uiModel = UiModel(scheduleList = list)
    )
}