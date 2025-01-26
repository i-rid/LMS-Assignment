package com.lms.lmsassignment.data.model

data class UpComingFixturesResponse(
    val teamId: Int,
    val teamName: String,
    val teamLogo: String,
    val oppoTeamId: Int,
    val oppTeamName: String,
    val oppLogo: String,
    val dateTime: String
)