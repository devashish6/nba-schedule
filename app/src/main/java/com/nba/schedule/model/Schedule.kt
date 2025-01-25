package com.nba.schedule.model

data class Schedule (
    val homeId: String,
    val visitorId: String,
    val gameStatus: String,
    val homeScore: String,
    val visitorScore: String,
    val year: String,
    val gameTime: String,
    val canBuy: String,
)