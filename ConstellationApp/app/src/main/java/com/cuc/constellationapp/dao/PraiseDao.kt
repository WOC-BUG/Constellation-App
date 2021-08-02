package com.cuc.constellationapp.dao

import com.cuc.constellationapp.model.data.Post

//Praise表的增删改查
class PraiseDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addLike(userID:Int, postID:Int){
        val db = dbHelper?.writableDatabase
        val addLike = "insert into Praise values(null, $userID, $postID)"
        db?.execSQL(addLike)

    }

    //查询数据--返回我点赞过的帖子id
    fun queryMyLike(myID:Int):ArrayList<Int>{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Praise " +
                "where userID = $myID" ,null)
        val allLike = ArrayList<Int>()

        if(cursor?.moveToFirst()!!){
            do{
                allLike.add(cursor.getInt(cursor.getColumnIndex("postID")))

            }while (cursor.moveToNext())
        }
        cursor.close()

        return allLike
    }

    //更新数据
    fun updateLike(){}

    //删除数据
    fun deleteLike(userID:Int, postID:Int){
        val db = dbHelper?.writableDatabase

        db?.execSQL("delete from Praise " +
                "where userID = $userID and postID = $postID")

        //删除全部数据
        //db?.delete("Praise",null,null)
    }
}