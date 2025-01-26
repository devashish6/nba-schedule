package com.nba.schedule.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nba.schedule.model.TeamUi

@Composable
internal fun TeamsScreen(
    teams: List<TeamUi>?,
) {

    Column {
        Spacer(modifier = Modifier.height(10.dp))
        if (teams != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                userScrollEnabled = true
            ) {
                items(teams.size) { index ->
                    HorizontalTeamCard(
                        teamLogo = teams[index].teamLogo,
                        teamName = teams[index].teamName
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

    }
}