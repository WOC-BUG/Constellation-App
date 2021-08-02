package com.cuc.constellationapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.DatabaseHelper
import com.cuc.constellationapp.dao.UserDao
import com.cuc.constellationapp.model.data.CurUser
import kotlinx.android.synthetic.main.personal_main.*
import java.util.*
import java.util.regex.Pattern

class RegisterFragment: Fragment() {
    private var dbHelper : DatabaseHelper? = null
    /**
     * 单例模式，使用方法:
     * RegisterFragment.instance.方法()
     */
    companion object{
        val instance by lazy {
            RegisterFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = this.activity?.let { DatabaseHelper(it, "MyAppData.db", 1) }
        return inflater.inflate(R.layout.register, null, false)
    }

    fun checkInput(view:View?):Boolean{
        if(view==null){
            print("view为空")
            return false
        }
        val userName:String=view.findViewById<EditText>(R.id.name).text.toString()
        val userPassword=view.findViewById<EditText>(R.id.password).text.toString()
        val userConfirmPassword=view.findViewById<EditText>(R.id.confirmPassword).text.toString()
        val userBirthday=view.findViewById<EditText>(R.id.birthday).text.toString()
        val user= UserDao(dbHelper).queryUser(userName)

        if(userName==""||userPassword==""||userConfirmPassword==""||userBirthday==""){
            Toast.makeText(view.context,"输入不能为空！", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(user.getName()!=""){
            Toast.makeText(view.context,"该用户已存在！", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(userPassword!=userConfirmPassword){
            Toast.makeText(view.context,"密码不一致！", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(!checkBirthday(userBirthday)){
            Toast.makeText(view.context,"生日输入出错！", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            val star=ifInRange(userBirthday)
            val avatar=getRandomAvatar()
            println("星座为：$star")

            UserDao(dbHelper).addUser(userName,userPassword,avatar,userBirthday,star)

            CurUser.instance.id=UserDao(dbHelper).queryUser(userName).id
            CurUser.instance.userName=userName
            CurUser.instance.userPassword=userPassword
            CurUser.instance.userAvatar=avatar
            CurUser.instance.birthday=userBirthday

            Toast.makeText(view.context,"注册成功！", Toast.LENGTH_SHORT).show()
            return true;
        }
    }

    private fun getRandomAvatar():String{
        val num:Int=(1..6).random()
        val resourceName="user"+num.toString()+"_img"
        val packageName= this.context?.packageName
        val url=this.getString(resources.getIdentifier(resourceName,"string",packageName))
        println("packageName=$packageName")
        println(url)
        return url
    }

    private fun ifInRange(birthday:String):String{
        val month:Int=birthday.substring(5,7).toInt()
        val day:Int=birthday.substring(8,10).toInt()
        var astroId:String=""
        var astroName:String=""
        if(month==3&&day>=21 || month==4&&day<=19){
            astroId="1"
            astroName="白羊座"
        }
        else if(month==4&&day>=20 || month==5&&day<=20){
            astroId="2"
            astroName="金牛座"
        }
        else if(month==5&&day>=21 || month==6&&day<=21){
            astroId="3"
            astroName="双子座"
        }
        else if(month==6&&day>=22 || month==7&&day<=22){
            astroId="4"
            astroName="巨蟹座"
        }
        else if(month==7&&day>=23 || month==8&&day<=22){
            astroId="5"
            astroName="狮子座"
        }
        else if(month==8&&day>=23 || month==9&&day<=22){
            astroId="6"
            astroName="处女座"
        }
        else if(month==9&&day>=23 || month==10&&day<=23){
            astroId="7"
            astroName="天秤座"
        }
        else if(month==10&&day>=24 || month==11&&day<=22){
            astroId="8"
            astroName="天蝎座"
        }
        else if(month==11&&day>=23 || month==12&&day<=21){
            astroId="9"
            astroName="射手座"
        }
        else if(month==12&&day>=22 || month==1&&day<=19){
            astroId="10"
            astroName="摩羯座"
        }
        else if(month==1&&day>=20 || month==2&&day<=18){
            astroId="11"
            astroName="水瓶座"
        }
        else if(month==2&&day>=19 || month==3&&day<=20){
            astroId="12"
            astroName="双鱼座"
        }
        CurUser.instance.astroId=astroId
        CurUser.instance.astroName=astroName
        return astroName
    }

    /**
     * 判断生日是否输入正确
     */
    fun checkBirthday(birthday: String): Boolean {
        println("生日：$birthday")
        if(birthday.length!=10 || birthday[4]!='-' || birthday[7]!='-'){
            println("格式不对")
            return false
        }
        else if(!ifNumber(birthday.substring(0,4)) || !ifNumber(birthday.substring(5,7)) || !ifNumber(birthday.substring(8,10))){
            println("特殊字符")
            return false
        }
        else{
            val year:Int=birthday.substring(0,4).toInt()
            val month:Int=birthday.substring(5,7).toInt()
            val day:Int=birthday.substring(8,10).toInt()
            if(year<1900 || year>2020 || month>12 || day>31){
                println("年月日输入不对")
                return false
            }

            // 2月
            if(year%4==0 && year%100!=0 || year%400==0){    // 闰年2月
                if(month==2&&day>29){
                    println("闰年2月日期不对")
                    return false
                }
            }
            else if(month==2&&day>28){  //非闰年2月
                println("非闰年2月日期不对")
                return false
            }

            // 奇偶月份
            if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){ //奇数月
                if(day>31){
                    println("奇数月日期不对")
                    return false
                }
            }
            else if(month==4||month==6||month==9||month==11){   //偶数月
                if(day>30){
                    println("偶数月日期不对")
                    return false
                }
            }
        }
        return true
    }

    /**
     * 判断字符串是否全为整数
     */
    fun ifNumber(str:String):Boolean{
        for(i in str){
            if(!i.isDigit()){
                return false
            }
        }
        return true
    }
}