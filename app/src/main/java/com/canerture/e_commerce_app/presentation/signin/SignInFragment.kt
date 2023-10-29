package com.canerture.e_commerce_app.presentation.signin

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canerture.e_commerce_app.R
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.common.delegate.viewBinding
import com.canerture.e_commerce_app.common.gone
import com.canerture.e_commerce_app.common.showSnackbar
import com.canerture.e_commerce_app.common.visible
import com.canerture.e_commerce_app.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (checkInfo(email, password)) {
                    viewModel.signInWithEmailAndPassword(email, password)
                }
            }
        }

        initObservers()
    }

    private fun initObservers() = with(binding) {
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

                Resource.Loading -> progressBar.visible()
            }
        }
    }

    private fun checkInfo(
        email: String,
        password: String
    ): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches() -> false
            password.isEmpty() -> false
            password.length <= 6 -> false
            else -> true
        }
    }
}