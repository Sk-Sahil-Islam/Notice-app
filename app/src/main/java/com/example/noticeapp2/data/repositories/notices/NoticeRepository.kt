package com.example.noticeapp2.data.repositories.notices

import com.example.noticeapp2.util.Resource

interface NoticeRepository {
    suspend fun insert(notice: Notice): Resource<String>
    fun getItems(): Resource<List<Notice>>
    suspend fun delete(notice: Notice): Resource<String>
    suspend fun update(notice: Notice): Resource<String>
}