package com.cuc.constellationapp.view.holder

import android.media.Image
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.cuc.constellationapp.R

class PostViewHolder(view: View): BaseViewHolder(view) {
    val postItem : LinearLayout = view.findViewById(R.id.postItemLayout)
    val followBtn: Button = view.findViewById(R.id.btnFollow)
    val likeImg : ImageView = view.findViewById(R.id.imgLike)
    val cmnImg : ImageView = view.findViewById(R.id.imgComment)
}