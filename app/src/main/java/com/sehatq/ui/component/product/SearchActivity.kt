package com.sehatq.ui.component.product

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sehatq.PRODUCT_ITEM_KEY
import com.sehatq.data.Resource
import com.sehatq.data.dto.home.Home
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.data.error.SEARCH_ERROR
import com.sehatq.databinding.SearchActivityBinding
import com.sehatq.ui.ViewModelFactory
import com.sehatq.ui.base.BaseActivity
import com.sehatq.ui.component.details.DetailsActivity
import com.sehatq.ui.component.product.adapter.ProductAdapter
import com.sehatq.utils.*
import javax.inject.Inject




class SearchActivity : BaseActivity() {
    private lateinit var binding: SearchActivityBinding

    @Inject
    lateinit var productListViewModel: ProductListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var productAdapter: ProductAdapter

    override fun initViewBinding() {
        binding = SearchActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        productListViewModel = viewModelFactory.create(ProductListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipesList.layoutManager = layoutManager
        binding.rvRecipesList.setHasFixedSize(true)

        productListViewModel.getHome()

        binding.searchText.apply {
            requestFocus()
            addTextChangedListener {
                afterTextChanged {
                    productListViewModel.getSearch(it)
                }
            }
        }
    }

    private fun bindListDataProduct(home: Home) {
        if (!(home.homeList[0].data.productList.isNullOrEmpty() && home.homeList[0].data.category.isNullOrEmpty())) {
            productAdapter = ProductAdapter(productListViewModel, home.homeList[0].data.productList)
            binding.rvRecipesList.adapter = productAdapter

            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun navigateToDetailsScreen(navigateEvent: SingleEvent<ProductItem>) {
        navigateEvent.getContentIfNotHandled()?.let {
            val nextScreenIntent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(PRODUCT_ITEM_KEY, it)
            }
            startActivity(nextScreenIntent)
        }
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showSearchError() {
        productListViewModel.showToastMessage(SEARCH_ERROR)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvRecipesList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvRecipesList.toGone()
    }

    private fun noSearchResult(unit: Unit) {
        showSearchError()
        binding.pbLoading.toGone()
    }

    private fun handleHomeList(status: Resource<Home>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let {
                bindListDataProduct(home = it)
            }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { productListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(productListViewModel.homeLiveData, ::handleHomeList)
        observe(productListViewModel.noSearchFound, ::noSearchResult)
        observeEvent(productListViewModel.openProductDetails, ::navigateToDetailsScreen)
        observeSnackBarMessages(productListViewModel.showSnackBar)
        observeToast(productListViewModel.showToast)

    }
}
