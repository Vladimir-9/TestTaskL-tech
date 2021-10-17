package com.project.testtaskl_tech.ui.dev_exam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.ui.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DevExamViewModel : ViewModel() {

    private val repo = Repository()
    private var jobAllInformation: Job? = null

    private val _allInformationLiveDate = MutableLiveData<List<RemoteAllInformation>>()
    val allInformationLiveDate: LiveData<List<RemoteAllInformation>>
        get() = _allInformationLiveDate

    private val _refreshInformationLiveDate = MutableLiveData<List<RemoteAllInformation>>()
    val refreshInformationLiveDate: LiveData<List<RemoteAllInformation>>
        get() = _refreshInformationLiveDate

    fun jobCancel() {
        jobAllInformation?.cancel()
    }

    fun getAllInformation() {
        jobAllInformation = viewModelScope.launch {
            repo.getAllInformation().collect { listInfo ->
                _allInformationLiveDate.postValue(listInfo)
            }
        }
    }

    fun getRefreshInformation() {
        viewModelScope.launch {
            _refreshInformationLiveDate.value = repo.getRefreshInformation()
        }
    }
}