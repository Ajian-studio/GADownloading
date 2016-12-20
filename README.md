## GADownloading
注: 我们说明文档分为两部分，第一部分为中文文档，第二部分为英文文档。

Note: The document is divided into two parts, the first part of the Chinese document, the second part of the English document.
### 一、创意原型：<br>

#### 原型效果图如下：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/loadingView_full_v.gif)  <br>

#### 实现效果图如下：<br>

成功状态：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/my_loading_view_success.gif) <br>

失败状态：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/my_loading_view_failed.gif) <br>
<br>
目前实现了原型中99%左右的内容，剩下的1%留给大家自行发挥，😄；<br>

注：<br>
1.整体实现均使用色值，未使用任何图片资源，核心色彩都已添加自定义属性；<br>
2.整体宽高自行定义，内部元素均根据整体宽高自动缩放适应，但由于整体效果限制，建议宽度不要低于100dp，否则效果不佳；<br>


## 二、如何使用:<br>
### 1 获取项目资源
获取项目资源的两种形式为：<br>
1).直接使用JitPack上的库； <br>
2).拷贝工程的的GADownloadingView及其他资源；<br>

#### 1.1.直接使用JitPack上的库

step 1 :在项目的build.gradle中加入如下代码：
```
allprojects {
		repositories {
			...
            // add the follow code
			maven { url 'https://jitpack.io' }
		}
	}
```
step 2 :在相应的模块的build.gradle中加入如下代码：
```
dependencies {
	compile 'com.github.Ajian-studio:GADownloading:v1.0.2'
}

```
#### 1.2.拷贝工程的中的GADownloadingView及相应的attr.xml文件
直接复制src/ui/GADownloadingView 及 res/values/attr.xml文件复制到相应的目录下

### 2.在布局文件中添加GADownloadingView:<br>

```
<xxx.xxx.xxxx.GADownloadingView
    android:id="@+id/ga_downloading"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_below="@+id/show_failed"
    android:layout_centerHorizontal="true" />
```

### 3.在Activity中找到组件：
```
mGADownloadingView = (GADownloadingView) findViewById(R.id.ga_downloading);
```
### 4.核心接口：

#### 4.1.performAnimation()：<br>
启动动画，包括背景和下载箭头抖动部分、背景镂空、圆变换为进度条、进度条抖动、下载箭头变换为承载文字的线框；<br>

#### 4.2.updateProgress(int progress)：<br>
更新进度；<br>

#### 4.3.onFail()：<br>
下载失败、调用则执行失败部分动效；<br>

### 5.自定义属性：<br>
#### 5.1 可更改属性 <br>
```
    <declare-styleable name="GADownloadingView">

        <attr name="arrow_color" format="color" />
        <attr name="loading_circle_back_color" format="color" />
        <attr name="loading_line_color" format="color" />
        <attr name="progress_line_color" format="color" />
        <attr name="progress_text_color" format="color" />
        <attr name="done_text_color" format="color" />
    </declare-styleable>

```

#### 5.2.自定义属性使用方式：<br>

添加自定义属性命名空间：<br>
```
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
         xmlns:gastudio="http://schemas.android.com/apk/res-auto"
         ... ...
    />
```
添加自定义属性<br>
```
    <com.gastudio.gadownloading.ui.GADownloadingView
        android:id="@+id/ga_downloading"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        gastudio:arrow_color="@android:color/white"
        gastudio:done_text_color="@android:color/white"
        gastudio:loading_circle_back_color="@android:color/white"
        gastudio:loading_line_color="@android:color/white"
        gastudio:progress_line_color="@android:color/white"
        gastudio:progress_text_color="@android:color/white" />
```

最后，如果你觉得还不错，欢迎Star！<br>

欢迎加入GAStudio交流qq群: 277582728 ；<br>


----
## GADownloading （English documentation）
### 1 Creative prototype：<br>

#### The effect is shown below：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/loadingView_full_v.gif)  <br>

#### Through the code to achieve the effect is as follows：<br>

State of success：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/my_loading_view_success.gif) <br>

State of fail：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/my_loading_view_failed.gif) <br>
<br>
We have achieved most of the results, the rest to you, ha ha，😄；<br>

Note：<br>
1.The project does not use any image resources, most of the colors can be changed by custom attributes；<br>
2.LoadingView will automatically adjust the width and height, the proposed width of not less than 100dp, otherwise ineffective；<br>


## 2 How to Use:<br>
### 2.1 How To Obtain Project Resources
The two forms of obtaining project resources are：<br>
1).Use libraries directly on JitPack；<br>
2).Copy the project's GADownloadingView and associated resources；<br>

#### 2.1.1 Use Libraries Directly On JitPack

step 1. In the project build.gradle add the following code：
```
allprojects {
		repositories {
			...
            // add the follow code
			maven { url 'https://jitpack.io' }
		}
	}
```
step 2. In the module's build.gradle add the following code：
```
dependencies {
	compile 'com.github.Ajian-studio:GADownloading:v1.0.2'
}

```
#### 2.1.2 Copy The Project's Resouces
Copy the src/ui/GADownloadingView and res/values/attr.xml files directly into the appropriate directory

### 2.2 Add GADownloadingView To The Layout File<br>

```
<xxx.xxx.xxxx.GADownloadingView
    android:id="@+id/ga_downloading"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_below="@+id/show_failed"
    android:layout_centerHorizontal="true" />
```

### 2.3 Find The View In The Activity
```
mGADownloadingView = (GADownloadingView) findViewById(R.id.ga_downloading);
```
### 2.4 Core Interface

#### 2.4.1 performAnimation() <br>
Start the animation;<br>

#### 2.4.2 updateProgress(int progress) <br>
Update progress；<br>

#### 2.4.3 onFail()：<br>
The download fails, and the execution fails with some animation；<br>

### 2.5 Custom Properties<br>
#### 2.5.1 You Can Change The Properties <br>
```
    <declare-styleable name="GADownloadingView">

        <attr name="arrow_color" format="color" />
        <attr name="loading_circle_back_color" format="color" />
        <attr name="loading_line_color" format="color" />
        <attr name="progress_line_color" format="color" />
        <attr name="progress_text_color" format="color" />
        <attr name="done_text_color" format="color" />
    </declare-styleable>

```

#### 2.5.2 How To Use Customize Properties<br>

Add a custom property namespace：<br>
```
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
         xmlns:gastudio="http://schemas.android.com/apk/res-auto"
         ... ...
    />
```
Add some custom properties<br>
```
    <com.gastudio.gadownloading.ui.GADownloadingView
        android:id="@+id/ga_downloading"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        gastudio:arrow_color="@android:color/white"
        gastudio:done_text_color="@android:color/white"
        gastudio:loading_circle_back_color="@android:color/white"
        gastudio:loading_line_color="@android:color/white"
        gastudio:progress_line_color="@android:color/white"
        gastudio:progress_text_color="@android:color/white" />
```

Finally, if you feel pretty good, please click the Star!<br>

Welcome to join the GAStudio exchange qq group: 277582728;<br>
