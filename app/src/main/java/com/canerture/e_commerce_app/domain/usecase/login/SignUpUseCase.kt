package com.canerture.e_commerce_app.domain.usecase.login

import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.data.model.User
import com.canerture.e_commerce_app.domain.repository.Authenticator
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(
        user: User,
        password: String
    ): Resource<Unit> {
        return try {
            Resource.Loading
            Resource.Success(authenticator.signUpWithEmailAndPassword(user, password))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}