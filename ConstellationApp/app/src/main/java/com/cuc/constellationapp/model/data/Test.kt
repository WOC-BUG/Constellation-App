package com.cuc.constellationapp.model.data

class Test:BaseData() {
    private lateinit var title :String  //测试题标题
    private lateinit var number:String  // 测评题目数量
    private lateinit var url:String  // 测试题地址

    // 赋值
    fun setTitle(title: String){
        this.title = title;
    }
    fun setNumber(number: String){
        this.number = number;
    }
    fun setUrl(url: String){
        this.url = url;
    }

    // 获取数据
    fun getTitle():String{
        return this.title
    }
    fun getNumber():String{
        return this.number
    }
    fun getUrl():String{
        return this.url
    }
}