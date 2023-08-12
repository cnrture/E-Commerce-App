package com.canerture.e_commerce_app.presentation.payment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.canerture.e_commerce_app.R
import com.canerture.e_commerce_app.common.CreditCardTextFormatter
import com.canerture.e_commerce_app.common.checkMonthYear
import com.canerture.e_commerce_app.common.delegate.viewBinding
import com.canerture.e_commerce_app.common.isNullorEmpty
import com.canerture.e_commerce_app.databinding.FragmentPaymentBinding
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    private val monthList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val yearList = listOf(2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032)

    private var monthValue = 0
    private var yearValue = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val monthAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, monthList)
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, yearList)

        with(binding) {

            etCreditCardNumber.addTextChangedListener(CreditCardTextFormatter())

            actExpireOnMonth.setAdapter(monthAdapter)
            actExpireOnYear.setAdapter(yearAdapter)

            actExpireOnMonth.setOnItemClickListener { _, _, position, _ ->
                monthValue = monthList[position]
            }

            actExpireOnYear.setOnItemClickListener { _, _, position, _ ->
                yearValue = yearList[position]
            }

            imgBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnPayNow.setOnClickListener {
                if (checkInfos(
                        etCardholderName,
                        etCreditCardNumber,
                        actExpireOnMonth,
                        actExpireOnYear,
                        etCvcCode,
                        etAddress
                    )
                )
                    findNavController().navigate(R.id.action_paymentFragment_to_paymentSuccessFragment)
            }
        }
    }

    private fun checkInfos(
        cardHolderName: TextInputEditText,
        creditCardNumber: TextInputEditText,
        month: AutoCompleteTextView,
        year: AutoCompleteTextView,
        cvcCode: TextInputEditText,
        address: TextInputEditText,
    ): Boolean {
        val checkInfos = when {
            cardHolderName.isNullorEmpty(getString(R.string.invalid_cardholder_name)).not() -> false
            creditCardNumber.isNullorEmpty(getString(R.string.invalid_credit_card_number))
                .not() -> false

            month.checkMonthYear(monthValue, getString(R.string.invalid_month)).not() -> false
            year.checkMonthYear(yearValue, getString(R.string.invalid_year)).not() -> false
            cvcCode.isNullorEmpty(getString(R.string.invalid_cvc_code)).not() -> false
            address.isNullorEmpty(getString(R.string.invalid_address)).not() -> false
            else -> true
        }
        return checkInfos
    }
}