package com.canerture.e_commerce_app.domain.usecase.login

import com.canerture.e_commerce_app.domain.repository.Authenticator
import javax.inject.Inject

class CheckCurrentUserUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(): Boolean = authenticator.isCurrentUserExist()
}