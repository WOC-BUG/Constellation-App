package com.cuc.constellationapp.dao

import android.util.Log
import com.cuc.constellationapp.model.data.Post

//Post表的增删改查类
class PostDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addPost(hostName:String, hostAvatar:String, tvTime:String, tvContent:String , numOfLike:Int){
        val db = dbHelper?.writableDatabase
        val addPost = "insert into Post values(null,'" + hostName + "','" + hostAvatar + "','" +
                tvTime + "','" + tvContent + "','" + numOfLike + "')"
        db?.execSQL(addPost)
        //Log.d("PostData insert","success")
    }

    //查询全部数据--返回帖子数组
    fun queryPost(): ArrayList<Post>{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Post " ,null)
        val allPost = ArrayList<Post>()

        if(cursor?.moveToFirst()!!){
            do{
                val post = Post()
                post.setPostID(cursor.getInt(cursor.getColumnIndex("id")))
                post.setHostName(cursor.getString(cursor.getColumnIndex("hostName")))
                post.setHostAvatar(cursor.getString(cursor.getColumnIndex("hostAvatar")))
                post.setCreateTime(cursor.getString(cursor.getColumnIndex("tvTime")))
                post.setContentText(cursor.getString(cursor.getColumnIndex("tvContent")))
                post.setPraiseNum(cursor.getInt(cursor.getColumnIndex("numOfLike")))

                allPost.add(post)

            }while (cursor.moveToNext())
        }
        cursor.close()

        return allPost
    }
    //查询某个帖子
    fun queryOne(id:Int):Post{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Post where id = $id" ,null)
        var post = Post()
        if(cursor?.moveToFirst()!!){
            do{
                post.setPostID(cursor.getInt(cursor.getColumnIndex("id")))
                post.setHostName(cursor.getString(cursor.getColumnIndex("hostName")))
                post.setHostAvatar(cursor.getString(cursor.getColumnIndex("hostAvatar")))
                post.setCreateTime(cursor.getString(cursor.getColumnIndex("tvTime")))
                post.setContentText(cursor.getString(cursor.getColumnIndex("tvContent")))
                post.setPraiseNum(cursor.getInt(cursor.getColumnIndex("numOfLike")))

            }while (cursor.moveToNext())
        }
        cursor.close()
        return post

    }

    //查询用户的历史帖子
    fun queryByName(hostName:String):ArrayList<Post>{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Post where hostName= '$hostName'" ,null)
        var myPosts = ArrayList<Post>()
        if(cursor?.moveToFirst()!!){
            do{
                val post = Post()
                post.setPostID(cursor.getInt(cursor.getColumnIndex("id")))
                post.setHostName(cursor.getString(cursor.getColumnIndex("hostName")))
                post.setHostAvatar(cursor.getString(cursor.getColumnIndex("hostAvatar")))
                post.setCreateTime(cursor.getString(cursor.getColumnIndex("tvTime")))
                post.setContentText(cursor.getString(cursor.getColumnIndex("tvContent")))
                post.setPraiseNum(cursor.getInt(cursor.getColumnIndex("numOfLike")))
                myPosts.add(post)

            }while (cursor.moveToNext())
        }
        cursor.close()
        return myPosts
    }


    //更新数据(更新点赞数)
    fun updatePost(op: String, postID: Int){
        val db = dbHelper?.writableDatabase
        val num = getPraisedNum(postID)

        //更新表中数据
        when(op){
            "inc" -> {
                db?.execSQL("update Post" +
                        " set numOfLike = ${num + 1} " +
                        "where id = $postID")
            }
            "dec" -> {
                db?.execSQL("update Post" +
                        " set numOfLike = ${num - 1} " +
                        "where id = $postID")
            }
        }
    }

    //获取某条帖子的点赞数
    fun getPraisedNum(postID: Int):Int{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Post " +
                "where id = $postID",null)
        var num = 0
        if(cursor?.moveToFirst()!!){
            do{
                num = cursor.getInt(cursor.getColumnIndex("numOfLike"))  //获取该帖子的点赞数

            }while (cursor.moveToNext())
        }
        cursor.close()
        return num
    }

    //删除数据
    fun deletePost(postID: Int){
        val db = dbHelper?.writableDatabase

        db?.execSQL("delete from Post " +
                "where id = $postID")

        //删除全部数据
        //db?.delete("Post",null,null)
    }

}