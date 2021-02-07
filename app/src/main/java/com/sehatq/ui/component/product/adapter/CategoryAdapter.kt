package com.sehatq.ui.component.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sehatq.data.dto.category.CategoryItem
import com.sehatq.databinding.CategoryItemBinding



class CategoryAdapter(private val product: List<CategoryItem>) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(product[position])
    }

    override fun getItemCount(): Int {
        return product.size
    }
}

