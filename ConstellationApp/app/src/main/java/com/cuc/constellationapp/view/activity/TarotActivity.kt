package com.cuc.constellationapp.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.cuc.constellationapp.R
import com.cuc.constellationapp.model.api.Api
import kotlinx.android.synthetic.main.tarot_activity.*

class TarotActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tarot_activity)

        webView.settings.javaScriptEnabled=true
        webView.webViewClient= WebViewClient()
        webView.loadUrl(Api().tarot_url)
    }
}