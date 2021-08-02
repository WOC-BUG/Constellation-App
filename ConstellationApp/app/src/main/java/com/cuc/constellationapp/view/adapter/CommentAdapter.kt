package com.cuc.constellationapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cuc.constellationapp.R
import com.cuc.constellationapp.model.data.Commentary
import com.cuc.constellationapp.view.holder.BaseViewHolder
import com.cuc.constellationapp.view.holder.CommentViewHolder
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentAdapter(private val commentList : List<Commentary>) : BaseAdapter(commentList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        setResource(R.layout.comment_item)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as CommentViewHolder
        val comment = commentList[position]

        Glide.with(holder.itemView.context).load(comment.getHostAvatar()).error(R.mipmap.ic_launcher).into(holder.commentItem.imgAvatar);
        holder.commentItem.userName.text = comment.getHostName()
        holder.commentItem.commentTime.text = comment.getCommentTime()
        holder.commentItem.commentContent.text = comment.getCommentText()

    }
}