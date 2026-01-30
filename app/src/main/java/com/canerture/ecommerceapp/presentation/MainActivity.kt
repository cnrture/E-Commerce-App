package com.canerture.ecommerceapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.canerture.ecommerceapp.R
import com.canerture.ecommerceapp.common.delegate.viewBinding
import com.canerture.ecommerceapp.common.gone
import com.canerture.ecommerceapp.common.visible
import com.canerture.ecommerceapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.splashFragment,
                R.id.signInFragment,
                R.id.signUpFragment,
                R.id.forgotPasswordFragment,
                R.id.paymentSuccessFragment,
                    -> {
                    binding.bottomNav.gone()
                    binding.floatingActionButton.gone()
                }

                else -> {
                    binding.bottomNav.visible()
                    binding.floatingActionButton.visible()
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }
    }
}