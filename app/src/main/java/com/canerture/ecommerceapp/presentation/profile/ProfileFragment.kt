package com.canerture.ecommerceapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.canerture.ecommerceapp.R
import com.canerture.ecommerceapp.common.Resource
import com.canerture.ecommerceapp.common.delegate.viewBinding
import com.canerture.ecommerceapp.common.gone
import com.canerture.ecommerceapp.common.showSnackbar
import com.canerture.ecommerceapp.common.visible
import com.canerture.ecommerceapp.databinding.FragmentProfileBinding
import com.canerture.ecommerceapp.presentation.MainActivity
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
                            tvEmail.text = it.data.email
                            tvNickname.text = it.data.nickname
                            tvPhoneNumber.text = it.data.phoneNumber
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