package com.sehatq.ui.component.product.adapter

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sehatq.R
import com.sehatq.data.dto.category.CategoryItem
import com.sehatq.databinding.CategoryItemBinding



class CategoryViewHolder(private val itemBinding: CategoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(data: CategoryItem) {
        itemBinding.tvName.text = data.name
        Picasso.get().load(data.imageUrl).placeholder(R.drawable.ic_logo).error(R.drawable.ic_logo).into(itemBinding.ivRecipeItemImage)
    }
}

