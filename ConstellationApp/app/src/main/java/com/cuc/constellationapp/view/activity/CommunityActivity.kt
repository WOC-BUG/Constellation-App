package com.cuc.constellationapp.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.*
import com.cuc.constellationapp.model.data.Commentary
import com.cuc.constellationapp.model.data.CurUser
import com.cuc.constellationapp.model.data.Post
import com.cuc.constellationapp.view.adapter.CommentAdapter
import com.cuc.constellationapp.view.adapter.PostAdapter
import com.cuc.constellationapp.view.fragment.CommunityFragment
import com.cuc.constellationapp.view.fragment.HomeFragment
import kotlinx.android.synthetic.main.community_item.*
import kotlinx.android.synthetic.main.post_detail.*
import java.text.SimpleDateFormat
import java.util.*

//帖子详情页activity
class CommunityActivity : AppCompatActivity() {

    val curHost = CurUser.instance  //获取当前用户
    private var post :Post = Post()
    private var commentList = ArrayList<Commentary>()  //评论数组
    private var commentAdapter: CommentAdapter ?= null
    private val dbHelper : DatabaseHelper? = DatabaseHelper(this, "MyAppData.db", 1)
    var dataChanged = false   //记录该页面是否更新数据


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail)

        post = intent.getSerializableExtra("postItem") as Post  //获取传递过来的Post对象

        //展示帖子信息
        Glide.with(this).load(post.getHostAvatar()).error(R.mipmap.ic_launcher).into(tvImgAvatar)
        tvUserName.setText(post.getHostName())
        tvTime.setText(post.getCreateTime())
        tvContent.setText(post.getContentText())
        //判断是否已点赞
        val isMyLike: Boolean= post.getPostID() in PraiseDao(dbHelper).queryMyLike(curHost.getID())
        //判断是否关注该用户
        var isMyFollow = false
        val myFollow:ArrayList<Int> = FollowDao(dbHelper).queryMyFollow(curHost.getID())
        val postUser = UserDao(dbHelper).queryUser(post.getHostName())
        for(id in myFollow){
            if( id == postUser.getID()){
                isMyFollow = true
            }
        }

        if(isMyLike){
            imgLike.setImageResource(R.mipmap.like_after)
            numOfLike.setTextColor(ContextCompat.getColor(numOfLike.context,R.color.mainColor))
        } else{
            imgLike.setImageResource(R.mipmap.like_before)
            numOfLike.setTextColor(ContextCompat.getColor(numOfLike.context,R.color.deepGray))
        }
        if(post.getPraiseNum() !== 0){
            numOfLike.text = post.getPraiseNum().toString()
        }else{
            numOfLike.text = " "
        }
        if(postUser.getID() == curHost.getID()){  //若发帖人为当前用户，隐藏关注按钮
            btnFollow.visibility = View.GONE
        }else{
            if(isMyFollow){
                btnFollow.text = "√已关注"
            }else{
                btnFollow.text = "+关注"
            }
        }

        //加载评论区
        val layoutManager1= LinearLayoutManager(this)
        layoutManager1.orientation= LinearLayoutManager.VERTICAL    //垂直排列
        commentRecyclerView.layoutManager=layoutManager1
        //读数据库
        commentList = CommentDao(dbHelper).queryComment(post.getPostID())
        Log.d("CommunityActivity读数据库","success！")

        commentAdapter = CommentAdapter(commentList)
        commentRecyclerView.adapter= commentAdapter

        //评论 发送点击监听
        btnComment.setOnClickListener {
            //dataChanged = true

            val content = inputBox.text.toString()
            if(content.isNotEmpty()){
                //获取当前时间
                val simpleDateFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm")
                val date = Date(System.currentTimeMillis())
                val DateTime = simpleDateFormat.format(date)

                val cmn = Commentary()
                //获取当前用户的信息
                cmn.setHostName(curHost.getName())
                cmn.setHostAvatar(curHost.getAvatar())
                cmn.setCommentTime(DateTime)
                cmn.setCommentText(content)
                cmn.getCommentText()
                cmn.getCommentTime()
                cmn.getHostAvatar()
                cmn.getHostName()

                //将数据写入数据库
                CommentDao(dbHelper).addComment(post.getPostID(),cmn.getHostName(),cmn.getHostAvatar(),cmn.getCommentTime(),cmn.getCommentText())
                Log.d("CommunityActivity写数据库","success！")

                commentList.add(cmn)
                //当有新评论，刷新RecyclerView中的显示
                commentAdapter?.notifyItemInserted(commentList.size-1)
                //将RecyclerView定位到最后一行
                commentRecyclerView.scrollToPosition(commentList.size-1)
                Toast.makeText(this, "评论成功", Toast.LENGTH_SHORT).show()
                inputBox.setText("")  //清空输入框

            }else{
                Toast.makeText(this,"请输入评论内容！", Toast.LENGTH_SHORT).show()
            }
        }

        //关注监听
        btnFollow.setOnClickListener {
            dataChanged = true
            if(!isMyFollow){
                //关注+1
                FollowDao(dbHelper).addFollow(curHost.getID(),postUser.getID())  //第一个参数为当前用户ID
                btnFollow.text = "√已关注"

            }else{
                //关注-1
                FollowDao(dbHelper).deleteFollow(curHost.getID(),postUser.getID())  //第一个参数为当前用户ID
                btnFollow.text = "+关注"
            }

            //个人主页关注数可能发生变化
            HomeFragment.instance.setFocusNum()
        }

        //点赞监听
        imgLike.setOnClickListener {
            dataChanged = true
            if(!isMyLike){
                //更新数据库
                PostDao(dbHelper).updatePost("inc",post.getPostID())
                PraiseDao(dbHelper).addLike(curHost.getID(),post.getPostID())   //第一个参数为当前用户ID
                Log.d("点击更新Praise表数据","success！")

                //更新UI
                imgLike.setImageResource(R.mipmap.like_after)
                numOfLike.text = PostDao(dbHelper).queryOne(post.getPostID()).getPraiseNum().toString()
                numOfLike.setTextColor(ContextCompat.getColor(numOfLike.context,R.color.mainColor))

            }else{
                PostDao(dbHelper).updatePost("dec",post.getPostID())
                PraiseDao(dbHelper).deleteLike(curHost.getID(), post.getPostID())  //第一个参数为当前用户ID
                Log.d("点击更新Praise表数据","success！")

                //更新UI
                imgLike.setImageResource(R.mipmap.like_before)
                numOfLike.text = PostDao(dbHelper).queryOne(post.getPostID()).getPraiseNum().toString()
                numOfLike.setTextColor(ContextCompat.getColor(numOfLike.context,R.color.deepGray))
            }
        }

        //返回社区主页点击监听
        val imageView = findViewById<ImageView>(R.id.returnView1)
        imageView.setOnClickListener {

            if(dataChanged){

                //更新adapter，重新获取帖子数据
                CommunityFragment.instance.setAdapter()
                HomeFragment.instance.setAdapter()
            }
            finish()    //关闭当前页面，返回上一个activity
        }
    }

}