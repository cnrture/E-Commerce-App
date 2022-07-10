package com.canerture.e_commerce_app.domain.usecase.login

import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.domain.repository.Authenticator
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authenticator: Authenticator
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