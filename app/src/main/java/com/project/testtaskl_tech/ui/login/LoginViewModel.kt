package com.project.testtaskl_tech.ui.login

import android.app.Application
import androidx.lifecycle.*
import com.project.testtaskl_tech.StateSuccess
import com.project.testtaskl_tech.ui.Repository
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = Repository(app)

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
            }.onSuccess {
                saveSuccessSignIn("$phone&$password", Repository.SP_KEY_PASSWORD_PHONE)
            }
        }
    }

    private fun saveSuccessSignIn(value: String, key: String) {
        viewModelScope.launch {
            repo.saveSuccessSignIn(value, key)
        }
    }

    fun getSuccessSignIn(key: String) {
        viewModelScope.launch {
            _successSignInLiveDate.postValue(repo.getSuccessSignIn(key))
        }
    }
}

sealed class LoginLoadState {
    data class Success(val response: StateSuccess) : LoginLoadState()
    data class Error(val errorMessage: String?) : LoginLoadState()
    object LoadState : LoginLoadState()
}
