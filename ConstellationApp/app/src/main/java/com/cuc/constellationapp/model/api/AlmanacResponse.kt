package com.cuc.constellationapp.model.api

import com.cuc.constellationapp.model.api.AlmanacData

/**
 * 黄历数据
 */
class AlmanacResponse(

    var reason:String,
    var error_code:Int,
    var result: AlmanacData
)