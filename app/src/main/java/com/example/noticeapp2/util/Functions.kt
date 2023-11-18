package com.example.noticeapp2.util

import com.google.firebase.Timestamp
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun checkForButton(
    initialHeading: String,
    initialBody: String,
    vHead: String,
    vBody: String
): Boolean {
    return (initialHeading != vHead || initialBody != vBody)
}

fun timeParse(time: Timestamp?): String {
    time ?: return ""
    val formatter = DateTimeFormatter.ofPattern("dd MMM yy hh:mm a").withLocale(Locale.getDefault())
    val dataString = time.toDate().toInstant().atZone(ZoneId.systemDefault()).format(formatter)
    return dataString
}