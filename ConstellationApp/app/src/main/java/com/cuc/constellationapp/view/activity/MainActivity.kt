package com.cuc.constellationapp.view.activity

import FragmentAdapter
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cuc.constellationapp.R
import com.cuc.constellationapp.dao.*
import com.cuc.constellationapp.view.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView //底部导航栏
    private lateinit var viewPager: ViewPager //中间切换页面
    private lateinit var menuItem: MenuItem  //选中的按钮
    private var listFragment : ArrayList<Fragment> = ArrayList()
    private var dbHelper : DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 先打开Login页面
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)

        //初始化控件
        bottomNavigationView=findViewById(R.id.bottomNavigationView)
        viewPager=findViewById(R.id.viewPager)

        // 将五个Fragment加入Fragment列表中
        listFragment.add(FortuneFragment.instance)    //添加运势Fragment
        listFragment.add(TestFragment.instance)    //添加测试Fragment
        listFragment.add(CommunityFragment.instance)    //添加社区Fragment
        listFragment.add(DivinationFragment.instance)    //添加占卜Fragment
        listFragment.add(HomeFragment.instance)    //添加个人Fragment

        bottomNavigationView.menu.getItem(0).isChecked = true;  //默认选中第一个页面

        // 设置监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationSelectedListener)
        viewPager.setOnPageChangeListener(viewPagerListener)

        //设置页面适配器
        viewPager.adapter=FragmentAdapter(supportFragmentManager,listFragment)
        viewPager.offscreenPageLimit = 5

        //创建数据库和表
        createDB()
        //插入测试数据
        addTestData()

    }

    //设置页面切换事件监听
    private val viewPagerListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                //让与当前Pager相应的item变为选中状态
                menuItem = bottomNavigationView.menu.getItem(position);
            }
        }


    //设置底部导航栏菜单监听
    private val bottomNavigationSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.fortune -> {
                        viewPager.currentItem = 0
                        return true
                    }
                    R.id.test -> {
                        viewPager.currentItem = 1
                        return true
                    }
                    R.id.community -> {
                        viewPager.currentItem = 2
                        return true
                    }
                    R.id.divination -> {
                        viewPager.currentItem = 3
                        return true
                    }
                    R.id.home -> {
                        viewPager.currentItem = 4
                        return true
                    }
                }
                return false
            }
        }

    //创建数据库及表
    fun createDB() {
        dbHelper = DatabaseHelper(this, "MyAppData.db", 1)
        dbHelper?.writableDatabase
    }
    //判断表是否为空
    fun isEmpty(tableNmae:String):Boolean{
        var empty = false
        val db = dbHelper?.writableDatabase
        val cursor = db?.rawQuery("select * from $tableNmae",null)
        if(cursor?.count == 0)
            empty = true

        return empty

    }
    //插入原始测试数据
    fun addTestData(){
        if(isEmpty("User")){
            val add_user = UserDao(dbHelper)

            add_user.addUser("Monster","666",this.getString(R.string.user1_img),"2000-05-20","金牛座")
            add_user.addUser("飞小猪","666",this.getString(R.string.user2_img),"2000-01-26","水瓶座")
            add_user.addUser("喜大狼","666",this.getString(R.string.user3_img),"2003-04-11","白羊座")
            add_user.addUser("Sunny","666",this.getString(R.string.user4_img),"2000-02-21","双鱼座")
            add_user.addUser("Luckylili","666",this.getString(R.string.user5_img),"2002-03-09","双鱼座")
            add_user.addUser("Healer","666",this.getString(R.string.user6_img),"2001-12-05","射手座")
            add_user.addUser("今天早睡了吗","666",this.getString(R.string.user7_img),"2000-06-17","双子座")
            add_user.addUser("甜瓜仔","666",this.getString(R.string.user8_img),"2002-01-08","摩羯座")

        }
        if(isEmpty("Post")){
            val add_post = PostDao(dbHelper)

            add_post.addPost("飞小猪",this.getString(R.string.user2_img),"2021-06-14 12:28","白羊男总是点赞我的朋友圈是怎么肥事??",3)
            add_post.addPost("Sunny",this.getString(R.string.user4_img),"2021-06-14 14:01","我减肥的时候，收藏从未停止，实践从未开始！",6)
            add_post.addPost("喜大狼",this.getString(R.string.user3_img),"2021-06-14 20:00","笑s我了,好开心啊hhhhhhh",2)
            add_post.addPost("Luckylili",this.getString(R.string.user5_img),"2021-06-15 09:05" ,"来自浙江杭州的双鱼座girl来报道啦",4)
            add_post.addPost("Healer",this.getString(R.string.user6_img), "2021-06-15 19:32","我的CP又发糖啦！！啊啊啊啊磕到了磕到了，再一次被甜到", 2)

            add_post.addPost("Luckylili",this.getString(R.string.user5_img),"2021-06-16 09:52","【双子月】祝双子座宝宝们生日快乐，本月最幸运的你，快来评论区许下生日愿望吧",6)
            add_post.addPost("今天早睡了吗",this.getString(R.string.user7_img),"2021-06-16 12:00","2021年第一次水逆……",1)
            add_post.addPost("甜瓜仔",this.getString(R.string.user8_img),"2021-06-17 15:07","清理自己的负能量，接下来一定好运连连！",3)
            add_post.addPost("Luckylili",this.getString(R.string.user5_img),"2021-06-17 08:22","每日打卡，我又来啦",0)
            add_post.addPost("喜大狼",this.getString(R.string.user3_img),"2021-06-17 09:00","精致睡眠，睡觉也太快乐了吧",2)
            add_post.addPost("Sunny",this.getString(R.string.user4_img),"2021-06-18 23:30","早睡早起，晚安仙女们，好梦",1)
            add_post.addPost("甜瓜仔",this.getString(R.string.user8_img),"2021-06-19 16:22","冒个泡",0)
            add_post.addPost("飞小猪",this.getString(R.string.user2_img),"2021-06-20 20::00","今天运势很不错",0)
        }
        if(isEmpty("Comment")){
            val add_comment = CommentDao(dbHelper)

            add_comment.addComment(1,"喜大狼",this.getString(R.string.user3_img),"2021-06-14 13:55","如果他平时也爱看朋友圈就是闲得慌hhhh")
            add_comment.addComment(2,"Healer",this.getString(R.string.user6_img),"2021-06-14 18:06","hhhh俺也是，隔空握个手")
            add_comment.addComment(2,"Healer",this.getString(R.string.user6_img),"2021-06-14 18:07","看过了就当自己做过了，咋办捏")
            add_comment.addComment(3,"Healer",this.getString(R.string.user6_img),"2021-06-14 19:46","鹅鹅鹅鹅鹅鹅")
            add_comment.addComment(1,"Luckylili",this.getString(R.string.user5_img),"2021-06-14 20:34","巧合?")
            add_comment.addComment(2,"飞小猪",this.getString(R.string.user2_img),"2021-06-14 21:42","管住嘴，迈开腿")
            add_comment.addComment(1,"Sunny",this.getString(R.string.user4_img),"2021-06-14 21:56","缘分来了就要抓住[看热闹]")
            add_comment.addComment(4,"Luckylili",this.getString(R.string.user5_img),"2021-06-15 10:00","有交友的么?")
            add_comment.addComment(3,"飞小猪",this.getString(R.string.user2_img),"2021-06-15 12:50","??????")
            add_comment.addComment(4,"Sunny",this.getString(R.string.user4_img),"2021-06-15 14:20","双鱼座+1")
            add_comment.addComment(4,"喜大狼",this.getString(R.string.user3_img),"2021-06-14 19:46","在？交个朋友？")
            add_comment.addComment(5,"Healer",this.getString(R.string.user6_img),"2021-06-15 19:55","我不行了呜呜，给我锁死")
            add_comment.addComment(6,"今天早睡了吗",this.getString(R.string.user7_img),"2021-06-16 13:55","好耶，许愿水逆快快过去，逢考必过")
            add_comment.addComment(8,"Luckylili",this.getString(R.string.user5_img),"2021-06-17 18:02","加油噢！")
            add_comment.addComment(11,"今天早睡了吗",this.getString(R.string.user7_img),"2021-06-19 01:13","真好啊，可惜我睡不着")

        }
        if(isEmpty("Praise")){
            val add_like = PraiseDao(dbHelper)

            add_like.addLike(6,1)
            add_like.addLike(3,2)
            add_like.addLike(2,1)
            add_like.addLike(6,2)
            add_like.addLike(6,3)
            add_like.addLike(2,2)
            add_like.addLike(4,1)
            add_like.addLike(4,2)
            add_like.addLike(2,3)
            add_like.addLike(5,2)
            add_like.addLike(6,4)
            add_like.addLike(3,5)
            add_like.addLike(3,4)
            add_like.addLike(4,4)
            add_like.addLike(6,5)
            add_like.addLike(2,4)
            add_like.addLike(2,6)
            add_like.addLike(3,6)
            add_like.addLike(4,6)
            add_like.addLike(7,6)
            add_like.addLike(6,6)
            add_like.addLike(1,6)
            add_like.addLike(3,8)
            add_like.addLike(7,7)
            add_like.addLike(7,8)
            add_like.addLike(5,8)
            add_like.addLike(5,10)
            add_like.addLike(7,11)
        }
        if(isEmpty("Test")) {
            val add_test = TestDao(dbHelper)
            add_test.addTest("恐惧情绪测试","共10题","https://www.apesk.com/xinliceshi/start_m/?1487.html")
            add_test.addTest("内在—外在心理控制源量表","共29题","https://www.apesk.com/xinliceshiNew/start_m/?15.html")
            add_test.addTest("时间管理能力", "共8题", "https://www.apesk.com/xinliceshi/start_m/?1490.html")
            add_test.addTest("抗挫折测试", "共10题", "https://www.apesk.com/xinliceshi/start_m/?1502.html")
            add_test.addTest("情绪健康测试","共30题","https://www.apesk.com/xinliceshi/start_m/?1488.html")
            add_test.addTest("PSTR心理压力测试", "共50题", "http://www.apesk.com/pressure/")
            add_test.addTest("你的犯罪倾向检测单","共5题","http://m.quce001.com/index.php/wetest/entry/index/id/9923##1624281106684")
            add_test.addTest("APESK瑞士荣格理论职业性格测试", "共28题", "https://www.apesk.com/mbti/dati28N.asp")
            add_test.addTest("16PersonalitiesType测试","共36题","http://www.apesk.com/16personalities/")
            add_test.addTest("职业测试", "共31题", "http://www.apesk.com/holland-m/")
            add_test.addTest("动物性格测试", "共36题", "http://www.apesk.com/animal/")
            add_test.addTest("羞怯量表", "共13题", "https://www.apesk.com/xinliceshi/start_m/?1447.html")
            add_test.addTest("精神卫生心理控制源量表","共28题","https://www.apesk.com/xinliceshi/start_m/?1420.html")
            add_test.addTest("你的键盘侠功力有几级","共10题","http://www.dhdyz.com/test/testinfo/cndjpxglyjj1553825476757?type=")
            add_test.addTest("灵魂温度进化图","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9522##1624281400684")
            add_test.addTest("你的人性检测单","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/10087##1624280661685")
            add_test.addTest("你体内有哪些未展现的洪荒之力","共10题","http://www.dhdyz.com/test/testinfo/cndtnhynxwzxdhhzl1553825579179?type=")
            add_test.addTest("你是哪种左右脑类型","共2题","http://m.quce001.com/index.php/wetest/entry/index/id/10002##1624280962191")
            add_test.addTest("正性情感、负性情感、情感平衡","共10题","https://www.apesk.com/xinliceshi/start_m/?1360.html")
            add_test.addTest("来世你的灵魂会去哪里","共10题","http://www.dhdyz.com/test/testinfo/cndtnhynxwzxdhhzl1553825579179?type=")
            add_test.addTest("你身边有多少伪朋友","共10题","http://www.dhdyz.com/test/testinfo/nsbydswpy?type=")
            add_test.addTest("你的心里年龄有多大","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9839##1624281219684")
            add_test.addTest("哪种痛苦你承受不起","共10题","http://www.dhdyz.com/test/testinfo/nztkncsbq?type=")
            add_test.addTest("你的六根清净吗","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9385##1624281518685")
            add_test.addTest("逆向思维大测试","共10题","http://www.dhdyz.com/test/testinfo/nxswdcs?type=")
            add_test.addTest("你是什么脾气","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9880##1624281165685")
            add_test.addTest("你的十关报告单","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9846##1624281190245")
            add_test.addTest("你的前世地位有多高","共5题","http://m.quce001.com/index.php/wetest/entry/index/id/9605##1624281350683")
            add_test.addTest("五个神奇数字，猜你潜在能力","共1题","http://www.dhdyz.com/test/testinfo/wgsqszcnqznl?type=")
            add_test.addTest("父母养育方式评价量表","共66题","https://www.apesk.com/xinliceshi/start_m/?1400.html")
            add_test.addTest("情商水平考试","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9260##1624281446640")
            add_test.addTest("家庭亲密度和适应性量表","共30题","https://www.apesk.com/xinliceshiNew/start_m/?1.html")
            add_test.addTest("你的动物性格分布图","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9596##1624281371607")
            add_test.addTest("是谁偷走了你的时间","共12题","http://www.dhdyz.com/test/testinfo/sstzlndsj?type=")
            add_test.addTest("你的人生能力评估单","共5题","http://m.quce001.com/index.php/wetest/entry/index/id/9727##1624281285291")
            add_test.addTest("诱惑面前，你还能淡定吗","共20题","http://www.dhdyz.com/test/testinfo/yhmqnhnddm?type=")
            add_test.addTest("个人评价问卷","共54题","https://www.apesk.com/xinliceshi/start_m/?1402.html")
            add_test.addTest("你会如何对待得罪过你的人","共10题","http://www.dhdyz.com/test/testinfo/nkbjqxddddzgndrm20210320101726?type=")
            add_test.addTest("你是不是助人型人格","共10题","http://www.dhdyz.com/test/testinfo/cnsbszrxrg202107231623?type=")
            add_test.addTest("自尊量表","共10题","https://www.apesk.com/xinliceshi/start_m/?1464.html")
            add_test.addTest("你像哪种小粽子","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/10139##1624280647684")
            add_test.addTest("你的七重气场检测单","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/10067##1624280749684")
            add_test.addTest("你有哪种天才基因","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/10017##1624280794386")
            add_test.addTest("你心中潜在的消极人格","共9题","http://www.dhdyz.com/test/testinfo/csnxzqzdxjrg1553482676626?type=")
            add_test.addTest("你的个性最像哪种酒","共3题","http://m.quce001.com/index.php/wetest/entry/index/id/10006##1624280819658")
            add_test.addTest("你的暴富潜质","共3题","http://m.quce001.com/index.php/wetest/entry/index/id/9629##1624280936685")
            add_test.addTest("总体幸福感量表","共33题","https://www.apesk.com/xinliceshi/start_m/?1466.html")
            add_test.addTest("灵魂估价单","共6题","http://m.quce001.com/index.php/wetest/entry/index/id/9790##1624280987684")
            add_test.addTest("别人眼中的你像哪种动物","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9982##1624281014601")
            add_test.addTest("高情商说话水平考试","共10题","http://m.quce001.com/index.php/wetest/entry/index/id/9948##1624281042475")
            add_test.addTest("你的内心有多冷淡","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9956##1624281075204")
            add_test.addTest("你的气味构成","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9903##1624281135518")
            add_test.addTest("你的人生需求检测单","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9824##1624281237683")
            add_test.addTest("你的内心住着什么动物","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9751##1624281266684")
            add_test.addTest("你的三观、三商检测报告","共4题","http://m.quce001.com/index.php/wetest/entry/index/id/9609##1624281327684")
            add_test.addTest("Russell吸烟原因问卷","共24题","https://www.apesk.com/xinliceshi/start_m/?1374.html")
        }


    }

}