package com.canerture.ecommerceapp.domain.usecase.login

import com.canerture.ecommerceapp.common.Resource
import com.canerture.ecommerceapp.domain.repository.Authenticator
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authenticator: Authenticator,
) {
    suspend operator fun invoke(email: String): Resource<Void> {

        return try {
            Resource.Loading
            Resource.Success(authenticator.sendPasswordResetEmail(email))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}