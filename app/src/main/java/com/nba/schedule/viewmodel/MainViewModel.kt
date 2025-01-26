package com.nba.schedule.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.nba.schedule.model.Schedule
import com.nba.schedule.model.ScheduleResponse
import com.nba.schedule.model.Team
import com.nba.schedule.model.TeamsResponse
import com.nba.schedule.model.UiModel
import com.nba.schedule.model.toScheduleUi
import com.nba.schedule.model.toTeamsUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val localScheduleData =
        application.assets.open("schedule.json").bufferedReader().use { it.readText() }
    private val localTeamsData =
        application.assets.open("teams.json").bufferedReader().use { it.readText() }

    private val _scheduleData: MutableStateFlow<List<Schedule>?> = MutableStateFlow(null)
    private val _teamData: MutableStateFlow<List<Team>?> = MutableStateFlow(null)

    val uiState = combine(
        _scheduleData,
        _teamData
    ) { scheduleResponse, teamsResponse ->
        if (scheduleResponse == null || teamsResponse == null) {
            UiModel()
        } else {
            val teamList = teamsResponse.map { it.toTeamsUi() }
            UiModel(
                teamsList = teamList,
                scheduleList = scheduleResponse.map { it.toScheduleUi(teamList) },
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiModel()
    )

    fun loadJsonData() {
        viewModelScope.launch {
            try {
                val finalSchedule = Gson().fromJson(localScheduleData, ScheduleResponse::class.java)
                _scheduleData.update { finalSchedule.data?.schedules }
                val finalTeam = Gson().fromJson(localTeamsData, TeamsResponse::class.java)
                _teamData.update { finalTeam.data?.teams }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}