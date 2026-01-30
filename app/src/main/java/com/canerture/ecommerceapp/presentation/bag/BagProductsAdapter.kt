package com.canerture.ecommerceapp.presentation.bag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.canerture.ecommerceapp.data.model.Product
import com.canerture.ecommerceapp.databinding.ItemBagProductBinding

class BagProductsAdapter : RecyclerView.Adapter<BagProductsAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Product>()

    var onIncreaseClick: (Double) -> Unit = {}
    var onDecreaseClick: (Double) -> Unit = {}
    var onDeleteClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemBagProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ProductsViewHolder(private var binding: ItemBagProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            with(binding) {

                var productCount = 1

                tvProductName.text = item.title
                tvPrice.text = if (item.salePrice != null) {
                    "$${item.salePrice}"
                } else {
                    "$${item.price}"
                }

                if (productCount == 1) tvProductCount.text = productCount.toString()

                Glide.with(imgProduct).load(item.image).into(imgProduct)

                imgIncrease.setOnClickListener {
                    onIncreaseClick(item.price)
                    productCount++
                    tvProductCount.text = productCount.toString()
                }

                imgDecrease.setOnClickListener {
                    if (productCount != 1) {
                        onDecreaseClick(item.price)
                        productCount--
                        tvProductCount.text = productCount.toString()
                    } else {
                        onDeleteClick(item.id)
                    }
                }

                imgDelete.setOnClickListener { onDeleteClick(item.id) }
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