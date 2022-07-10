package com.canerture.e_commerce_app.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.canerture.e_commerce_app.data.model.Product
import e_commerce_app.databinding.ItemFavoriteBinding

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Product>()

    var onDeleteClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ProductsViewHolder(private var binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            with(binding) {

                product = item

                imgProduct.setOnClickListener {
                    val action =
                        FavoritesFragmentDirections.actionFavoritesFragmentToProductDetailFragment(
                            item
                        )
                    it.findNavController().navigate(action)
                }

                imgDelete.setOnClickListener { onDeleteClick(item.id) }
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