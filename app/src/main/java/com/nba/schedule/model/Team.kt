package com.nba.schedule.model

data class TeamUi(
    val teamId: String,
    val teamName: String,
    val teamDisplayName: String,
    val teamLogo: String,
    val score: String,
)

fun Team.toTeamsUi() = TeamUi(
    teamId = teamId,
    teamName = teamName,
    teamDisplayName = abbreviation,
    teamLogo = logoUrl,
    score = ""
)
