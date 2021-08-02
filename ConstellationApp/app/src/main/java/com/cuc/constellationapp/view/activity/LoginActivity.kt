package com.cuc.constellationapp.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.cuc.constellationapp.R
import com.cuc.constellationapp.view.fragment.CommunityFragment
import com.cuc.constellationapp.view.fragment.HomeFragment
import com.cuc.constellationapp.view.fragment.LoginFragment
import com.cuc.constellationapp.view.fragment.RegisterFragment
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity(){
    lateinit var loginLayout: LinearLayout  //findViewById<LinearLayout>(R.id.login_layout)
    lateinit var registerLayout: LinearLayout //findViewById<LinearLayout>(R.id.register_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_or_register)

        loginLayout=findViewById<LinearLayout>(R.id.login_layout)
        registerLayout=findViewById<LinearLayout>(R.id.register_layout)
        registerLayout.visibility=View.INVISIBLE
        loginLayout.visibility=View.VISIBLE
        // 显示登录页面
        replaceFragment(LoginFragment.instance)

        // 按钮监听
        val loginButton: Button =findViewById(R.id.login_btn)
        val toRegisterButton: Button=findViewById(R.id.to_register_btn)
        val registerButton: Button =findViewById(R.id.register_btn)
        val toLoginButton: Button=findViewById(R.id.to_login_btn)

        loginButton.setOnClickListener{
            if(LoginFragment.instance.checkInput(LoginFragment.instance.view)){
                //社区界面与个人主页加载数据
                CommunityFragment.instance.setAdapter()
                HomeFragment.instance.setAdapter()
                HomeFragment.instance.getUserInfo() // 初始化用户页面信息
                finish()
            }
        }
        registerButton.setOnClickListener{
            if(RegisterFragment.instance.checkInput(RegisterFragment.instance.view)){
                //社区界面与个人主页加载数据
                CommunityFragment.instance.setAdapter()
                HomeFragment.instance.setAdapter()
                HomeFragment.instance.getUserInfo() // 初始化用户页面信息
                finish()
            }
        }
        toRegisterButton.setOnClickListener{
            loginLayout.visibility=View.INVISIBLE
            registerLayout.visibility=View.VISIBLE
            replaceFragment(RegisterFragment.instance)
        }
        toLoginButton.setOnClickListener{
            registerLayout.visibility=View.INVISIBLE
            loginLayout.visibility=View.VISIBLE
            replaceFragment(LoginFragment.instance)
        }
    }

    /**
     * 切换Fragment
     */
    fun replaceFragment(fragment:Fragment){
        val fragmentManager= supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.frameLayout,fragment)  //rightLayout替换为fragment
//        transaction.addToBackStack(null)    //手动添加到栈中，这样回退时可返回上一个fragment，而不是关闭整个activity
        transaction.commit()   //留住Fragment的前后关系
    }
}