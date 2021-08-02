package com.cuc.constellationapp.dao

import android.util.Log
import com.cuc.constellationapp.model.data.User

//User表的增删改查
class UserDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addUser(hostName:String, password:String, hostAvatar:String, birthday:String, astro:String){
        val db = dbHelper?.writableDatabase
        val addUser = "insert into User values(null,'$hostName','$password','$hostAvatar','$birthday','$astro')"
        db?.execSQL(addUser)

    }

    //根据name查询用户
    fun queryUser(userName:String): User {
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from User " +
                "where hostName = '$userName'" ,null)

        var user = User()
        if(cursor?.moveToFirst()!!){
            do{
                user.setID(cursor.getInt(cursor.getColumnIndex("id")))
                user.setName(cursor.getString(cursor.getColumnIndex("hostName")))
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")))
                user.setAvatar(cursor.getString(cursor.getColumnIndex("hostAvatar")))
                user.setBirthDay(cursor.getString(cursor.getColumnIndex("birthday")))
                user.setAstro(cursor.getString(cursor.getColumnIndex("astro")))

            }while (cursor.moveToNext())
        }
        cursor.close()

        return user
    }

    //更新数据
    fun updateUser(){}

    //删除数据
    fun deleteUser(userID:Int){
        val db = dbHelper?.writableDatabase

        db?.execSQL("delete from User " +
                "where id = $userID")

        //删除全部数据
        //db?.delete("User",null,null)
    }
}