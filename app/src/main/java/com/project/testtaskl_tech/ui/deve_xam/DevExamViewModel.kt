package com.project.testtaskl_tech.ui.deve_xam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.ui.Repository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class DevExamViewModel : ViewModel() {

    private val repo = Repository()

    private val _allInformationLiveDate = MutableLiveData<List<RemoteAllInformation>>()
    val allInformationLiveDate: LiveData<List<RemoteAllInformation>>
        get() = _allInformationLiveDate

    private val _refreshInformationLiveDate = MutableLiveData<List<RemoteAllInformation>>()
    val refreshInformationLiveDate: LiveData<List<RemoteAllInformation>>
        get() = _refreshInformationLiveDate

    init {
        repo.getAllInformation()
            .onEach {
                Timber.d("getAllInformation")
                _allInformationLiveDate.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    fun getRefreshInformation() {
        viewModelScope.launch {
            _refreshInformationLiveDate.value = repo.getRefreshInformation()
        }
    }
}