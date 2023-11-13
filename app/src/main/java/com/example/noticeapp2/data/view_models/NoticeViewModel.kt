package com.example.noticeapp2.data.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noticeapp2.data.repositories.notices.Notice
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

    private val _state = MutableStateFlow<Resource<String>?>(null)
    val state: StateFlow<Resource<String>?> = _state

    fun insertNotice(notice: Notice) = viewModelScope.launch {
        _state.value = Resource.Loading()
        val result = repository.insert(notice)
        _state.value = result
    }
}