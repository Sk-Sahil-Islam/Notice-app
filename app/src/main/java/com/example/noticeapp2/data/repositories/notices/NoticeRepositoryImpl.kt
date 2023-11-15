package com.example.noticeapp2.data.repositories.notices

import com.example.noticeapp2.models.Notice
import com.example.noticeapp2.util.Resource
import com.google.firebase.firestore.CollectionReference
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
        return try {
            noticeCollectionRef.add(notice).await()
            Resource.Success("Insert successful")
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override fun getItems(): Flow<Resource<List<Notice>>> = callbackFlow{
//        trySend(Resource.Loading())
//
//        val subscription = noticeCollectionRef
//            .get()
//            .addOnCompleteListener { task->
//                if(task.isSuccessful) {
//                    val data = task.result?.documents?.mapNotNull { documentSnapshot ->
//                        documentSnapshot.toObject<Notice>()
//                    } ?: emptyList()
//                    trySend(Resource.Success(data))
//                } else {
//                    trySend(Resource.Error(task.exception?.message!!))
//                }
//            }
//        awaitClose {
//            close()
//        }

        val snapShotListener = noticeCollectionRef
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