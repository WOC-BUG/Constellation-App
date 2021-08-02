package com.cuc.constellationapp.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cuc.constellationapp.model.data.BaseData
import com.cuc.constellationapp.view.holder.BaseViewHolder

/**
 * Adapter基类
 * @param list 数据列表，数据类必须继承自BaseData类
 */
abstract class BaseAdapter(private var list:List<BaseData>): RecyclerView.Adapter<BaseViewHolder>(){
    var resourceId:Int=0;

    /**
     * 设置要绑定的布局
     */
    fun setResource(id:Int){
        resourceId=id
    }

    override fun getItemCount(): Int = list.size
    /**
     * 监听每个item的点击事件
     * @param name 给要传递的数据起一个名字
     * @param data 要传递给Activity的数据（该数据必须继承自BaseData）
     * @param activity 要打开的Activity
     */
    class clickItem(var name:String,var data: BaseData,var activity:Activity) : View.OnClickListener {
        override fun onClick(v: View) {
            val intent = Intent(v.context, activity.javaClass)
            intent.putExtra(name, data)
            v.context.startActivity(intent)
        }
    }
}