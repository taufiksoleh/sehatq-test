package com.sehatq.ui.component.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.databinding.ProductItemBinding
import com.sehatq.ui.base.listeners.RecyclerProductItemListener
import com.sehatq.ui.component.product.ProductListViewModel



class ProductAdapter(private val productListViewModel: ProductListViewModel, private val product: List<ProductItem>) : RecyclerView.Adapter<ProductViewHolder>() {

    private val onItemClickListener: RecyclerProductItemListener = object : RecyclerProductItemListener {
        override fun onItemSelected(product: ProductItem) {
            productListViewModel.openProductDetails(product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(product[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return product.size
    }
}

