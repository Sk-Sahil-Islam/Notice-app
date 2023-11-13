package com.example.noticeapp2.data.repositories.notices

import com.example.noticeapp2.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val noticeCollectionRef: CollectionReference
) : NoticeRepository {
    override suspend fun insert(notice: Notice): Resource<String> {
        return try {
            noticeCollectionRef.add(notice).await()
            Resource.Success("Insert successful")
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override fun getItems(): Resource<List<Notice>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(notice: Notice): Resource<String> {
        TODO("Not yet implemented")
    }

    override suspend fun update(notice: Notice): Resource<String> {
        TODO("Not yet implemented")
    }
}