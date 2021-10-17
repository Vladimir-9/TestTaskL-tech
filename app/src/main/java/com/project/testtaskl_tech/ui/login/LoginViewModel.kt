package com.project.testtaskl_tech.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.testtaskl_tech.StateSuccess
import com.project.testtaskl_tech.ui.Repository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repo = Repository()

    private val _maskPhoneLiveDate = MutableLiveData<LoginLoadState>()
    val maskPhoneLiveDate: LiveData<LoginLoadState>
        get() = _maskPhoneLiveDate

    private val _signInLiveDate = MutableLiveData<LoginLoadState>()
    val signInLiveDate: LiveData<LoginLoadState>
        get() = _signInLiveDate

    fun getMaskPhone() {
        viewModelScope.launch {
            _maskPhoneLiveDate.postValue(LoginLoadState.LoadState)
            runCatching {
                _maskPhoneLiveDate.postValue(LoginLoadState.Success(repo.getMaskPhone()))
            }.onFailure {
                _maskPhoneLiveDate.postValue(LoginLoadState.Error(it.message))
            }
        }
    }

    fun signIn(phone: String, password: String) {
        viewModelScope.launch {
            _maskPhoneLiveDate.postValue(LoginLoadState.LoadState)
            runCatching {
                _signInLiveDate.postValue(LoginLoadState.Success(repo.signIn(phone, password)))
            }.onFailure {
                _maskPhoneLiveDate.postValue(LoginLoadState.Error(it.message))
            }
        }
    }
}

sealed class LoginLoadState {
    data class Success(val response: StateSuccess) : LoginLoadState()
    data class Error(val errorMessage: String?) : LoginLoadState()
    object LoadState : LoginLoadState()
}
