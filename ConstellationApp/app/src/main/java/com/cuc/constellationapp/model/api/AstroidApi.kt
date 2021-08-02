package com.cuc.constellationapp.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AstroidApi {
    @GET("all")
    fun getMessageByGet(@Query("appkey")key:String): Call<AstroidResponse>

}