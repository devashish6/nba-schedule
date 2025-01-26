package com.nba.schedule.model

data class TeamUi(
    val teamId: String,
    val teamName: String,
    val teamDisplayName: String,
    val teamLogo: String,
    val score: String,
    val color: String,
)

fun Team.toTeamsUi() = TeamUi(
    teamId = teamId,
    teamName = teamName,
    teamDisplayName = abbreviation,
    teamLogo = logoUrl,
    color = color,
    score = ""
)
