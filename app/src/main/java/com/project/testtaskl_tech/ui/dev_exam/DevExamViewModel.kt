package com.project.testtaskl_tech.ui.dev_exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.testtaskl_tech.data.AllTheInformation
import com.project.testtaskl_tech.data.RepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DevExamViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private var jobAllInformation: Job? = null

    private val _allInformationLiveDate = MutableLiveData<DevExamLoadState>()
    val allInformationLiveDate: LiveData<DevExamLoadState>
        get() = _allInformationLiveDate

    fun jobCancel() {
        jobAllInformation?.cancel()
    }

    fun getAllInformation() {
        jobAllInformation = viewModelScope.launch {
            _allInformationLiveDate.postValue(DevExamLoadState.LoadState)
            runCatching {
                repository.getAllInformation().collect { listInfo ->
                    _allInformationLiveDate.postValue(DevExamLoadState.Success(listInfo))
                }
            }.onFailure {
                _allInformationLiveDate.postValue(DevExamLoadState.Error(it.message))
            }
        }
    }

    fun getRefreshInformation() {
        viewModelScope.launch {
            runCatching {
                _allInformationLiveDate.postValue(DevExamLoadState.Success(repository.getRefreshInformation()))
            }.onFailure {
                _allInformationLiveDate.postValue(DevExamLoadState.Error(it.message))
            }
        }
    }
}

sealed class DevExamLoadState {
    data class Success(val listInfo: List<AllTheInformation>) : DevExamLoadState()
    data class Error(val errorMessage: String?) : DevExamLoadState()
    object LoadState : DevExamLoadState()
}
