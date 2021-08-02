package com.cuc.constellationapp.httprequest

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.cuc.constellationapp.R
import com.cuc.constellationapp.model.api.AlmanacApi
import com.cuc.constellationapp.model.api.AlmanacResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetAlmanacData (val date:String,var context:Context,view: View){

    val calendar: TextView =view.findViewById(R.id.calendar)
    val wuxing:TextView =view.findViewById(R.id.wuxing)
    val chongsha:TextView =view.findViewById(R.id.chongsha)
    val baiji:TextView =view.findViewById(R.id.baiji)
    val jishen:TextView =view.findViewById(R.id.jishen)
    val xiongshen:TextView =view.findViewById(R.id.xiongshen)
    val yi:TextView =view.findViewById(R.id.yi)
    val ji:TextView =view.findViewById(R.id.ji)

    fun getAlmanacData(){

        //请求网络
        val retrofit = Retrofit.Builder()
            .baseUrl("http://v.juhe.cn/laohuangli/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AlmanacApi::class.java)
        appService.getMessageByGet(date, "76a2574ba4ce0313ca7882a8ecf4cd61")
            .enqueue(object : retrofit2.Callback<AlmanacResponse> {

                override fun onResponse(call: Call<AlmanacResponse>, response: Response<AlmanacResponse>) {
                    Log.d("Retrofit", response.body().toString())
                    val msg = response.body()
                    if (msg != null) {

                        //在页面显示黄历信息
                        calendar.text=date+"  "+"${msg.result.yinli}"
                        wuxing.text="${msg.result.wuxing}"
                        chongsha.text="${msg.result.chongsha}"
                        baiji.text="${msg.result.baiji}"
                        jishen.text="${msg.result.jishen}"
                        xiongshen.text="${msg.result.xiongshen}"
                        yi.text="${msg.result.yi}"
                        ji.text="${msg.result.ji}"



                    }

                }
                override fun onFailure(call: Call<AlmanacResponse>, t: Throwable) {
                    //  TODO("Not yet implemented")
                    Log.d("almanac", "failure")
                    t.printStackTrace()
                }
            })

    }
}