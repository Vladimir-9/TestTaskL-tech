package com.project.testtaskl_tech.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.testtaskl_tech.StateSuccess
import com.project.testtaskl_tech.data.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (private val repository: RepositoryImpl) : ViewModel() {

    private val _maskPhoneLiveDate = MutableLiveData<LoginLoadState>()
    val maskPhoneLiveDate: LiveData<LoginLoadState>
        get() = _maskPhoneLiveDate

    private val _signInLiveDate = MutableLiveData<LoginLoadState>()
    val signInLiveDate: LiveData<LoginLoadState>
        get() = _signInLiveDate

    private val _successSignInLiveDate = MutableLiveData<String>()
    val successSignInLiveDate: LiveData<String>
        get() = _successSignInLiveDate

    fun getMaskPhone() {
        viewModelScope.launch {
            _maskPhoneLiveDate.postValue(LoginLoadState.LoadState)
            runCatching {
                _maskPhoneLiveDate.postValue(LoginLoadState.Success(repository.getMaskPhone()))
            }.onFailure {
                _maskPhoneLiveDate.postValue(LoginLoadState.Error(it.message))
            }
        }
    }

    fun signIn(phone: String, password: String) {
        viewModelScope.launch {
            _maskPhoneLiveDate.postValue(LoginLoadState.LoadState)
            runCatching {
                _signInLiveDate.postValue(LoginLoadState.Success(repository.signIn(phone, password)))
            }.onFailure {
                _maskPhoneLiveDate.postValue(LoginLoadState.Error(it.message))
            }.onSuccess {
                saveSuccessSignIn("$phone&$password", RepositoryImpl.SP_KEY_PASSWORD_PHONE)
            }
        }
    }

    private fun saveSuccessSignIn(value: String, key: String) {
        viewModelScope.launch {
            repository.saveSuccessSignIn(value, key)
        }
    }

    fun getSuccessSignIn(key: String) {
        viewModelScope.launch {
            _successSignInLiveDate.postValue(repository.getSuccessSignIn(key))
        }
    }
}

sealed class LoginLoadState {
    data class Success(val response: StateSuccess) : LoginLoadState()
    data class Error(val errorMessage: String?) : LoginLoadState()
    object LoadState : LoginLoadState()
}
