package com.sehatq.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.sehatq.databinding.SplashLayoutBinding
import com.sehatq.ui.ViewModelFactory
import com.sehatq.ui.base.BaseActivity
import com.sehatq.ui.component.login.LoginActivity
import com.sehatq.SPLASH_DELAY
import javax.inject.Inject

class SplashActivity : BaseActivity(){
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var splashViewModel: SplashViewModel

    private lateinit var binding: SplashLayoutBinding

    override fun initViewBinding() {
        binding = SplashLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        splashViewModel = viewModelFactory.create(splashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {

    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, LoginActivity::class.java)
            overridePendingTransition(0, 0);
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}
