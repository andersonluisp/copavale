package com.andersonpimentel.faceitdata.login.presentation.loginActivity.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.andersonpimentel.faceitdata.login.R
import com.andersonpimentel.faceitdata.login.databinding.ActivityLoginBinding
import com.andersonpimentel.faceitdata.login.presentation.loginActivity.viewmodel.LoginViewModel
import com.andersonpimentel.faceitdata.login.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.pm.PackageManager.NameNotFoundException

private const val CHROME_PACKAGE = "com.android.chrome"

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleLoginCode()
        setupListeners()
        setupObservers()
    }

    private fun handleLoginCode() {
        if (intent != null && intent.data != null) {
            val code = intent.data?.getQueryParameter("code")
            viewModel.receiveLoginCode(code)
        }
    }

    private fun setupObservers() {
        viewModel.userDataLiveData.observe(this) {
            Log.d("Teste", it.toString())
        }
    }

    private fun setupListeners() {
        binding.btLogin.setOnClickListener {
            navigateToExternalLogin()
        }
    }

    private fun navigateToExternalLogin() {
        if (this.isPackageInstalled("com.android.chrome")) {
            openChromeCustomTabs()
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.LOGIN_URL))
            startActivity(intent)
        }
    }

    private fun openChromeCustomTabs() {
        val toolbarColorParams = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(this, R.color.black))
            .build()
        val browserBuilder = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)
            .setDefaultColorSchemeParams(toolbarColorParams)
            .build()
        browserBuilder.intent.setPackage(CHROME_PACKAGE)
        browserBuilder.launchUrl(this, Uri.parse(Constants.LOGIN_URL))
    }
}

fun Context.isPackageInstalled(packageName: String): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: NameNotFoundException) {
        false
    }
}