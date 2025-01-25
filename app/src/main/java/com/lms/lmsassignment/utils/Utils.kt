package com.lms.lmsassignment.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toDayDateMonth(): String {
    val input = this
    // Define the input format and output format
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("EEEE dd MMMM", Locale.ENGLISH)
    // Parse the input date string into a Date object
    val date: Date? = inputFormat.parse(input)
    // Format the Date object into the desired output string
    val formattedDate = date?.let { outputFormat.format(it) }
    return formattedDate?: input // Output: Sunday 04 September
}