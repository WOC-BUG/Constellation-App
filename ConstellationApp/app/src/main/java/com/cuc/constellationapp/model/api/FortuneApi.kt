package com.cuc.constellationapp.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FortuneApi {
    @GET("fortune")
    fun getMessageByGet(@Query("astroid")astroid:Int,
                        @Query("date")date:String,
                        @Query("appkey")key:String): Call<FortuneResponse>
}