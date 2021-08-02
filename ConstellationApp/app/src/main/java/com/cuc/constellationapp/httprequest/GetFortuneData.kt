package com.cuc.constellationapp.httprequest

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.cuc.constellationapp.R
import com.cuc.constellationapp.model.api.FortuneApi
import com.cuc.constellationapp.model.api.FortuneResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetFortuneData (var flag:Int,var astroid:Int,val date:String, var context: Context, view: View){
    //请求网络

    val showFortune:TextView=view.findViewById(R.id.showFortune)


    fun getFortuneData() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.jisuapi.com/astro/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(FortuneApi::class.java)
        appService.getMessageByGet(astroid,date, "5ecb70307e7e62bf")
            .enqueue(object : retrofit2.Callback<FortuneResponse> {

                override fun onResponse(
                    call: Call<FortuneResponse>,
                    response: Response<FortuneResponse>
                ) {
                    Log.d("Retrofit", response.body().toString())
                    val msg = response.body()
                    var astroname=" "
                    var presummary=""
                    var star=""
                    var color=""
                    var number=""
                    var health=""
                    var money=""
                    var career=""
                    var love=""
                    var job=""
                    var summary=""
                    if (msg != null) {
                        //在textview显示不同的运势
                        if(flag==1){//显示今日运势
                            astroname="${msg.result.astroname}"
                            presummary="${msg.result.today.presummary}"
                            star="${msg.result.today.star}"
                            color="${msg.result.today.color}"
                            number="${msg.result.today.number}"
                            health="${msg.result.today.health}"
                            showFortune.text=astroname+","+presummary+" 贵人星座："+star+" 幸运颜色："+
                                    color+" 幸运数字："+number+" 健康指数："+health
                        }
                        if(flag==2){//显示本周运势

                            money="${msg.result.week.money}"
                            career="${msg.result.week.career}"
                            love="${msg.result.week.love}"
                            health="${msg.result.week.health}"
                            job="${msg.result.week.job}"
                            showFortune.text=money+career+love+health+job
                        }

                        if(flag==3) {//显示本月运势
                            summary="${msg.result.month.summary}"
                            career="${msg.result.month.career}"
                            love="${msg.result.month.love}"
                            health="${msg.result.month.health}"

                            showFortune.text =summary+career+love+health
                        }
                        if(flag==4){//显示今年运势
                            summary="${msg.result.year.summary}"
                            career="${msg.result.year.career}"
                            love="${msg.result.year.love}"
                            showFortune.text = summary+career+love
                        }

                    }

                }

                override fun onFailure(call: Call<FortuneResponse>, t: Throwable) {
                    //  TODO("Not yet implemented")
                    Log.d("almanac", "failure")
                    t.printStackTrace()
                }
            })
    }
}