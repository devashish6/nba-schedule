package com.nba.schedule.model

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class ScheduleUi(
    val homeId: String,
    val visitorId: String,
    val gameStatus: Int, // 1-> future, 2-> Live, 3-> Past
    val homeScore: String,
    val visitorScore: String,
    val year: Int,
    val teamCity: String,
    val isHomeMatch: Boolean = false,
    val formattedDate: String, //Match date
    val gameDate: String, //Match date
    val startTime: String, //Match time
    val canBuy: Boolean, //To show and hide button
    val homeTeamUi: TeamUi,
    val awayTeamUi: TeamUi,
)

fun Schedule.toScheduleUi(teamList: List<TeamUi>): ScheduleUi {
    val home = teamList.find { it.teamId == homeTeam.teamId }
    val awayTeam = teamList.find { it.teamId == visitingTeam.teamId }

    return ScheduleUi(
        homeId = homeTeam.teamId,
        visitorId = visitingTeam.teamId,
        gameStatus = status,
        homeScore = homeTeam.score,
        visitorScore = visitingTeam.score,
        year = year,
        teamCity = homeTeam.city,
        isHomeMatch = homeTeam.city.equals("Miami", ignoreCase = true),
        formattedDate = formatDate(gameTime),
        gameDate = gameTime,
        startTime = startTime,
        homeTeamUi = TeamUi(
            teamId = homeTeam.teamId,
            teamName = home?.teamName.orEmpty(),
            teamLogo = home?.teamLogo.orEmpty(),
            teamDisplayName = home?.teamDisplayName.orEmpty(),
            score = homeTeam.score
        ),
        awayTeamUi = TeamUi(
            teamId = visitingTeam.teamId,
            teamName = awayTeam?.teamName.orEmpty(),
            teamLogo = awayTeam?.teamLogo.orEmpty(),
            teamDisplayName = awayTeam?.teamDisplayName.orEmpty(),
            score = visitingTeam.score
        ),
        canBuy = buyTicketUrl.isNullOrEmpty()
    )

}


private fun formatDate(date: String): String {
    val zonedDateTime = ZonedDateTime.parse(date)
    val formatter =
        DateTimeFormatter.ofPattern("E MMM dd") // "E" gives a short form of the day (e.g., "Tue")
    return zonedDateTime.format(formatter)
}