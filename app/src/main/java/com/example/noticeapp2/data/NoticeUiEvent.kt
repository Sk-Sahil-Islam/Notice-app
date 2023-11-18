package com.example.noticeapp2.data

sealed class NoticeUiEvent {
    data class HeadingChange(val heading: String): NoticeUiEvent()
    data class BodyChange(val body: String): NoticeUiEvent()
}