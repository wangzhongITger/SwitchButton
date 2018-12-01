# SwitchButton
可以竖直或者横向开关的SwitchButton

使用：
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.wangzhongITger:SwitchButton:1.0.0'
	}

	
	
![image](https://github.com/wangzhongITger/SwitchButton/blob/master/device-2018-12-01-233108.png)

相关属性

属性 | 作用 | 提示
---- | ---- | ------
initStatus | 初始化开关所处状态（打开/关闭） | 默认为未开启状态
switchBgColor | 开关未打开状态的背景颜色
switchOnTextColor | 开启字体的颜色 | 
switchOffTextColor | 关闭字体的颜色
switchOnText | 开启按钮显示的文字 | 如果开启显示文字，默认显示为开启
switchOffText | 关闭按钮显示的文字 | 如果开启显示文字，默认显示为关闭
switchTextSize | 显示字体的大小
switchOnFlapColor | 开启按钮被打开的背景颜色
switchOffFlapColor | 关闭按钮被打开的背景颜色
switchMode | 按钮显示方向（horizontal：横向；vertical：竖直） | 默认为竖直状态
switchCorners | 按钮圆角大小
switchStrokeColor | 按钮边框颜色
switchStrokeWidth | 按钮边框宽度 | 如不需要显示边框可以设为0dp
isShowText | 是否显示文字 | 默认不显示文字



相关方法

方法 | 作用
---- | ----
setSwitchStatusStatusListener(SwitchStatusStatusListener listener) | 设置开关状态的监听
getSwitchStatus() | 获取当前开关的状态
setSwitchStatus(boolean isOn) | 设置当前开关的状态
setSwitchAble(boolean enable) | 设置当前开关是否可控

