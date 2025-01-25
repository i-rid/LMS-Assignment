package com.lms.lmsassignment.data.model

data class SummaryResponse (
    val teamAndSponsor: TeamAndSponsor,
    val winsAndLoses: WinsAndLoses,
    val rankAndForms: RankAndForms,
    val batsmenList: List<BatsmenBowlersAllRounders>,
    val bowlersList: List<BatsmenBowlersAllRounders>,
    val allRoundersList: List<BatsmenBowlersAllRounders>,
    val recentVideosList: List<RecentVideos>,
    val honoursAndAwards: HonoursAndAwards,
    val recentResults: List<RecentResults>
)

data class TeamAndSponsor(
    val TeamName: String,
    val TeamLogo: String,
    val SponsorLogo: String,
    val TeamDescription: String
)

data class WinsAndLoses(
    val gamesPlayed: Int,
    val WinRatio: Double,
    val Wins: Int,
    val Loses: Int
)

data class RankAndForms(
    val WorldRank: Int,
    val CountryRank: Int,
    val RegionalRank: Int,
    val Form: String
)

data class BatsmenBowlersAllRounders(
    val UserId: Int,
    val UserName: String,
    val Nationality: Int,
    val UserPicture: String,
    val WorldRank: Int,
    val NationalRank: Int
)

data class RecentVideos(
    val TeamFixture: Int,
    val Date: String,
    val PlaybackUrl: String,
    val YouTube: String,
    val FixDate: String,
    val TeamId: Int
)