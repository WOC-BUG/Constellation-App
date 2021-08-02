package com.cuc.constellationapp.dao

import com.cuc.constellationapp.model.data.Test

class TestDao(var dbHelper : DatabaseHelper?) {
    //插入数据
    fun addTest(title:String, number:String, url:String){
        val db = dbHelper?.writableDatabase
        val addTest = "insert into Test values(null,'$title','$number','$url')"
        db?.execSQL(addTest)
    }

    //查询全部数据--返回测试数组
    fun queryTest(): ArrayList<Test>{
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from Test" ,null)
        val allTest = ArrayList<Test>()

        if(cursor?.moveToFirst()!!){
            do{
                val test = Test()
                test.setTitle(cursor.getString(cursor.getColumnIndex("title")))
                test.setNumber(cursor.getString(cursor.getColumnIndex("number")))
                test.setUrl(cursor.getString(cursor.getColumnIndex("url")))
                allTest.add(test)

            }while (cursor.moveToNext())
        }
        cursor.close()

        return allTest
    }

    //更新数据
    fun updateTest(){
        val db = dbHelper?.writableDatabase
    }

    //删除数据
    fun deleteTest(testID: Int){
        val db = dbHelper?.writableDatabase

        db?.execSQL("delete from Test " +
                "where id = $testID")
    }

    fun deleteAll(){
        val db = dbHelper?.writableDatabase
        db?.delete("Test",null,null)
    }
}