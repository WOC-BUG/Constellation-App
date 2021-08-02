package com.cuc.constellationapp.view.fragment
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.DatabaseHelper
import com.cuc.constellationapp.dao.PostDao
import com.cuc.constellationapp.model.data.Post
import com.cuc.constellationapp.view.activity.WriteActivity
import com.cuc.constellationapp.view.adapter.PostAdapter
import kotlinx.android.synthetic.main.community_main.*
import java.util.*


//社区主页fragment
class CommunityFragment: Fragment() {

    /**
     * 单例模式，使用方法:
     * CommunityFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            CommunityFragment()
        }
    }

    //创建Fragment的布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.community_main, null, false)
        return view
    }

    private var dbHelper : DatabaseHelper? = null
    private var postList = ArrayList<Post>()  //帖子数组
    private var postAdapter: PostAdapter?= null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager= LinearLayoutManager(context)
        layoutManager.orientation= LinearLayoutManager.VERTICAL    //垂直排列
        itemsRecyclerView.layoutManager=layoutManager

        dbHelper = this.activity?.let { DatabaseHelper(it, "MyAppData.db", 1) }
        //setAdapter()
//        postList = PostDao(dbHelper).queryPost()
//        Log.d("CommunityFragment读数据库","success！")
//        postAdapter = PostAdapter(postList)
//        itemsRecyclerView.adapter= postAdapter


        //发布announceView绑定点击跳转事件
        announceView.setOnClickListener {
            val intent = Intent()
            intent.setClass(announceView.context,WriteActivity::class.java)

            startActivityForResult(intent, 1)
        }
    }

    /**
     * 此方法用来接收跳转的activity要传回的数据，以及接受到传回数据的要做的业务
     * 这里因为只有一个activity返回数据，所以这里就只有一个resultCode，就直接接收返回值处理
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            val newPost = data?.getSerializableExtra("announce_post") as Post  //接收从WriteActivity传过来的Post对象
            postList.add(newPost)
            postAdapter?.notifyItemInserted(postList.size - 1)
            //定位到最后一行（最新发布的帖子）
            itemsRecyclerView.scrollToPosition(postList.size - 1)

        }
    }

    fun setAdapter(){
        postList = PostDao(dbHelper).queryPost()
        Log.d("CommunityFragment读数据库","success！")
        postAdapter = PostAdapter(postList)
        itemsRecyclerView.adapter= postAdapter
    }

}