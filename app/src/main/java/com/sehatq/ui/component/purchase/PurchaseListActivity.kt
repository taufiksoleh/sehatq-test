package com.sehatq.ui.component.purchase

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.sehatq.data.Resource
import com.sehatq.data.dto.purchase.Purchase
import com.sehatq.databinding.PurchaseActivityBinding
import com.sehatq.ui.ViewModelFactory
import com.sehatq.ui.base.BaseActivity
import com.sehatq.ui.component.product.adapter.PurchaseAdapter
import com.sehatq.utils.*
import javax.inject.Inject




class PurchaseListActivity : BaseActivity() {
    private lateinit var binding: PurchaseActivityBinding

    @Inject
    lateinit var purchaseViewModel: PurchaseListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var purchaseAdapter: PurchaseAdapter

    override fun initViewBinding() {
        binding = PurchaseActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        purchaseViewModel = viewModelFactory.create(PurchaseListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipesList.layoutManager = layoutManager
        binding.rvRecipesList.setHasFixedSize(true)

        purchaseViewModel.getPurchase()
    }

    private fun bindListDataProduct(purchase: Purchase) {
        if (!(purchase.purchaseList.isNullOrEmpty())) {
            purchaseAdapter = PurchaseAdapter(purchase.purchaseList)
            binding.rvRecipesList.adapter = purchaseAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun showDataView(show: Boolean) {
        binding.rvRecipesList.visibility = if (show) VISIBLE else GONE
    }

    private fun showLoadingView() {
        binding.rvRecipesList.toGone()
    }


    private fun handleList(status: Resource<Purchase>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let {
                bindListDataProduct(purchase = it)
            }
            is Resource.DataError -> {
                showDataView(false)
            }
        }
    }

    override fun observeViewModel() {
        observe(purchaseViewModel.purchaseLiveData, ::handleList)
    }
}
