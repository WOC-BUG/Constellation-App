package com.cuc.constellationapp.dao

import com.cuc.constellationapp.model.data.Commentary

//Comment表的增删改查
class CommentDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addComment(postID:Int, hostName:String, hostAvatar:String, commTime:String, commContent:String){
        val db = dbHelper?.writableDatabase
        val addComment = "insert into Comment values(null,$postID,'$hostName','$hostAvatar','$commTime','$commContent')"
        db?.execSQL(addComment)
        //Log.d("CommentData insert","success")

    }

    //查询数据--返回评论数组
    fun queryComment(postID: Int):ArrayList<Commentary>{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Comment " +
                "where postID = $postID" ,null)
        val allComm= ArrayList<Commentary>()

        if(cursor?.moveToFirst()!!){
            do{
                val comment =Commentary()
                comment.setCommentID(cursor.getInt(cursor.getColumnIndex("id")))
                comment.setHostName(cursor.getString(cursor.getColumnIndex("hostName")))
                comment.setHostAvatar(cursor.getString(cursor.getColumnIndex("hostAvatar")))
                comment.setCommentTime(cursor.getString(cursor.getColumnIndex("commTime")))
                comment.setCommentText(cursor.getString(cursor.getColumnIndex("commContent")))

                allComm.add(comment)

            }while (cursor.moveToNext())
        }
        cursor.close()

        return allComm
    }

    //更新数据
    fun updateComment(){}

    //删除数据
    fun deleteComment(commentID:Int){
        val db = dbHelper?.writableDatabase

        db?.execSQL("delete from Comment " +
                "where id = $commentID")

        //删除全部数据
        //db?.delete("Comment",null,null)
    }
}