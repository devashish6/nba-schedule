package com.nba.schedule.model

import com.google.gson.annotations.SerializedName

data class TeamsResponse(
    @SerializedName("data")
    val data: TeamsData?
)

data class TeamsData(
    @SerializedName("teams")
    val teams: List<Team>
)

data class Team(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("league_id")
    val leagueId: String,
    @SerializedName("season_id")
    val seasonId: String,
    @SerializedName("ist_group")
    val istGroup: String,
    @SerializedName("tid")
    val teamId: String,
    @SerializedName("tn")
    val teamName: String,
    @SerializedName("ta")
    val abbreviation: String,
    @SerializedName("tc")
    val city: String,
    @SerializedName("di")
    val division: String,
    @SerializedName("co")
    val conference: String,
    @SerializedName("sta")
    val state: String,
    @SerializedName("logo")
    val logoUrl: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("custom_fields")
    val customFields: Any?,
)

data class PublishDetails(
    @SerializedName("environment")
    val environment: String?,
    @SerializedName("locale")
    val locale: String?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("user")
    val user: String?
)
