package com.lms.lmsassignment.data.model

data class BowlingResponse(
    val UserId: Int,
    val UserName: String,
    val UserPicture: String,
    val PlayerInfo: String,
    val Overs: Double,
    val Wickets: Int,
    val Average: Double,
    val Economy: Double,
    val Best: String,
    val ThreeFA: Int,
    val WorldRank: Int,
    val NationalRank: Int,
    val IsFormer: Int
)
