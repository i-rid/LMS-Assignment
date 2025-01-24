package com.lms.lmsassignment.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lms.lmsassignment.data.model.SummaryResponse
import com.lms.lmsassignment.data.model.parseBatsmenBowlersAllRounders
import com.lms.lmsassignment.data.model.parseRankAndForms
import com.lms.lmsassignment.data.model.parseRecentVideos
import com.lms.lmsassignment.data.model.parseTeamAndSponsor
import com.lms.lmsassignment.data.model.parseWinsAndLoses
import com.lms.lmsassignment.data.remote.api.ApiService
import com.lms.lmsassignment.utils.AppUiState
import com.lms.lmsassignment.utils.Const
import kotlinx.coroutines.launch

class LMSViewModel:ViewModel() {

    private val _summary = MutableLiveData<AppUiState>(AppUiState.Loading)
    val summary: LiveData<AppUiState> = _summary

    private val _squadList = MutableLiveData<AppUiState>(AppUiState.Loading)
    val squadList: LiveData<AppUiState> = _squadList

    private var _battingList = MutableLiveData<AppUiState>(AppUiState.Loading)
    val battingList: LiveData<AppUiState> = _battingList

    private var _bowlingList = MutableLiveData<AppUiState>(AppUiState.Loading)
    val bowlingList: LiveData<AppUiState> = _bowlingList

    private val apiService: ApiService = ApiService.RetrofitInstance.api

    fun getSummary(teamId: Int = Const.BOWLING_TEAM_ID) {
        viewModelScope.launch {
            try {
                val response = apiService.getSummary(teamId).string()
                Log.d("SumNCall", "SumNetCallRaw $response")
                val teamAndSponsor = parseTeamAndSponsor(response)
                val winsAndLoses = parseWinsAndLoses(response)
                val rankAndForms = parseRankAndForms(response)
                val batsmenBowlersAllRounders = parseBatsmenBowlersAllRounders(response)
                val recentVideosList = parseRecentVideos(response)


                val summaryResponse = SummaryResponse(
                    teamAndSponsor,
                    winsAndLoses,
                    rankAndForms,
                    batsmenBowlersAllRounders.first.toList(),
                    batsmenBowlersAllRounders.second.toList(),
                    batsmenBowlersAllRounders.third.toList(),
                    recentVideosList.toList()
                )

                _summary.value = AppUiState.Loaded(summaryResponse)
            } catch (e: Exception) {
                _summary.value = AppUiState.Error("An error occurred: ${e.message}")
            }
        }
    }

    fun getSquadList(
        typeId: Int = Const.SQUAD_TYPE_ID,
        teamId: Int = Const.SQUAD_TEAM_ID
    ){
        _squadList.value = AppUiState.Loading
        viewModelScope.launch {
            try {
                val response = apiService.getSquadList(typeId, teamId)
                _squadList.value = AppUiState.Loaded(response)
                Log.d("SquadNetCall","SquadNetCall: ${response[0].UserName}")
            } catch (e: Exception){
                _squadList.value = AppUiState.Error(e.message ?: "Unknown Error")
                Log.d("SquadNetCall","Error: ${e.message}")
            }
        }
    }

    fun getBattingList(
        typeId: Int = Const.BATTING_TYPE_ID,
        teamId: Int = Const.BATTING_TEAM_ID
    ) {
        _battingList.value = AppUiState.Loading
        viewModelScope.launch {
            try {
                val response = apiService.getBattingList(typeId, teamId)
                _battingList.value = AppUiState.Loaded(response)
                Log.d("BatNetCall","BatNetworkCall: ${response[0].FirstName}")
            } catch (e: Exception) {
                _battingList.value = AppUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getBowlingList(
        typeId: Int = Const.BOWLING_TYPE_ID,
        teamId: Int = Const.BOWLING_TEAM_ID
    ) {
        _bowlingList.value = AppUiState.Loading
        viewModelScope.launch {
            try {
                val response = apiService.getBowlingList(typeId, teamId)
                _bowlingList.value = AppUiState.Loaded(response)
            } catch (e: Exception) {
                _bowlingList.value = AppUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}