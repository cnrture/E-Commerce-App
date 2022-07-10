package com.canerture.e_commerce_app.presentation.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.domain.usecase.login.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordUseCase: ForgotPasswordUseCase) :
    ViewModel() {

    private val _result = MutableLiveData<Resource<Void>>()
    val result: LiveData<Resource<Void>> = _result

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _result.value = Resource.Loading
            _result.value = forgotPasswordUseCase(email)
        }
    }
}