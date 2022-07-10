package com.canerture.e_commerce_app.presentation.home.search

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.canerture.e_commerce_app.common.Resource
import com.canerture.e_commerce_app.common.util.gone
import com.canerture.e_commerce_app.common.util.hideKeyboard
import com.canerture.e_commerce_app.common.util.showSnackbar
import com.canerture.e_commerce_app.common.util.visible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import e_commerce_app.databinding.FragmentSearchProductBinding

@AndroidEntryPoint
class SearchProductFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: BottomSheetDialog

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    private val searchProductViewModel: SearchProductViewModel by viewModels()

    private val searchProductAdapter by lazy { SearchProductAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        initObservers()

        with(binding) {

            with(searchProductViewModel) {

                bsLayout.minimumHeight = resources.displayMetrics.heightPixels

                //bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        searchProduct(query.orEmpty())
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText.isNullOrEmpty()) {
                            hideKeyboard(requireActivity(), view)
                        }
                        return false
                    }
                })
            }
        }
    }

    private fun initObservers() {

        with(binding) {

            with(searchProductViewModel) {

                products.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.gone()
                            searchProductAdapter.updateList(it.data)
                            rvSearchProducts.adapter = searchProductAdapter
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
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}