# ConstellationApp

## 介绍
软件工程结课作业-Android App开发-星座App

* App分为五个页面：星座运势页、心理测试页面、社区页面、占卜页面、个人主页
* 星座运势：可以查看黄历，以及日、周、月、年的运势
* 心理测试：包括情绪健康测试、心理压力测试、人格测试等
* 社区页面：可以发帖、点赞、转发
* 占卜页面：可以抽卡进行占卜
* 个人主页：显示个人信息和档案



---


## 编码规范

**类： 大驼峰命名法**

```kotlin
class DemoFragment{
    // TODO
}
```

**控件id、变量、对象、函数：小驼峰命名法**

```Kotlin
android:id="@+id/demoId"
var number:Number = 5
var demoObject:DemoFragment = DemoFragment()
fun demoFunction(){
    // TODO 
}
```

**样式文件：全小写+下划线**

```kotlin
home_main.xml
```

**常量：全大写+下划线**

```
val name="CONST_VALUE"
```

**格式规范：**

快捷键`ctrl+alt+L`规范代码



---

## app颜色规范

* 主色调：`#b385ff`

* 深灰色：`#bbbbbb`

* 浅灰色：`#ededed`

  

---

## app文字规范

### 1. 大小

* 标题字号：`23sp`
* 正文字号：`21sp`
* 副文字号：`14sp-19sp`
* 最小字号不得低于`10sp`

**字号单位请一律使用`sp`，不要使用`px`**

### 2. 字体

* Android默认字体

  

**请将字号大小、颜色等配置同一添加到`res/values/stypes.xml`和`res/values/colors.xml`文件中，以便于统一修改**



---

## 软件架构
**主要文件结构：**

```bash
app
|__ assets	# echarts模块
|__ java
|     |__ com.cuc.constellationapp
|            |__ utils		# 基础工具层，可以放运算、字符串检查等工具
|            |__ dao	    # dao层，实现数据库交互
|            |__ model		# 放数据
|            |  |__ api	# API数据
|            |  |__ data	# 类图设计的类
|            |__ httprequest  # 接口请求
|            |__ view		# 视图
|               |__ activity
|               |__ fragment
|               |__ adapter	# 绑定数据
|               |__ holder	# 获取RecycleView的item项
|__ res
     |__ layout	# 布局文件
     |__ drawable	# 图片
     |__ menu	# 导航栏多选项
     |__ values	# 所有字号、颜色、文字
```




## 参与贡献

|        | 荆薇                 | 王淋             | 周桥                 | 冀佳璐            |
| ------ | -------------------- | ---------------- | -------------------- | ----------------- |
| 阶段一 | 写项目框架           | 社区页面布局文件 | 星座运势页面布局文件 | 个人主页布局文件  |
| 阶段二 | 占卜页面动画制作     | 社区页面功能     | 星座运势功能         | 个人主页功能      |
| 阶段三 | Cocos2dx动画嵌入安卓 | 社区页面功能     | 星座运势功能         | 编写项目说明文档  |
| 阶段四 | 数据库               | 数据库           | echarts可视化模块    | echarts可视化模块 |
| 阶段五 | 补充及测试           | 补充及测试       | 补充及测试           | 补充说明文档、ppt |
