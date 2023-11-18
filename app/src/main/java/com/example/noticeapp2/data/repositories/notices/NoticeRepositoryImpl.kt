package com.example.noticeapp2.data.repositories.notices

import com.example.noticeapp2.models.Notice
import com.example.noticeapp2.util.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val noticeCollectionRef: CollectionReference
) : NoticeRepository {
    override suspend fun insert(notice: Notice): Resource<String> {

        val document = if(notice.noticeId.isEmpty()) {
            noticeCollectionRef.document()
        } else {
            notice.isEdited = true
            noticeCollectionRef.document(notice.noticeId)
        }
        notice.noticeId = document.id
        notice.timestamp = FieldValue.serverTimestamp()

        return try {
            document.set(notice).await()
            Resource.Success("Insert successful")
        } catch (e: Exception){
            e.printStackTrace()
            notice.isEdited = false
            Resource.Error(e.message ?: "Error while inserting")
        }
    }

    override fun getItems(): Flow<Resource<List<Notice>>> = callbackFlow{

        val snapShotListener = noticeCollectionRef
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapShot, error ->
                if(error != null) {
                    trySend(Resource.Error(error.message.toString()))
                    close(error)
                    return@addSnapshotListener
                }
                if(querySnapShot != null) {
                    val data = querySnapShot.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject<Notice>()
                    }
                    trySend(Resource.Success(data))
                }
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override suspend fun delete(notice: Notice): Resource<String> {
        TODO("Not yet implemented")
    }

    override suspend fun update(notice: Notice): Resource<String> {
        TODO("Not yet implemented")
    }
}