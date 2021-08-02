package com.cuc.constellationapp.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(val context: Context, name:String, version:Int):         //第二个参数为数据库名
    SQLiteOpenHelper(context,name,null,version) {           //参数三：查询数据用的Cursor，参数四：版本号

    //用户表
    private val createUser = "create table if not exists User (" +
            "id integer primary key autoincrement," +
            "hostName text unique," +
            "password text,"+
            "hostAvatar text," +
            "birthday text,"+
            "astro text)"
    //帖子表
    private val createPost = "create table if not exists Post (" +
            "id integer primary key autoincrement," +
            "hostName text," +
            "hostAvatar text," +
            "tvTime text,"+
            "tvContent text," +
            "numOfLike integer)"
    //评论表
    private val createComment = "create table if not exists Comment (" +
            "id integer primary key autoincrement," +
            "postID integer,"+
            "hostName text," +
            "hostAvatar text," +
            "commTime text,"+
            "commContent text)"

    //点赞表
    private val createLike = "create table if not exists Praise (" +
            "id integer primary key autoincrement," +
            "userID integer," +
            "postID integer)"

    //关注表
    private val createFollow = "create table if not exists Follow (" +
            "id integer primary key autoincrement," +
            "hostID integer," +
            "followedUserID integer)"

    // 心理测试表
    private val createTest = "create table if not exists Test (" +
            "id integer primary key autoincrement," +
            "title text,"+
            "number text,"+
            "url text)"

    //创建数据库
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createUser)
        db.execSQL(createPost)
        db.execSQL(createComment)
        db.execSQL(createLike)
        db.execSQL(createFollow)
        db.execSQL(createTest)
        println("createTest成功！")
        //Toast.makeText(context,"Create successed!", Toast.LENGTH_SHORT).show()
        Log.d("DatabaseHelper","onCreate succeessed!")
    }
    //数据库升级
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}