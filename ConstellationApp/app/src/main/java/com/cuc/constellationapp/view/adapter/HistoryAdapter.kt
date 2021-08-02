package com.cuc.constellationapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.DatabaseHelper
import com.cuc.constellationapp.dao.PostDao
import com.cuc.constellationapp.dao.PraiseDao
import com.cuc.constellationapp.model.data.CurUser
import com.cuc.constellationapp.model.data.Post
import com.cuc.constellationapp.view.activity.CommunityActivity
import com.cuc.constellationapp.view.fragment.CommunityFragment
import com.cuc.constellationapp.view.fragment.HomeFragment
import com.cuc.constellationapp.view.holder.BaseViewHolder
import com.cuc.constellationapp.view.holder.HistoryViewHolder
import kotlinx.android.synthetic.main.history_post_item.view.*


class HistoryAdapter(private val historyList: ArrayList<Post>):BaseAdapter(historyList) {

    val curHost = CurUser.instance  //获取当前用户
    var dbHelper : DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        setResource(R.layout.history_post_item)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        dbHelper = DatabaseHelper(parent.context, "MyAppData.db", 1)
        return HistoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as HistoryViewHolder
        val history = historyList[position]

        var likeNum = PostDao(dbHelper).queryOne(history.getPostID()).getPraiseNum()

        //判断是否已点赞
        var isMyLike:Boolean = hasPraised(history.getPostID())

        Glide.with(holder.itemView.context).load(history.getHostAvatar()).error(R.mipmap.ic_launcher).into(holder.historyItem.tvImgAvatar);
        holder.historyItem.tvUserName.text = history.getHostName()
        holder.historyItem.tvTime.text = history.getCreateTime()
        holder.historyItem.tvContent.text = history.getContentText()
        if(isMyLike){
            holder.historyItem.imgLike.setImageResource(R.mipmap.like_after)
            holder.historyItem.numOfLike.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.mainColor))
        } else{
            holder.historyItem.imgLike.setImageResource(R.mipmap.like_before)
            holder.historyItem.numOfLike.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.deepGray))
        }
        if(likeNum !== 0){
            holder.historyItem.numOfLike.text = likeNum.toString()
        }else{
            holder.historyItem.numOfLike.text = " "
        }

        // item配置监听器
        holder.historyItem.setOnClickListener(clickItem("postItem",history, CommunityActivity()))
        //删除点击监听
        holder.historyItem.btnDelete.setOnClickListener {
            PostDao(dbHelper).deletePost(history.getPostID())
            HomeFragment.instance.setHistoryNum()
            Toast.makeText(holder.itemView.context, "删除成功", Toast.LENGTH_SHORT).show()
            //更新adapter
            HomeFragment.instance.setAdapter()
            CommunityFragment.instance.setAdapter()

            //通知更新item视图
            notifyItemRemoved(position)
        }
        //评论点击监听
        holder.historyItem.setOnClickListener(clickItem("postItem",history,CommunityActivity()))
        //喜欢点击监听
        holder.historyItem.imgLike.setOnClickListener{
            if(!hasPraised(history.getPostID())){   //未点赞，点赞数+1

                //更新数据库
                PostDao(dbHelper).updatePost("inc",historyList[position].getPostID())
                PraiseDao(dbHelper).addLike(curHost.getID(),historyList[position].getPostID())   //第一个参数为当前用户ID

            }else{   //已点赞，点赞数-1

                PostDao(dbHelper).updatePost("dec",historyList[position].getPostID())
                PraiseDao(dbHelper).deleteLike(curHost.getID(), historyList[position].getPostID())  //第一个参数为当前用户ID
            }
            //更新数据
            history.setPraiseNum(PostDao(dbHelper).queryOne(history.getPostID()).getPraiseNum())

            CommunityFragment.instance.setAdapter()

            //通知更新item视图
            notifyItemChanged(position)
        }

    }

    //判断是否已点赞
    fun hasPraised(postID:Int):Boolean{
        return postID in PraiseDao(dbHelper).queryMyLike(curHost.getID())  //当前用户ID
    }
}