package com.example.noticeapp2.models

import com.google.firebase.Timestamp

data class Notice(
    var noticeId: String = "",
    val heading: String = "",
    val body: String = "",
    var timestamp: Any? = null,
    var isEdited: Boolean = false
)
