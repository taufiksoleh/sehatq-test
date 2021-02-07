package com.sehatq.ui.component.product

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.sehatq.R
import com.sehatq.PRODUCT_ITEM_KEY
import com.sehatq.data.Resource
import com.sehatq.data.dto.home.Home
import com.sehatq.data.dto.product.ProductItem
import com.sehatq.data.error.SEARCH_ERROR
import com.sehatq.databinding.HomeActivityBinding
import com.sehatq.ui.ViewModelFactory
import com.sehatq.ui.base.BaseActivity
import com.sehatq.ui.component.details.DetailsActivity
import com.sehatq.ui.component.product.adapter.CategoryAdapter
import com.sehatq.ui.component.product.adapter.ProductAdapter
import com.sehatq.ui.component.purchase.PurchaseListActivity
import com.sehatq.utils.*
import kotlinx.android.synthetic.main.home_activity.*
import javax.inject.Inject




class ProductListActivity : BaseActivity() {
    private lateinit var binding: HomeActivityBinding

    @Inject
    lateinit var productListViewModel: ProductListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun initViewBinding() {
        binding = HomeActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        productListViewModel = viewModelFactory.create(ProductListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipesList.layoutManager = layoutManager
        binding.rvRecipesList.setHasFixedSize(true)

        val categoryLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoryList.layoutManager = categoryLayoutManager
        binding.rvCategoryList.setHasFixedSize(true)
        productListViewModel.getHome()

        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        binding.searchBar.setOnClickListener {
            navigateToSearch()
        }
    }

    private fun navigateToSearch(){
        overridePendingTransition(0, 0);
        startActivity(Intent(this, SearchActivity::class.java))
    }

    private fun navigateToPurchase(){
        startActivity(Intent(this, PurchaseListActivity::class.java))
    }

    var navigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profile -> {
                navigateToPurchase()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun bindListDataProduct(home: Home) {
        if (!(home.homeList[0].data.productList.isNullOrEmpty() && home.homeList[0].data.category.isNullOrEmpty())) {
            productAdapter = ProductAdapter(productListViewModel, home.homeList[0].data.productList)
            binding.rvRecipesList.adapter = productAdapter

            categoryAdapter = CategoryAdapter(home.homeList[0].data.category)
            binding.rvCategoryList.adapter = categoryAdapter

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
