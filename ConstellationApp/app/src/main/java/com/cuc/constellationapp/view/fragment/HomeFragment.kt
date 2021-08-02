package com.cuc.constellationapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.DatabaseHelper
import com.cuc.constellationapp.dao.FollowDao
import com.cuc.constellationapp.dao.PostDao
import com.cuc.constellationapp.dao.UserDao
import com.cuc.constellationapp.model.data.CurUser
import com.cuc.constellationapp.model.data.Post
import com.cuc.constellationapp.view.adapter.HistoryAdapter
import kotlinx.android.synthetic.main.personal_main.*
import java.util.ArrayList

class HomeFragment:Fragment() {

    /**
     * 单例模式，使用方法:
     * HomeFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            HomeFragment()
        }
    }
    //创建fragment的布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.personal_main,null, false)
        return view
    }

    val curHost = CurUser.instance  //获取当前用户
    private var dbHelper : DatabaseHelper? = null
    private var historyList = ArrayList<Post>()  //历史帖子数组
    private var historyAdapter: HistoryAdapter?= null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

         val layoutManager=LinearLayoutManager(context)
        layoutManager.orientation =LinearLayoutManager.VERTICAL//垂直排列
        personalRecyclerView.layoutManager=layoutManager

        dbHelper = this.activity?.let { DatabaseHelper(it, "MyAppData.db", 1) }

//        var numOfFans = FollowDao(dbHelper).queryMyFans(1).size  //粉丝数
//        var numOfFocus = FollowDao(dbHelper).queryMyFollow(1).size   //关注人数
        setFansNum()
        setFocusNum()
        setHistoryNum()

        //setAdapter()
        //获取我发的帖子
//        historyList = PostDao(dbHelper).queryByName(curHost.getName())   //当前用户名
//        Log.d("HomeFragment读数据库","success！")
//        historyAdapter = HistoryAdapter(historyList)
//        personalRecyclerView.adapter= historyAdapter
    }

    /**
     * 初始化当前登录的用户信息
     */
    fun getUserInfo(){
        userName.text = curHost.getName()
        constellation.text = UserDao(dbHelper).queryUser(curHost.getName()).getAstro()
        Glide.with(this.context).load(curHost.getAvatar()).error(R.mipmap.ic_launcher).into(head_portrait)

        setFansNum()
        setFocusNum()
        setHistoryNum()
    }

    //设置adapte，获取数据
    fun setAdapter(){
        historyList = PostDao(dbHelper).queryByName(curHost.getName())  //当前用户名
        Log.d("HomeFragment读数据库","success！")
        historyAdapter = HistoryAdapter(historyList)
        personalRecyclerView.adapter= historyAdapter
    }
    //展示粉丝数
    fun setFansNum(){
        fansNum.text = FollowDao(dbHelper).queryMyFans(curHost.getID()).size.toString()  //当前用户id
    }
    //展示关注人数
    fun setFocusNum(){
        focusNum.text = FollowDao(dbHelper).queryMyFollow(curHost.getID()).size.toString()   //当前用户id
    }
    //展示历史帖子数
    fun setHistoryNum(){
        val num = PostDao(dbHelper).queryByName(curHost.getName()).size  //当前用户名
        historyNum.text = "("+ num.toString() + ")"
    }

}

