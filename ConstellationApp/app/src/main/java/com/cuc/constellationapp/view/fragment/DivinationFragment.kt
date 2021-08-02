package com.cuc.constellationapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.cuc.constellationapp.R
import com.cuc.constellationapp.view.activity.TarotActivity
/**
 * 塔罗Fragment
 */
class DivinationFragment: Fragment() {
    private lateinit var button: ImageButton  //按钮
    /**
     * 单例模式，使用方法:
     * DivinationFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            DivinationFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.tarot_fragment, null, false)
        button = view.findViewById(R.id.start_tarot_btn)
        button.setOnClickListener(ClickButton())
        return view
    }

    private class ClickButton : View.OnClickListener {
        override fun onClick(v: View) {
            val intent = Intent(v.context, TarotActivity::class.java)
            v.context.startActivity(intent)
        }
    }
}