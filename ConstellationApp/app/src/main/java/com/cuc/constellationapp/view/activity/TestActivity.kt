package com.cuc.constellationapp.view.activity

import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cuc.constellationapp.R
import com.cuc.constellationapp.model.data.Test
import kotlinx.android.synthetic.main.post_detail.view.*
import kotlinx.android.synthetic.main.tarot_activity.*

class TestActivity: AppCompatActivity() {
    private lateinit var test : Test
    lateinit var url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)

        test = intent.getSerializableExtra("testData") as Test   //获取传递过来的数据
        url = test.getUrl()

        webView.settings.javaScriptEnabled=true
        webView.webViewClient= WebViewClient()
        webView.loadUrl(url)

        val title = findViewById<TextView>(R.id.test_title)
        title.text=test.getTitle()
        val returnBtn = findViewById<ImageView>(R.id.returnView)
        returnBtn.setOnClickListener {
            finish()    //关闭当前页面，返回上一个activity
        }
    }
}