package com.project.testtaskl_tech.ui.dev_exam

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.ui.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DevExamViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app)
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
                repo.getAllInformation().collect { listInfo ->
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
                _allInformationLiveDate.postValue(DevExamLoadState.Success(repo.getRefreshInformation()))
            }.onFailure {
                _allInformationLiveDate.postValue(DevExamLoadState.Error(it.message))
            }
        }
    }
}

sealed class DevExamLoadState {
    data class Success(val listInfo: List<RemoteAllInformation>) : DevExamLoadState()
    data class Error(val errorMessage: String?) : DevExamLoadState()
    object LoadState : DevExamLoadState()
}
