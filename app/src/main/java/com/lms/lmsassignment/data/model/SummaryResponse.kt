package com.lms.lmsassignment.data.model

data class SummaryResponse (
    // obj 0 description
    val description: Description
)

data class Description(
    val TeamName: String,
    val TeamLogo: String,
    val SponsorLogo: String,
    val TeamDescription: String
)