package com.example.noticeapp2.data.repositories.notices

import com.example.noticeapp2.models.Notice
import com.example.noticeapp2.util.Resource
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    suspend fun insert(notice: Notice): Resource<String>
    fun getItems(): Flow<Resource<List<Notice>>>
    suspend fun delete(notice: Notice): Resource<String>
    suspend fun update(notice: Notice): Resource<String>
}