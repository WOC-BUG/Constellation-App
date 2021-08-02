package com.cuc.constellationapp.httprequest

class AstroData {

    var astroArray:Array<String>?=null
//    var astroArray= arrayOf("a","b")
    companion object{
        val instance by lazy{
            AstroData()
        }

    }
}