package com.canerture.e_commerce_app.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.canerture.e_commerce_app.data.model.Product
import e_commerce_app.R
import e_commerce_app.databinding.ItemSaleProductBinding

class SaleProductsAdapter : RecyclerView.Adapter<SaleProductsAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Product>()

    var onFavoriteClick: (Product) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ItemSaleProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ProductsViewHolder(private var binding: ItemSaleProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {

            with(binding) {

                product = item

                imgFavorite.setOnClickListener {

                    if (item.isFavorite) {
                        onFavoriteClick(item)
                        imgFavorite.setImageResource(R.drawable.ic_favorite_unselected)
                    } else {
                        onFavoriteClick(item)
                        imgFavorite.setImageResource(R.drawable.ic_favorite_selected)
                    }
                }

                imgProduct.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(item)
                    it.findNavController().navigate(action)
                }
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