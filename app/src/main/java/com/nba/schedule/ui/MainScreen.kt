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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.imageLoader
import com.nba.schedule.R
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
private fun MainScreenRoute() {


}

@Composable
private fun MainScreen() {
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    val tabNames = listOf("Schedules", "Games")
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
                        SchedulesScreen()
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
private fun SchedulesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        CalendarView()
        Spacer(modifier = Modifier.height(10.dp))
        MatchCard()
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
            IconButton(onClick = { currentDate = currentDate.minusMonths(1) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Previous Month",
                    tint = Color.White
                )
            }
            Text(
                text = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                fontSize = 18.sp,
                color = Color.White
            )
            IconButton(onClick = { currentDate = currentDate.plusMonths(1) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Next Month",
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
fun MatchCard(

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
                text = "AWAY",
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
                text = "SAT JUL 01",
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
                text = "FINAL",
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
            Column {
                AsyncImage(
                    model = "Image Url",
                    contentDescription = "",
                    imageLoader = LocalContext.current.imageLoader,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "MIA",
                    color = Color.White,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            Text(
                "120",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = "@",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                "102",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Column {
                AsyncImage(
                    model = "Image Url",
                    contentDescription = "",
                    imageLoader = LocalContext.current.imageLoader,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "WAS",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

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
private fun ConcatMapXMainSubscriber() {
    MainScreen()
}