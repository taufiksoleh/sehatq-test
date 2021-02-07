package com.sehatq.ui.component.details

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.sehatq.R
import com.sehatq.PRODUCT_ITEM_KEY
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.databinding.DetailsLayoutBinding
import com.sehatq.ui.ViewModelFactory
import com.sehatq.ui.base.BaseActivity
import com.sehatq.ui.component.purchase.PurchaseListViewModel
import com.sehatq.utils.observe
import javax.inject.Inject



class DetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: DetailsViewModel

    @Inject
    lateinit var viewModelPurchase: PurchaseListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: DetailsLayoutBinding


    override fun initViewBinding() {
        binding = DetailsLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initIntentData(intent.getParcelableExtra(PRODUCT_ITEM_KEY) ?: ProductItem())
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun observeViewModel() {
        observe(viewModel.productData, ::initializeView)
    }

    override fun initializeViewModel() {
        viewModel = viewModelFactory.create(viewModel::class.java)
        viewModelPurchase = viewModelFactory.create(viewModelPurchase::class.java)
    }

    private fun initializeView(data: ProductItem) {
        binding.tvName.text = data.title
        binding.tvHeadline.text = data.price
        binding.tvDescription.text = data.description
        Picasso.get()
            .load(data.imageUrl)
            .placeholder(R.drawable.ic_logo)
            .into(binding.ivRecipeImage)

        binding.favorite.apply {
            if(data.love == 1){
                setImageResource(R.drawable.ic_baseline_favorite_24)
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@DetailsActivity, R.color.colorPrimary))
                alpha = 1f
            } else {
                setImageResource(R.drawable.ic_baseline_favorite_border_24)
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@DetailsActivity, R.color.black))
                alpha = 0.3f
            }
        }
        binding.btnBuy.setOnClickListener {
            viewModelPurchase.insertPurchase(data)
        }
    }
}
