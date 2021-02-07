package com.sehatq.ui.component.product.adapter

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sehatq.R
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.databinding.ProductItemBinding
import com.sehatq.ui.base.listeners.RecyclerProductItemListener



class ProductViewHolder(private val itemBinding: ProductItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(data: ProductItem, recyclerProductItemListener: RecyclerProductItemListener) {
        itemBinding.tvCaption.text = data.description
        itemBinding.tvName.text = data.title
        Picasso.get().load(data.imageUrl).placeholder(R.drawable.ic_logo).error(R.drawable.ic_logo).into(itemBinding.ivRecipeItemImage)
        itemBinding.rlRecipeItem.setOnClickListener { recyclerProductItemListener.onItemSelected(data) }
        itemBinding.favorite.apply {
            if(data.love == 1){
                setImageResource(R.drawable.ic_baseline_favorite_24)
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(itemBinding.root.context, R.color.colorPrimary))
            } else {
                setImageResource(R.drawable.ic_baseline_favorite_border_24)
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(itemBinding.root.context, R.color.black))
                alpha = 0.3f
            }
        }
    }
}

