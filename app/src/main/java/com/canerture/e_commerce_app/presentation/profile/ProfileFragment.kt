package com.canerture.e_commerce_app.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.canerture.e_commerce_app.R
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.common.delegate.viewBinding
import com.canerture.e_commerce_app.common.gone
import com.canerture.e_commerce_app.common.showSnackbar
import com.canerture.e_commerce_app.common.visible
import com.canerture.e_commerce_app.databinding.FragmentProfileBinding
import com.canerture.e_commerce_app.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        binding.btnSignOut.setOnClickListener {
            profileViewModel.signOut()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

    private fun initObservers() {
        with(binding) {
            with(profileViewModel) {

                currentUser.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            user = it.data
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
}