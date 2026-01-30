package com.canerture.ecommerceapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canerture.ecommerceapp.R
import com.canerture.ecommerceapp.data.model.Product
import com.canerture.ecommerceapp.databinding.ItemSaleProductBinding

class SaleProductsAdapter : RecyclerView.Adapter<SaleProductsAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Product>()

    var onProductClick: (Product) -> Unit = {}
    var onFavoriteClick: (Product) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemSaleProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ProductsViewHolder(private var binding: ItemSaleProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {

            with(binding) {
                Glide.with(imgProduct).load(item.image).into(imgProduct)

                tvProductName.text = item.title
                tvProductPrice.text = "$${item.price}"

                tvProductSale.text = if (item.salePrice != null) {
                    "%${((item.price - item.salePrice) / item.price * 100).toInt()} OFF"
                } else {
                    ""
                }

                if (item.isFavorite) imgFavorite.setImageResource(R.drawable.ic_favorite_selected)
                else imgFavorite.setImageResource(R.drawable.ic_favorite_unselected)

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
                    onProductClick(item)
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