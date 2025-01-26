package com.nba.schedule.model

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("data")
    val data: ScheduleData?
)

data class ScheduleData(
    @SerializedName("schedules")
    val schedules: List<Schedule>
)

data class Schedule(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("league_id")
    val leagueId: String,
    @SerializedName("season_id")
    val seasonId: String,
    @SerializedName("h")
    val homeTeam: BasicTeam,
    @SerializedName("v")
    val visitingTeam: BasicTeam,
    @SerializedName("gid")
    val gameId: String,
    @SerializedName("gcode")
    val gameCode: String,
    @SerializedName("buy_ticket")
    val buyTicket: String?,
    @SerializedName("bd")
    val broadcasters: Broadcasters,
    @SerializedName("is_game_necessary")
    val isGameNecessary: String,
    @SerializedName("gametime")
    val gameTime: String,
    @SerializedName("arena_name")
    val arenaName: String,
    @SerializedName("arena_city")
    val arenaCity: String,
    @SerializedName("arena_state")
    val arenaState: String,
    @SerializedName("st")
    val status: Int,
    @SerializedName("stt")
    val startTime: String,
    @SerializedName("buy_ticket_url")
    val buyTicketUrl: String?,
    @SerializedName("game_state")
    val gameState: String,
    @SerializedName("template_fields")
    val templateFields: TemplateFields
)

data class BasicTeam(
    @SerializedName("tid")
    val teamId: String,
    @SerializedName("re")
    val record: String,
    @SerializedName("ta")
    val abbreviation: String,
    @SerializedName("tn")
    val teamName: String,
    @SerializedName("tc")
    val city: String,
    @SerializedName("s")
    val score: String,
    @SerializedName("ist_group")
    val istGroup: String?
)

data class Broadcasters(
    @SerializedName("b")
    val broadcasters: List<Broadcaster>
)

data class Broadcaster(
    @SerializedName("seq")
    val sequence: Int,
    @SerializedName("broadcasterId")
    val broadcasterId: Int,
    @SerializedName("disp")
    val displayName: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("lan")
    val language: String,
    @SerializedName("url")
    val url: String?
)

data class TemplateFields(
    @SerializedName("access_pass_setup")
    val accessPassSetup: AccessPassSetup
)

data class AccessPassSetup(
    @SerializedName("stm_cost")
    val stmCost: String?,
    @SerializedName("non_stm_cost")
    val nonStmCost: String?,
    @SerializedName("total_passes_allowed")
    val totalPassesAllowed: String?,
    @SerializedName("available_passes")
    val availablePasses: String?,
    @SerializedName("no_threshold")
    val noThreshold: Boolean,
    @SerializedName("fortress_passes_sold")
    val fortressPassesSold: String?
)
