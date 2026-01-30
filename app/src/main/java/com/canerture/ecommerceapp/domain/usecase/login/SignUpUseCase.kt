package com.canerture.ecommerceapp.domain.usecase.login

import com.canerture.ecommerceapp.common.Resource
import com.canerture.ecommerceapp.data.model.User
import com.canerture.ecommerceapp.domain.repository.Authenticator
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticator: Authenticator,
) {
    suspend operator fun invoke(
        user: User,
        password: String,
    ): Resource<Unit> {
        return try {
            Resource.Loading
            Resource.Success(authenticator.signUpWithEmailAndPassword(user, password))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}