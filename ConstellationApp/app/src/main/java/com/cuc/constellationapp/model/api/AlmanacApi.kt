package com.cuc.constellationapp.model.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//黄历api接口
interface AlmanacApi {
    @GET("d")
    fun getMessageByGet(@Query("date")date:String,
                        @Query("key")key:String): Call<AlmanacResponse>
}