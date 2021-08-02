package com.cuc.constellationapp.model.api

import com.cuc.constellationapp.model.data.BaseData

/**
 * API接口
 */
class Api: BaseData() {
    // 老黄历
    val almanac="http://api.tianapi.com/txapi/lunar/index"
    val apikey="943342bb497907a43eb8f89c960054c2"
    val almanacApi="$almanac?key=$apikey"

    // 星座运势
    val astro="https://api.jisuapi.com/astro/all"       // 根据日期查询星座和星座id
    val fortune="https://api.jisuapi.com/astro/fortune" // 根据星座id和日期查询运势
    var astroid="1"
    var date="2021-7-31"
    val appkey="7ef5d35dacca5f3c"
    var astroApi="$astro?appkey=$appkey"
    var fortuneApi="$fortune?astroid=$astroid&date=$date&appkey=$appkey"

    // 占卜
    var tarot_url="http://59.110.216.95/"
}