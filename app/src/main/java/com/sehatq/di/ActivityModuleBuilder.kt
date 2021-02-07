package com.sehatq.di

import com.sehatq.ui.component.details.DetailsActivity
import com.sehatq.ui.component.login.LoginActivity
import com.sehatq.ui.component.purchase.PurchaseListActivity
import com.sehatq.ui.component.product.ProductListActivity
import com.sehatq.ui.component.product.SearchActivity
import com.sehatq.ui.component.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {
    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector()
    abstract fun contributeHomeActivity(): ProductListActivity

    @ContributesAndroidInjector()
    abstract fun contributeDetailsActivity(): DetailsActivity

    @ContributesAndroidInjector()
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector()
    abstract fun contributePurchaseActivity(): PurchaseListActivity

    @ContributesAndroidInjector()
    abstract fun contributeSearchActivity(): SearchActivity
}
