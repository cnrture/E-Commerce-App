package com.canerture.ecommerceapp.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canerture.ecommerceapp.common.margin
import com.canerture.ecommerceapp.data.model.Product
import com.canerture.ecommerceapp.databinding.ItemFavoriteBinding

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Product>()

    var onDeleteClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) =
        holder.bind(list[position], position)

    inner class ProductsViewHolder(private var binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product, position: Int) {
            with(binding) {

                tvTitle.text = item.title
                ratingBar.rating = item.rate.toFloat()
                tvPrice.text = if (item.salePrice != null) {
                    "$${item.salePrice}"
                } else {
                    "$${item.price}"
                }
                Glide.with(imgProduct).load(item.image).into(imgProduct)

                imgProduct.setOnClickListener {
                    val action =
                        FavoritesFragmentDirections.actionFavoritesFragmentToProductDetailFragment(
                            item
                        )
                    it.findNavController().navigate(action)
                }

                imgDelete.setOnClickListener { onDeleteClick(item.id) }

                if (position == list.size - 1) {
                    binding.cardView.margin(
                        bottom = cardView.context.resources.getDimensionPixelSize(
                            com.intuit.sdp.R.dimen._12sdp
                        )
                    )
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