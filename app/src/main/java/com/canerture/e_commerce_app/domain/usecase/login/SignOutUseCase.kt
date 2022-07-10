package com.canerture.e_commerce_app.domain.usecase.login

import com.canerture.e_commerce_app.domain.repository.Authenticator
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke() = authenticator.signOut()
}