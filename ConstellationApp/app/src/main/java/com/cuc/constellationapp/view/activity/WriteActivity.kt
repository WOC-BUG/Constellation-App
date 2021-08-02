package com.cuc.constellationapp.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.DatabaseHelper
import com.cuc.constellationapp.dao.PostDao
import com.cuc.constellationapp.model.data.CurUser
import com.cuc.constellationapp.model.data.Post
import com.cuc.constellationapp.view.fragment.HomeFragment
import kotlinx.android.synthetic.main.announce.*
import java.text.SimpleDateFormat
import java.util.*

//发帖页面activity
class WriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.announce)

        val curHost = CurUser.instance  //获取当前用户
        val dbHelper : DatabaseHelper? = DatabaseHelper(this, "MyAppData.db", 1)

        //发送按钮点击监听
        btnSend.setOnClickListener {

            val content = inputBox2.text.toString()
            val resultIntent = Intent()

            if(content.isNotEmpty()){
                val note = Post()
                //获取当前时间
                val simpleDateFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm")
                val date = Date(System.currentTimeMillis())
                val time = simpleDateFormat.format(date)

                //获取当前用户的信息
                note.setHostName(curHost.getName())
                note.setHostAvatar(curHost.getAvatar())
                note.setCreateTime(time)
                note.setContentText(content)
                note.setPraiseNum(0)

                //将数据写入数据库
                PostDao(dbHelper).addPost(note.getHostName(),note.getHostAvatar(),note.getCreateTime(),note.getContentText(),note.getPraiseNum())
                Log.d("WriteActivity写数据库","success！")
                //更新个人主页我的动态
                HomeFragment.instance.setHistoryNum()
                HomeFragment.instance.setAdapter()

                resultIntent.putExtra("announce_post", note)
                setResult(Activity.RESULT_OK, resultIntent)

                Toast.makeText(this, "发帖成功", Toast.LENGTH_SHORT).show()
                //返回社区主页
                finish()

            }else{
                setResult(Activity.RESULT_CANCELED, resultIntent)

                Toast.makeText(this,"请输入内容！",Toast.LENGTH_SHORT).show()
            }

        }

        //返回社区主页点击监听
        val imageView = findViewById<ImageView>(R.id.returnView)
        imageView.setOnClickListener {
            finish()    //关闭当前页面，返回上一个activity
        }

    }
}