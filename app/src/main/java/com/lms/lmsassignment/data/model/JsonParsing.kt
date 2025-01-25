package com.lms.lmsassignment.data.model

import org.json.JSONArray


fun parseTeamAndSponsor(str: String) : TeamAndSponsor{
    val jsonArray = JSONArray(str) // Parse the entire JSON array
    // Access Json[0] (First Array)
    val json0 = jsonArray.getJSONArray(0) // Get the first list
    val team = json0.getJSONObject(0) // Get the first object in Json[0]

    val teamName = team.getString("TeamName")
    val teamLogo = team.getString("TeamLogo")
    val sponsorLogo = team.getString("SponsorLogo")
    val teamDescription = team.getString("TeamDescription")

    return TeamAndSponsor(
        teamName,
        teamLogo,
        sponsorLogo,
        teamDescription
    )
}
fun parseWinsAndLoses(str: String) : WinsAndLoses{
    val jsonArray = JSONArray(str) // Parse the entire JSON array
    val json1 = jsonArray.getJSONArray(1) // Get the second list
    val stats = json1.getJSONObject(0) // Get the first object in Json[1]

    val gamesPlayed = stats.getInt("gamesPlayed")
    val winRatio = stats.getDouble("WinRatio")
    val wins = stats.getInt("Wins")
    val loses = stats.getInt("Loses")

    return WinsAndLoses(
        gamesPlayed,
        winRatio,
        wins,
        loses
    )
}

fun parseRankAndForms(str: String): RankAndForms {
    val jsonArray = JSONArray(str) // Parse the entire JSON array
    val json1 = jsonArray.getJSONArray(2) // Get the second list
    val stats = json1.getJSONObject(0) // Get the first object in Json[1]

    val worldRank = stats.getInt("WorldRank")
    val countryRank = stats.getInt("CountryRank")
    val regionalRank = stats.getInt("RegionalRank")
    val form = stats.getString("Form")

    return RankAndForms(
        worldRank,
        countryRank,
        regionalRank,
        form
    )
}

fun parseBatsmenBowlersAllRounders(str: String): Triple<MutableList<BatsmenBowlersAllRounders>, MutableList<BatsmenBowlersAllRounders>, MutableList<BatsmenBowlersAllRounders>> {
    val jsonArray = JSONArray(str) // Parse the entire JSON array
    val json3 = jsonArray.getJSONArray(3)
    val json4 = jsonArray.getJSONArray(4)
    val json5 = jsonArray.getJSONArray(5)

    val batsmenList = mutableListOf<BatsmenBowlersAllRounders>()
    val bowlersList = mutableListOf<BatsmenBowlersAllRounders>()
    val allRoundersList = mutableListOf<BatsmenBowlersAllRounders>()

    for (i in 0 until json3.length()) {
        val batsmenObject = json3.getJSONObject(i)
        val batsmen = BatsmenBowlersAllRounders(
            batsmenObject.getInt("UserId"),
            batsmenObject.getString("FirstName"),
            batsmenObject.getInt("Nationality"),
            batsmenObject.getString("UserPicture"),
            batsmenObject.getInt("WorldRank"),
            batsmenObject.getInt("NationalRank")
        )
        batsmenList.add(batsmen)
    }
    for (i in 0 until json4.length()) {
        val bowlersObject = json4.getJSONObject(i)
        val bowlers = BatsmenBowlersAllRounders(
            bowlersObject.getInt("UserId"),
            bowlersObject.getString("FirstName"),
            bowlersObject.getInt("Nationality"),
            bowlersObject.getString("UserPicture"),
            bowlersObject.getInt("WorldRank"),
            bowlersObject.getInt("NationalRank")
        )
        bowlersList.add(bowlers)
    }
    for (i in 0 until json5.length()) {
        val allRoundersObject = json5.getJSONObject(i)
        val allRounders = BatsmenBowlersAllRounders(
            allRoundersObject.getInt("UserId"),
            allRoundersObject.getString("FirstName"),
            allRoundersObject.getInt("Nationality"),
            allRoundersObject.getString("UserPicture"),
            allRoundersObject.getInt("WorldRank"),
            allRoundersObject.getInt("NationalRank")
        )
        allRoundersList.add(allRounders)
    }

    return Triple(batsmenList, bowlersList, allRoundersList)
}

fun parseRecentVideos(str: String): MutableList<RecentVideos> {
    val jsonArray = JSONArray(str) // Parse the entire JSON array
    val jsonRV = jsonArray.getJSONArray(9)
    val recentVideosList = mutableListOf<RecentVideos>()

    for (i in 0 until jsonRV.length()) {
        val recentVideosObject = jsonRV.getJSONObject(i)
        val recentVideos = RecentVideos(
            recentVideosObject.getInt("TeamFixture"),
            recentVideosObject.getString("Date"),
            recentVideosObject.getString("PlaybackUrl"),
            recentVideosObject.getString("YouTube"),
            recentVideosObject.getString("FixDate"),
            recentVideosObject.getInt("TeamId")
        )
        recentVideosList.add(recentVideos)
    }
    return recentVideosList
}
fun parseHonoursAndAwards(str: String): HonoursAndAwards {
    val jsonArray = JSONArray(str) // Parse the entire JSON array
    // Access Json[6] (First Array)
    val json0 = jsonArray.getJSONArray(6) // Get the first list
    val team = json0.getJSONObject(0) // Get the first object in Json[6]

    val champ = team.getInt("Champion")
    val rUp = team.getInt("RunnersUp")
    return HonoursAndAwards(champ,rUp)
}

fun parseRecentResults(str: String): List<RecentResults> {
    val jsonArray = JSONArray(str) // Parse the entire JSON array
    val json = jsonArray.getJSONArray(7)
    val recentResultsList = mutableListOf<RecentResults>()

    for (i in 0 until json.length()) {
        val recentResultsObject = json.getJSONObject(i)
        val recentResults = RecentResults(
            recentResultsObject.getInt("TeamId"),
            recentResultsObject.getString("TeamName"),
            recentResultsObject.getString("TeamLogo"),
            recentResultsObject.getInt("oppoTeamId"),
            recentResultsObject.getString("oppTeamName"),
            recentResultsObject.getString("oppLogo"),
            recentResultsObject.getString("MatchInfo"),
            recentResultsObject.getString("DateTime")
        )
        recentResultsList.add(recentResults)
    }
    return recentResultsList
}