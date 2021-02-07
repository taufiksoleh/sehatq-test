package com.sehatq.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sehatq.ui.ViewModelFactory
import com.sehatq.ui.component.details.DetailsViewModel
import com.sehatq.ui.component.login.LoginViewModel
import com.sehatq.ui.component.purchase.PurchaseListViewModel
import com.sehatq.ui.component.product.ProductListViewModel
import com.sehatq.ui.component.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Since dagger
 * support multibinding you are free to bind as may ViewModels as you want.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    abstract fun bindUserViewModel(viewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    internal abstract fun bindSplashViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PurchaseListViewModel::class)
    internal abstract fun bindPurchaseViewModel(viewModel: PurchaseListViewModel): ViewModel
}
