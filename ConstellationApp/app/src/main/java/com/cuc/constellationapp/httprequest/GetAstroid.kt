package com.cuc.constellationapp.httprequest

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.cuc.constellationapp.R
import com.cuc.constellationapp.model.api.AstroidApi
import com.cuc.constellationapp.model.api.AstroidResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetAstroid (var context: Context, view: View) {

    val calendar: TextView =view.findViewById(R.id.calendar)

    fun getAstroid(){



        //请求网络
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.jisuapi.com/astro/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AstroidApi::class.java)
        appService.getMessageByGet("5ecb70307e7e62bf")
            .enqueue(object : retrofit2.Callback<AstroidResponse> {

                override fun onResponse(
                    call: Call<AstroidResponse>,
                    response: Response<AstroidResponse>
                ) {
                    Log.d("Retrofit", response.body().toString())
                    val msg = response.body()

                    if (msg != null) {

//                            astroArray= arrayOf("${msg.result[0].astroname}","${msg.result[1].astroname}",
//                            "${msg.result[2].astroname}","${msg.result[3].astroname}","${msg.result[4].astroname}",
//                            "${msg.result[5].astroname}","${msg.result[6].astroname}","${msg.result[7].astroname}",
//                            "${msg.result[8].astroname}","${msg.result[9].astroname}","${msg.result[10].astroname}",
//                            "${msg.result[11].astroname}")

                        AstroData.instance.astroArray =arrayOf(
                            "${msg.result[0].astroname}",
                            "${msg.result[1].astroname}",
                            "${msg.result[2].astroname}",
                            "${msg.result[3].astroname}",
                            "${msg.result[4].astroname}",
                            "${msg.result[5].astroname}",
                            "${msg.result[6].astroname}",
                            "${msg.result[7].astroname}",
                            "${msg.result[8].astroname}",
                            "${msg.result[9].astroname}",
                            "${msg.result[10].astroname}",
                            "${msg.result[11].astroname}"
                        )
//



                        //在页面显示黄历信息

                    }

                }

                override fun onFailure(call: Call<AstroidResponse>, t: Throwable) {
                    //  TODO("Not yet implemented")
                    Log.d("getAstroid", "failure")
                    t.printStackTrace()
                }
            })


//        println("msg.result[11].astrotest="+AstroData.instance.astrotest)
//        return astroid
//        return AstroData.instance.astrotest
    }
}