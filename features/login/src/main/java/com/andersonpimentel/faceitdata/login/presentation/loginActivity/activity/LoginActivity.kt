package com.andersonpimentel.faceitdata.login.presentation.loginActivity.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.andersonpimentel.faceitdata.login.R
import com.andersonpimentel.faceitdata.login.databinding.ActivityLoginBinding
import com.andersonpimentel.faceitdata.login.presentation.loginActivity.viewmodel.LoginViewModel
import com.andersonpimentel.faceitdata.login.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.content.pm.PackageManager.NameNotFoundException
import android.util.AttributeSet
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.andersonpimentel.faceitdata.baseviewmodel.extension.onAction
import com.andersonpimentel.faceitdata.baseviewmodel.extension.onStateChange
import com.andersonpimentel.faceitdata.login.presentation.loginActivity.action.LoginActivityAction
import com.andersonpimentel.faceitdata.login.presentation.loginActivity.state.LoginActivityState

private const val CHROME_PACKAGE = "com.android.chrome"

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<LoginViewModel>()

    private val handleCloseLoginScreen = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.closedLoginScreen()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupClickListeners()
        setupStateListener()
        setupActionListener()
    }

    private fun handleLoginCode() {
        val code = intent.data?.getQueryParameter("code")
        viewModel.receiveLoginCode(code)
    }

    private fun setupClickListeners() {
        binding.btLogin.setOnClickListener {
            viewModel.onLoginButtonClicked()
        }
    }

    private fun setupActionListener() {
        onStateChange(viewModel) { state ->
            state.updateContent()
        }
    }

    private fun setupStateListener() {
        onAction(viewModel) { action ->
            when (action) {
                LoginActivityAction.NavigateToMainActivity -> navigateToMainActivity()
                LoginActivityAction.OpenLoginPage -> navigateToExternalLogin()
                LoginActivityAction.ReceiveCodeLogin -> handleLoginCode()
            }
        }
    }

    private fun LoginActivityState.updateContent() {
        binding.progressCircular.isVisible = isLoading
        binding.contentGroup.isVisible = showContent
    }

    private fun navigateToMainActivity() {
        val mainDeepLink = "faceitdata://main"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mainDeepLink))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
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
        browserBuilder.intent.data = Uri.parse(Constants.LOGIN_URL)
        handleCloseLoginScreen.launch(browserBuilder.intent)
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