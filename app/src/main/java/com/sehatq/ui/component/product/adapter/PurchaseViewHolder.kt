package com.sehatq.ui.component.product.adapter

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sehatq.R
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.databinding.PurchaseItemBinding



class PurchaseViewHolder(private val itemBinding: PurchaseItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(data: ProductItem) {
        itemBinding.tvCaption.text = data.price
        itemBinding.tvName.text = data.title
        Picasso.get().load(data.imageUrl).placeholder(R.drawable.ic_logo).error(R.drawable.ic_logo).into(itemBinding.ivRecipeItemImage)
    }
}

