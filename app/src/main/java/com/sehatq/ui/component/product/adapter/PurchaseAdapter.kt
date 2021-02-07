package com.sehatq.ui.component.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.databinding.PurchaseItemBinding



class PurchaseAdapter(private val purchase: List<ProductItem>) : RecyclerView.Adapter<PurchaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val itemBinding = PurchaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PurchaseViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        holder.bind(purchase[position])
    }

    override fun getItemCount(): Int {
        return purchase.size
    }
}

