package com.cuc.constellationapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.DatabaseHelper
import com.cuc.constellationapp.dao.TestDao
import com.cuc.constellationapp.model.data.Test
import com.cuc.constellationapp.view.activity.TestActivity
import com.cuc.constellationapp.view.holder.BaseViewHolder
import com.cuc.constellationapp.view.holder.TestViewHolder
import kotlinx.android.synthetic.main.test_item.view.*

class TestAdapter(private val testList : List<Test>) : BaseAdapter(testList) {

    var dbHelper : DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        setResource(R.layout.test_item)
        val view= LayoutInflater.from(parent.context).inflate(resourceId, parent,false)
        dbHelper = DatabaseHelper(parent.context, "MyAppData.db", 1)
        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder as TestViewHolder
        val test = testList[position]


        holder.testItem.title.text=test.getTitle()
        holder.testItem.type.text=test.getNumber()

        holder.testItem.setOnClickListener(clickItem("testData", test, TestActivity()))
    }
}