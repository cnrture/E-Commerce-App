package com.canerture.e_commerce_app.presentation.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.common.delegate.viewBinding
import com.canerture.e_commerce_app.common.util.*
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import e_commerce_app.R
import e_commerce_app.databinding.FragmentSignInBinding

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        with(binding) {

            btnSignIn.setOnClickListener {
                if (checkInfos(etEmail, etPassword)) {
                    viewModel.signInWithEmailAndPassword(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                }
            }
        }
    }

    private fun initObservers() {

        with(binding) {

            viewModel.result.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> {
                        progressBar.gone()
                        findNavController().navigate(R.id.action_signInFragment_to_main_graph)
                    }
                    is Resource.Error -> {
                        progressBar.gone()
                        requireView().showSnackbar(it.throwable.message.toString())
                    }
                    is Resource.Loading -> progressBar.visible()
                }
            }
        }
    }

    private fun checkInfos(
        email: TextInputEditText,
        password: TextInputEditText
    ): Boolean {
        val checkInfos = when {
            email.isValidEmail(getString(R.string.invalid_mail)).not() -> false
            password.isNullorEmpty(getString(R.string.invalid_password)).not() -> false
            else -> true
        }
        return checkInfos
    }
}