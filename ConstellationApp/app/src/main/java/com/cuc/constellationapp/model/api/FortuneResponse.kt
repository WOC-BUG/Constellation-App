package com.cuc.constellationapp.model.api

import com.cuc.constellationapp.model.data.Result

/**
 * 星座对应的运势数据
 */
data class FortuneResponse(
    val msg: String,
    val result: Result,
    val status: Int
)


//
//data class Month(
//    val career: String,
//    val date: String,
//    val love: String,
//    val money: String,
//    val summary: String
//)
//
//data class Today(
//    val career: String,
//    val color: String,
//    val date: String,
//    val health: String,
//    val love: String,
//    val money: String,
//    val number: String,
//    val presummary: String,
//    val star: String,
//    val summary: String
//)
//
//data class Tomorrow(
//    val career: String,
//    val color: String,
//    val date: String,
//    val health: String,
//    val love: String,
//    val money: String,
//    val number: String,
//    val presummary: String,
//    val star: String,
//    val summary: String
//)
//
//data class Week(
//    val career: String,
//    val date: String,
//    val health: String,
//    val job: String,
//    val love: String,
//    val money: String
//)
//
//data class Year(
//    val career: String,
//    val date: String,
//    val love: String,
//    val money: String,
//    val summary: String
//)