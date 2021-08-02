package com.cuc.constellationapp.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cuc.constellationapp.R
import com.cuc.constellationapp.httprequest.AstroData
import com.cuc.constellationapp.httprequest.GetAlmanacData
import com.cuc.constellationapp.httprequest.GetAstroid
import com.cuc.constellationapp.httprequest.GetFortuneData
import com.cuc.constellationapp.model.api.Api
import kotlinx.android.synthetic.main.data.*
import kotlinx.android.synthetic.main.fortune_fragment.*
import kotlinx.android.synthetic.main.tarot_activity.*
import java.text.SimpleDateFormat
import java.util.*



class FortuneFragment: Fragment(){
    /**
     * 单例模式，使用方法:
     * FortuneFragment.instance.方法()
     */

  //  获取当前系统日期
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val curDate = Date(System.currentTimeMillis())
    val date = formatter.format(curDate)
    var btnToday: Button? = null//获取今日运势按钮
    var btnMonth: Button? = null//获取本月运势按钮
    var btnWeek: Button? = null//获取本周运势按钮
    var btnYear: Button? = null//获取今年运势按钮
    var btnSearch:Button?=null//搜索星座运势按钮

    var flag:Int=1//默认为今日运势
    var astroid:Int=1//默认星座id为1


    companion object{

        val instance by lazy {
            FortuneFragment()
        }
    }



    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fortune_fragment, null, false)

        //获取星座id
        GetAstroid(activity!!,view).getAstroid()

        GetAlmanacData(
            date,
            activity!!,
            view
        ).getAlmanacData()//显示黄历
        GetFortuneData(
            flag,
            astroid,
            date,
            activity!!,
            view
        ).getFortuneData()//页面默认显示今日的运势
        //定义各个按钮
        btnToday = view.findViewById(R.id.today) as Button
        btnMonth = view.findViewById(R.id.month) as Button
        btnWeek = view.findViewById(R.id.week) as Button
        btnYear = view.findViewById(R.id.year) as Button
        //搜索按钮
        btnSearch=view.findViewById(R.id.searchFortune) as Button
        val CONTENTS =
            arrayOf("白羊座", "金牛座", "双子座", "巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","魔羯座","水瓶座","双鱼座")
        var textView = view.findViewById(R.id.autoCompleteTextView1) as AutoCompleteTextView
        //创建一个ArrayAdapter适配器
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            activity!!,
            R.layout.support_simple_spinner_dropdown_item,
            CONTENTS
        )
        textView.setAdapter(adapter);

        return view;
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState)
        btnToday?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("WrongConstant")
            override fun onClick(v: View?) {
            // TODO Auto-generated method stub
            // Toast.makeText(activity, "success2",0).show()

                view?.let { GetFortuneData(
                    flag,
                    astroid,
                    date,
                    activity!!,
                    it
                ).getFortuneData() }
            }
        })

    btnWeek?.setOnClickListener(object : View.OnClickListener {
        @SuppressLint("WrongConstant")
        override fun onClick(v: View?) {
            // TODO Auto-generated method stub
            // Toast.makeText(activity, "success2",0).show()
            flag=2
            view?.let { GetFortuneData(
                flag,
                astroid,
                date,
                activity!!,
                it
            ).getFortuneData() }
        }
    })
        btnMonth?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("WrongConstant")
            override fun onClick(v: View?) {
                // TODO Auto-generated method stub
               // Toast.makeText(activity, "success2",0).show()
                flag=3
                view?.let { GetFortuneData(
                    flag,
                    astroid,
                    date,
                    activity!!,
                    it
                ).getFortuneData() }
            }
        })
    btnYear?.setOnClickListener(object : View.OnClickListener {
        @SuppressLint("WrongConstant")
        override fun onClick(v: View?) {
            // TODO Auto-generated method stub
            // Toast.makeText(activity, "success2",0).show()
            flag=4
            view?.let { GetFortuneData(
                flag,
                astroid,
                date,
                activity!!,
                it
            ).getFortuneData() }
        }
    })
    btnSearch?.setOnClickListener(object : View.OnClickListener {
        @SuppressLint("WrongConstant")
        override fun onClick(v: View?) {
            // TODO Auto-generated method stub
            // Toast.makeText(activity, "success2",0).show()
            val astro=autoCompleteTextView1.text.toString()
//            Toast.makeText(activity!!, autoCompleteTextView1.getText().toString(),Toast.LENGTH_SHORT).show();
            flag=1
            for(i in AstroData.instance.astroArray!!){
                if(i==astro){
                    println("i = astro!")
                    astroid= AstroData.instance.astroArray!!.indexOf(i)+1
                }
                println("AstroData.instance.astroArray[i]="+i);
            }
            println("Fragment astroid="+astroid)
            view?.let { GetFortuneData(
                flag,
                astroid,
                date,
                activity!!,
                it
            ).getFortuneData() }

        }
    })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWeb(webView1.settings)
        setWeb(webView2.settings)
        setWeb(webView3.settings)

        // 打开的网址
        webView1.loadUrl("file:///android_asset/sun.html")
        webView2.loadUrl("file:///android_asset/heatmap.html")
        webView3.loadUrl("file:///android_asset/LineChart.html")
    }

    fun setWeb(settings:WebSettings){
        settings.javaScriptEnabled=true
        //支持缩放
        settings.useWideViewPort=true   //设定支持viewport
        settings.loadWithOverviewMode=true
        settings.builtInZoomControls=true
        settings.setSupportZoom(true)   //设定支持缩放
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        month.setOnClickListener(this)
//
//    }
//    override fun onClick(view:View){
//      //  val date="2021-06-18"
//
//
//        when(view.id){
//            R.id.searchFortune->
//            {
//
//                GlobalScope.launch { // 在后台启动一个新协程，并继续执行之后的代码
//                    delay(1000L)
//                    // 延迟结束后打印
//                    val astro=autoCompleteTextView1.text.toString()
//                    astroid= GetAstroid(astro, activity!!,view).getAstroid()
//                    println("Fragment astroid="+astroid)
//
//                }
//                flag=2
//                GetFortuneData(flag,astroid,date,activity!!, view).getFortuneData()
//            }
//        }
//    }


}




