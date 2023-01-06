package com.canerture.e_commerce_app.presentation.paymentsuccess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.data.model.CRUDResponse
import com.canerture.e_commerce_app.domain.usecase.bag.ClearBagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentSuccessViewModel @Inject constructor(
    private val clearBagUseCase: ClearBagUseCase
) : ViewModel() {

    private val _result = MutableLiveData<Resource<CRUDResponse>>()
    val result: LiveData<Resource<CRUDResponse>> = _result

    fun clearBag() {
        viewModelScope.launch {
            _result.value = Resource.Loading
            _result.value = clearBagUseCase()
        }
    }
}