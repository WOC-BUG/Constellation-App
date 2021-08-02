package com.cuc.constellationapp.model.data

//帖子item类
class Post: BaseData() {
    private var postID: Int = 0
    private lateinit var hostName :String  //用户昵称
    private lateinit var hostAvatar :String    //用户头像
    private lateinit var createTime :String    //发布时间
    private lateinit var contentText :String   //帖子内容
    private var praiseNum :Int = 0    //点赞数
    private var hasFollowed = 0   //是否关注帖主
    private var hasPraised = 0  //是否点赞帖子

    //赋值
    fun setPostID(id: Int){
        this.postID = id
    }
    fun setHostName(hostName: String){
        this.hostName = hostName
    }
    fun setHostAvatar(hostAvatar: String){
        this.hostAvatar = hostAvatar
    }
    fun setCreateTime(createTime: String){
        this.createTime = createTime
    }
    fun setContentText(contentText: String){
        this.contentText = contentText
    }
    fun setPraiseNum(praiseNum: Int){
        this.praiseNum = praiseNum
    }
    fun setHasFollowed(hasFollow:Int){
        this.hasFollowed = hasFollow
    }
    fun setHasPraised(hasPraise:Int){
        this.hasPraised = hasPraise
    }

    //获取数据
    fun getPostID():Int{
        return postID
    }
    fun getHostName() :String{
        return this.hostName
    }
    fun getHostAvatar(): String{
        return this.hostAvatar
    }
    fun getCreateTime(): String{
        return this.createTime
    }
    fun getContentText(): String{
        return this.contentText;
    }
    fun getPraiseNum(): Int{
        return this.praiseNum;
    }
    fun getHasFollowed():Int{
        return this.hasFollowed
    }
    fun getHasPraised():Int{
        return this.hasPraised
    }

}