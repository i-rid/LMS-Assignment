package com.lms.lmsassignment.data.model

data class BattingResponse(
    val UserId: Int,
    val UserName: String,
    val UserPicture: String,
    val PlayerInfo: String,
    val Innings: Int,
    val Runs: Int,
    val Average: Double,
    val StrikeRate: Double,
    val HighestScore: Int,
    val Fifties: Int,
    val Hundred: Int,
    val WorldRank: Int,
    val NationalRank: Int,
    val IsFormer: Int
)