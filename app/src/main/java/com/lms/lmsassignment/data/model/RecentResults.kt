package com.lms.lmsassignment.data.model

data class RecentResults(
    val teamId: Int,
    val teamName: String,
    val teamLogo: String,
    val oppoTeamId: Int,
    val oppTeamName: String,
    val oppLogo: String,
    val matchInfo: String,
    val dateTime: String
)