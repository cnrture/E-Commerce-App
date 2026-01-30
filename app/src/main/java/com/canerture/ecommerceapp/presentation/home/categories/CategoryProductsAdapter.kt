package com.canerture.ecommerceapp.presentation.home.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.canerture.ecommerceapp.data.model.Product
import com.canerture.ecommerceapp.databinding.ItemProductBinding
import com.canerture.ecommerceapp.presentation.home.HomeFragmentDirections

class CategoryProductsAdapter : RecyclerView.Adapter<CategoryProductsAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) =
        holder.bind(list[position])

    class ProductsViewHolder(private var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            with(binding) {
                tvProductSale.text = if (item.salePrice != null) {
                    "$${item.salePrice}"
                } else {
                    "$${item.price}"
                }

                tvProductPrice.apply {
                    if (item.salePrice != null) {
                        text = "$${item.price}"
                        paint.isStrikeThruText = true
                        visibility = View.VISIBLE
                    } else {
                        visibility = View.GONE
                    }
                }

                tvProductName.text = item.title

                imgProduct.setOnClickListener {
                    val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(item)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount() = list.size

    fun updateList(updatedList: List<Product>) {
        list.clear()
        list.addAll(updatedList)
        notifyItemRangeInserted(0, updatedList.size)
    }
}