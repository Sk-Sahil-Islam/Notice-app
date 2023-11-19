package com.example.noticeapp2.data.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticeapp2.data.NoticeUiEvent
import com.example.noticeapp2.models.Notice
import com.example.noticeapp2.data.repositories.notices.NoticeRepository
import com.example.noticeapp2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val repository: NoticeRepository
) : ViewModel() {

    var noticeState = mutableStateOf(Notice())
    val isEditingStartedHead = mutableStateOf(false)
    val isEditingStartedBody = mutableStateOf(false)

    val heading = mutableStateOf("")
    val body = mutableStateOf("")

    private val _state = MutableStateFlow<Resource<String>?>(null)
    val state: StateFlow<Resource<String>?> = _state

    private val _res =  MutableStateFlow<Resource<List<Notice>>>(Resource.Loading())
    val res: StateFlow<Resource<List<Notice>>> = _res

    init {
        getNotice()
    }

    fun insertNotice(notice: Notice) = viewModelScope.launch {
        _state.value = Resource.Loading()
        val result = repository.insert(notice)
        _state.value = result
    }

    fun deleteNotice(noticeId: String) = viewModelScope.launch {
        _state.value = Resource.Loading()
        val result = repository.delete(noticeId)
        _state.value = result
    }

    fun getNotice() = viewModelScope.launch {
        repository.getItems().collect {
            _res.value = it
        }
    }

    fun onEvent(event: NoticeUiEvent) {
        when(event) {
            is NoticeUiEvent.BodyChange -> {
                noticeState.value = noticeState.value.copy(
                    body = event.body
                )
            }
            is NoticeUiEvent.HeadingChange -> {
                noticeState.value = noticeState.value.copy(
                    heading = event.heading
                )
            }
        }
    }

}