## GADownloading

### 一、创意原型：<br>

#### 原型效果图如下：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/loadingView_full_v.gif)  <br>

#### 实现效果图如下：<br>

成功状态：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/my_loading_view_success.gif) <br>
<br>

失败状态：<br>

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/my_loading_view_failed.gif) <br>
<br>
目前实现了原型中99%左右的内容，剩下的1%留给大家自行发挥，😄；<br>

注：<br>
1.整体实现均使用色值，未使用任何图片资源，核心色彩都已添加自定义属性；<br>
2.整体宽高自行定义，内部元素均根据整体宽高自动缩放适应，但由于整体效果限制，建议宽度不要低于100dp，否则效果不佳；<br>

<br>
## 二、如何使用:<br>

#### 1.在布局文件中添加GADownloadingView:<br>

```
<com.gastudio.gadownloading.ui.GADownloadingView
    android:id="@+id/ga_downloading"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_below="@+id/show_failed"
    android:layout_centerHorizontal="true"
    android:layout_marginTop="15dp" />
```

#### 2.在Activity中找到组件：

mGADownloadingView = (GADownloadingView) findViewById(R.id.ga_downloading);

#### 3.核心接口：

1.performAnimation()：<br>
启动动画，包括背景和下载箭头抖动部分、背景镂空、圆变换为进度条、进度条抖动、下载箭头变换为承载文字的线框；<br>

2.updateProgress(int progress)：<br>
更新进度；<br>

3.onFail()：<br>
下载失败、调用则执行失败部分动效；<br>

4.自定义属性：<br>
```
    <declare-styleable name="GADownloadingView">

        <attr name="arrow_color" format="color" />
        <attr name="loading_circle_back_color" format="color" />
        <attr name="loading_line_color" format="color" />
        <attr name="progress_line_color" format="color" />
        <attr name="progress_text_color" format="color" />
        <attr name="done_text_color" format="color" />
    </declare-styleable>

```<br>

5.自定义属性使用方式：<br>

添加自定义属性命名空间：<br>
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:gastudio="http://schemas.android.com/apk/res-auto"
    ... ...
    />
```<br>
添加自定义属性<br>
```
    <com.gastudio.gadownloading.ui.GADownloadingView
        android:id="@+id/ga_downloading"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@+id/show_failed"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="15dp"
        gastudio:arrow_color="@android:color/white"
        gastudio:done_text_color="@android:color/white"
        gastudio:loading_circle_back_color="@android:color/white"
        gastudio:loading_line_color="@android:color/white"
        gastudio:progress_line_color="@android:color/white"
        gastudio:progress_text_color="@android:color/white" />
```
