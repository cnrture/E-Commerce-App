package com.canerture.e_commerce_app.presentation.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.canerture.e_commerce_app.data.model.Product
import com.canerture.e_commerce_app.presentation.home.HomeFragmentDirections
import e_commerce_app.databinding.ItemSearchProductBinding

class SearchProductAdapter : RecyclerView.Adapter<SearchProductAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ItemSearchProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ProductsViewHolder(private var binding: ItemSearchProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.product = item

            binding.imgProduct.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(item)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(updatedList: List<Product>) {
        list.clear()
        list.addAll(updatedList)
        notifyItemRangeInserted(0, updatedList.size)
    }
}