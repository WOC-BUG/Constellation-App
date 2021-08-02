package com.cuc.constellationapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.DatabaseHelper
import com.cuc.constellationapp.dao.PostDao
import com.cuc.constellationapp.dao.TestDao
import com.cuc.constellationapp.model.data.Test
import com.cuc.constellationapp.view.adapter.TestAdapter
import kotlinx.android.synthetic.main.test_fragment.*
import java.util.ArrayList

class TestFragment: Fragment() {

    /**
     * 单例模式，使用方法:
     * CommunityFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            TestFragment()
        }
    }

    //创建Fragment的布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.test_fragment, null, false)
    }

    private var testList = ArrayList<Test>()  //测试题数组
    private var testAdapter: TestAdapter?= null
    var name="";
    private var dbHelper : DatabaseHelper? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager= LinearLayoutManager(context)
        layoutManager.orientation= LinearLayoutManager.VERTICAL    //垂直排列
        testRecyclerView.layoutManager=layoutManager
//        testList = getTests()

        dbHelper = this.activity?.let { DatabaseHelper(it, "MyAppData.db", 1) }
        testList=TestDao(dbHelper).queryTest()

        testAdapter = TestAdapter(testList)
        testRecyclerView.adapter= testAdapter
    }

    //获取帖子数据
    private fun getTests(): ArrayList<Test> {
        val testList= ArrayList<Test>()
        for(i in 1..10){
            val test = Test()
            test.setTitle("羞怯量表")
            test.setNumber("共13题")
            test.setUrl("https://www.apesk.com/xinliceshi/start_m/?1447.html")
            testList.add(test)
        }
        return testList
    }
}