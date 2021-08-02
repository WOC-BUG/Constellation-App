package com.cuc.constellationapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.*
import com.cuc.constellationapp.model.data.CurUser
import com.cuc.constellationapp.model.data.Post
import com.cuc.constellationapp.model.data.User
import com.cuc.constellationapp.view.activity.CommunityActivity
import com.cuc.constellationapp.view.fragment.HomeFragment
import com.cuc.constellationapp.view.holder.BaseViewHolder
import com.cuc.constellationapp.view.holder.PostViewHolder
import kotlinx.android.synthetic.main.community_item.view.*


class PostAdapter(private val postList: ArrayList<Post>): BaseAdapter(postList){

    val curHost = CurUser.instance  //获取当前用户
    var dbHelper : DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        setResource(R.layout.community_item)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)

        dbHelper = DatabaseHelper(parent.context, "MyAppData.db", 1)

        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as PostViewHolder
        var post = postList[position]
        val postUser = UserDao(dbHelper).queryUser(post.getHostName())
        val likeNum = PostDao(dbHelper).queryOne(post.getPostID()).getPraiseNum()

        //判断是否已点赞
        var isMyLike:Boolean = hasPraised(post.getPostID())
        //判断是否已关注该用户
        var isMyFollow = hasFollowed(postUser)


        Glide.with(holder.itemView.context).load(post.getHostAvatar()).error(R.mipmap.ic_launcher).into(holder.postItem.tvImgAvatar);
        holder.postItem.tvUserName.text = post.getHostName()
        holder.postItem.tvTime.text = post.getCreateTime()
        holder.postItem.tvContent.text = post.getContentText()

        if(isMyLike){
            holder.likeImg.setImageResource(R.mipmap.like_after)
            holder.postItem.numOfLike.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.mainColor))
        } else{
            holder.likeImg.setImageResource(R.mipmap.like_before)
            holder.postItem.numOfLike.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.deepGray))
        }
        if(likeNum !== 0){
            holder.postItem.numOfLike.text = likeNum.toString()
        }else{
            holder.postItem.numOfLike.text = " "
        }
        if(postUser.getID() == curHost.getID()){  //若发帖人为当前用户，隐藏关注按钮
            holder.followBtn.visibility = View.GONE
        }else{
            if(isMyFollow){
                holder.followBtn.text = "√已关注"
            }else{
                holder.followBtn.text = "+关注"
            }
        }


        // item配置监听器
        holder.postItem.setOnClickListener(clickItem("postItem",post,CommunityActivity()))

        //评论点击监听
        holder.cmnImg.setOnClickListener(clickItem("postItem",post,CommunityActivity()))

        //关注点击监听
        holder.followBtn.setOnClickListener{

            if(!hasFollowed(postUser)){  //未关注该用户，关注+1
                FollowDao(dbHelper).addFollow(curHost.getID(),postUser.getID()) //第一个参数为当前用户ID

            }else{  //已关注，关注-1
                FollowDao(dbHelper).deleteFollow(curHost.getID(),postUser.getID())  //第一个参数为当前用户ID
            }
            //通知更新社区主页的item
            notifyItemChanged(position)

            //个人主页关注数可能发生变化
            HomeFragment.instance.setFocusNum()
        }

        //喜欢点击监听
        holder.likeImg.setOnClickListener{
            if(!hasPraised(post.getPostID())){   //未点赞，点赞数+1

                //更新数据库
                PostDao(dbHelper).updatePost("inc",postList[position].getPostID())
                PraiseDao(dbHelper).addLike(curHost.getID(),postList[position].getPostID())   //第一个参数为当前用户ID
                Log.d("点击更新Praise表数据","success！")

            }else{   //已点赞，点赞数-1

                PostDao(dbHelper).updatePost("dec",postList[position].getPostID())
                PraiseDao(dbHelper).deleteLike(curHost.getID(), postList[position].getPostID())  //第一个参数为当前用户ID
                Log.d("点击更新Praise表数据","success！")
            }
            //更新数据
            post.setPraiseNum(PostDao(dbHelper).queryOne(post.getPostID()).getPraiseNum())

            //个人主页帖子点赞数可能发生变化
            HomeFragment.instance.setAdapter()

            //通知更新社区主页的item
            notifyItemChanged(position)
        }

    }
    //判断是否已点赞
    fun hasPraised(postID:Int):Boolean{
        return postID in PraiseDao(dbHelper).queryMyLike(curHost.getID())  //当前用户ID
    }
    //判断是否已关注该用户
    fun hasFollowed(user:User):Boolean{
        var hasFollowed = false
        val myFollow:ArrayList<Int> = FollowDao(dbHelper).queryMyFollow(curHost.getID())  //当前用户ID
        for(id in myFollow){
            if( id == user.getID()){
                hasFollowed = true
            }
        }
        return hasFollowed
    }

}

