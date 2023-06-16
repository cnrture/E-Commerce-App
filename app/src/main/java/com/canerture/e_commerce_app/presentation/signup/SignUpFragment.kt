package com.canerture.e_commerce_app.presentation.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.common.delegate.viewBinding
import com.canerture.e_commerce_app.common.gone
import com.canerture.e_commerce_app.common.isNullorEmpty
import com.canerture.e_commerce_app.common.isValidEmail
import com.canerture.e_commerce_app.common.showSnackbar
import com.canerture.e_commerce_app.common.visible
import com.canerture.e_commerce_app.data.model.User
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import e_commerce_app.R
import e_commerce_app.databinding.FragmentSignUpBinding

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding) {

            btnSignUp.setOnClickListener {
                if (checkInfos(etEmail, etPassword, etVerifyPassword, etNickname, etPhoneNumber)) {
                    viewModel.signUpWithEmailAndPassword(
                        User(
                            email = etEmail.text.toString(),
                            nickname = etNickname.text.toString(),
                            phoneNumber = etPhoneNumber.text.toString()
                        ),
                        etPassword.text.toString()
                    )
                }
            }

            tvAlreadyAccount.setOnClickListener {
                it.findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            }
        }
    }

    private fun initObservers() {

        with(binding) {

            with(viewModel) {

                checkCurrentUser.observe(viewLifecycleOwner) {
                    if (it) findNavController().navigate(R.id.action_signUpFragment_to_main_graph)
                }

                result.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            requireView().showSnackbar("Sign up successful")
                            findNavController().navigate(R.id.action_signUpFragment_to_main_graph)
                            progressBar.gone()
                        }
                        is Resource.Error -> {
                            progressBar.gone()
                            requireView().showSnackbar(it.throwable.message.toString())
                        }
                        Resource.Loading -> progressBar.visible()
                    }
                }
            }
        }
    }

    private fun checkInfos(
        email: TextInputEditText,
        password: TextInputEditText,
        verifyPassword: TextInputEditText,
        nickname: TextInputEditText,
        phoneNumber: TextInputEditText
    ): Boolean {
        val checkInfos = when {
            email.isValidEmail(getString(R.string.invalid_mail)).not() -> false
            nickname.isNullorEmpty(getString(R.string.invalid_nickname)).not() -> false
            phoneNumber.isNullorEmpty(getString(R.string.invalid_phone_number)).not() -> false
            password.isNullorEmpty(getString(R.string.invalid_password)).not() -> false
            verifyPassword.isNullorEmpty(getString(R.string.invalid_verify_password)).not() -> false
            password.text.toString() != verifyPassword.text.toString() -> false
            else -> true
        }
        return checkInfos
    }
}