package com.cuc.constellationapp.model.data

//评论item类
class Commentary:BaseData() {
    private var commentID: Int = 0
    private lateinit var hostName :String  //用户昵称
    private lateinit var hostAvatar :String    //用户头像
    private lateinit var commentText :String   //评论内容
    private lateinit var commentTime :String   //评论时间

    //赋值
    fun setCommentID(id:Int){
        this.commentID = id
    }
    fun setHostName(hostName:String){
        this.hostName = hostName
    }
    fun setHostAvatar(hostAvatar: String){
        this.hostAvatar = hostAvatar
    }
    fun setCommentText(commentText: String){
        this.commentText = commentText
    }
    fun setCommentTime(commentTime: String){
        this.commentTime = commentTime
    }

    //获取数据
    fun getCommentID():Int{
        return this.commentID
    }
    fun getHostName(): String{
        return this.hostName
    }
    fun getHostAvatar():String{
        return this.hostAvatar
    }
    fun getCommentText():String{
        return this.commentText
    }
    fun getCommentTime():String{
        return this.commentTime
    }
}