package com.nba.schedule.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nba.schedule.R
import com.nba.schedule.model.ScheduleUi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
internal fun SchedulesScreen(
    schedules: List<ScheduleUi>?,
) {
    var displayMonth by remember {
        mutableStateOf(LocalDateTime.now())
    }

    Column {
        Spacer(modifier = Modifier.height(10.dp))
        CalendarView(
            displayMonth = displayMonth,
            onNext = {
                displayMonth = displayMonth.plusMonths(1)
            }
        ) {
            displayMonth = displayMonth.minusMonths(1)
        }

        if (schedules != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                userScrollEnabled = true
            ) {
                items(schedules.size) { index ->
                    MatchCard(
                        item = schedules[index],
                    )
                    val instant =
                        Instant.parse(schedules[index].gameDate) // Parse the ISO-8601 date string
                    val localDateTime = LocalDateTime.ofInstant(
                        instant,
                        ZoneId.systemDefault()
                    ) // Convert to LocalDateTime in the system's default timezone
                    displayMonth = localDateTime
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

    }
}

@Composable
fun CalendarView(
    displayMonth: LocalDateTime,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
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
                    onNext()
                }
            )
            Text(
                text = displayMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                fontSize = 18.sp,
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.down),
                contentDescription = "Up",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.clickable {
                    onPrevious()
                }
            )
        }
    }
}

@Composable
fun MatchCard(
    item: ScheduleUi,
) {
    val backgroundColor = remember {
        val color = if (item.isHomeMatch) item.awayTeamUi.color else item.homeTeamUi.color
        val colorLong = color.toLong(16)
        if (color.length == 6) {
            Color(0xFF000000 or colorLong)
        } else {
            Color(colorLong)
        }
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
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
                text = item.formattedDate,
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

        Text(
            text = item.arena,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

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