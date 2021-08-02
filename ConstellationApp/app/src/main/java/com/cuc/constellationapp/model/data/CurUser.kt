package com.cuc.constellationapp.model.data

import android.widget.Button

/**
 * 当前登录的用户数据
 */
class CurUser:BaseData() {

    private lateinit var button: Button  //按钮
    /**
     * 单例模式，使用方法:
     * CurUser.instance.方法()
     */
    companion object{
        val instance by lazy {
            CurUser()
        }
    }

    var id:Int = 0
    var userName:String = ""
    var userPassword:String=""
    var userAvatar:String = ""
    var birthday:String=""
    var astroId:String?=""  // 星座ID
    var astroName:String ="" // 星座

    fun setID(id:Int){
        this.id = id
    }
    fun setName(name:String){
        this.userName = name
    }
    fun setPassword(password:String){
        this.userPassword=password
    }
    fun setAvatar(img:String){
        this.userAvatar = img
    }
    fun setBirthDay(bir:String){
        this.birthday = bir
    }
    fun setAstro(astro:String){
        this.astroName = astro
    }

    fun getID():Int{
        return  this.id
    }
    fun getName():String{
        return  this.userName
    }
    fun getPassword():String{
        return this.userPassword
    }
    fun getAvatar():String{
        return this.userAvatar
    }
    fun getBirthDay():String{
        return this.birthday
    }
    fun getAstro():String{
        return this.astroName
    }
}