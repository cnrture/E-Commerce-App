package com.canerture.ecommerceapp.domain.usecase.login

import com.canerture.ecommerceapp.domain.repository.Authenticator
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authenticator: Authenticator,
) {
    suspend operator fun invoke() = authenticator.signOut()
}