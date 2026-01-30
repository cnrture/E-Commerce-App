package com.canerture.ecommerceapp.domain.usecase.login

import com.canerture.ecommerceapp.domain.repository.Authenticator
import javax.inject.Inject

class CheckCurrentUserUseCase @Inject constructor(
    private val authenticator: Authenticator,
) {
    suspend operator fun invoke(): Boolean = authenticator.isCurrentUserExist()
}