package com.cuc.constellationapp.model.data

class History:BaseData() {
    private lateinit var userName :String;  //用户昵称
    private lateinit var userAvatar :String;    //用户头像
    private lateinit var userConstellation :String;    //用户星座
    private lateinit var userFortune :String;   //用户运势
    private lateinit var historyUserName :String;//历史记录中的昵称
    private lateinit var historyUserConstellation :String;//历史记录中的星座


//赋值
    fun SetUserName(userName:String){
    this.userName=userName;
}
    fun GetUserName(): String{
        return this.userName
    }
    fun SetUserAvatar(userAvatar:String){
        this.userAvatar=userAvatar;
    }
    fun GetUserAvatar():String{
        return this.userAvatar
    }
    fun SetUserFortune(userFortune:String){
        this.userFortune=userFortune
    }
    fun GetUserFortune():String{
        return this.userFortune
    }
    fun SetUserConstellation(userConstellation:String){
        this.userConstellation=userConstellation
    }
    fun GetUserConstellation():String{
        return this.userConstellation
    }
    fun SetHistoryUserName(historyUserName:String){
        this.historyUserName=historyUserName
    }
    fun GetHistoryUserName():String{
        return this.historyUserName
    }
    fun SetHistoryUserContellation(historyUserConstellation:String){
        this.historyUserConstellation=historyUserConstellation
    }
    fun GetHistoryUserConstellation():String{
        return this.historyUserConstellation
    }
}