package com.canerture.ecommerceapp.domain.usecase.login

import com.canerture.ecommerceapp.common.Resource
import com.canerture.ecommerceapp.data.model.User
import com.canerture.ecommerceapp.domain.repository.Authenticator
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authenticator: Authenticator,
) {
    suspend operator fun invoke(): Resource<User> {

        return try {
            Resource.Loading
            Resource.Success(authenticator.getCurrentUser())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}