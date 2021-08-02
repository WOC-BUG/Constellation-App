package com.cuc.constellationapp.dao

class FollowDao(var dbHelper : DatabaseHelper?) {

    //插入数据
    fun addFollow(hostID:Int, followedUserID:Int){
        val db = dbHelper?.writableDatabase
        val addFollow = "insert into Follow values(null, $hostID, $followedUserID)"
        db?.execSQL(addFollow)
    }
    //查询数据--返回我关注的id
    fun queryMyFollow(myID:Int):ArrayList<Int>{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Follow " +
                "where hostID = $myID" ,null)
        val allFollow = ArrayList<Int>()

        if(cursor?.moveToFirst()!!){
            do{
                allFollow.add(cursor.getInt(cursor.getColumnIndex("followedUserID")))

            }while (cursor.moveToNext())
        }
        cursor.close()

        return allFollow
    }
    fun queryMyFans(myID:Int):ArrayList<Int>{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Follow " +
                "where followedUserID = $myID" ,null)
        val allFans = ArrayList<Int>()

        if(cursor?.moveToFirst()!!){
            do{
                allFans.add(cursor.getInt(cursor.getColumnIndex("hostID")))

            }while (cursor.moveToNext())
        }
        cursor.close()

        return allFans

    }
    //更新数据
    fun update(){}

    //删除数据
    fun deleteFollow(hostID:Int, followedID:Int){
        val db = dbHelper?.writableDatabase

        db?.execSQL("delete from Follow " +
                "where hostID = $hostID and followedUserID = $followedID")

        //删除全部数据
        //db?.delete("Follow",null,null)
    }

}