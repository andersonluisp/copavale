package com.andersonpimentel.faceitdata.login.presentation.webview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.andersonpimentel.faceitdata.login.databinding.ActivityWebViewBinding
import com.andersonpimentel.faceitdata.login.util.Constants

class WebViewActivity : AppCompatActivity() {

    private val binding: ActivityWebViewBinding by lazy {
        ActivityWebViewBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginWebView.webChromeClient = WebChromeClient()
        binding.loginWebView.webViewClient = WebViewClient()
        binding.loginWebView.settings.javaScriptEnabled = true
        binding.loginWebView.settings.cacheMode = WebSettings.LOAD_DEFAULT

        val USER_AGENT = "Mozilla/5.0 (Linux; Android 11; sdk_gphone_arm64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.104 Mobile Safari/537.36"
        binding.loginWebView.settings.userAgentString = USER_AGENT
        binding.loginWebView.loadUrl(Constants.LOGIN_URL)
    }
}