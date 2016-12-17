## GADownloading

### 创意原型：

#### 原型效果图如下：

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/loadingView_full_v.gif)  

#### 实现效果图如下：

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/my_loading_view_success.gif) 

![](https://github.com/Ajian-studio/GADownloading/raw/master/raw/my_loading_view_failed.gif) 

目前实现了原型中99%左右的内容；

### 如何使用:

#### 1.在布局文件中添加LeafLoding:

<com.gastudio.gadownloading.ui.GADownloadingView
    android:id="@+id/ga_downloading"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_below="@+id/show_failed"
    android:layout_centerHorizontal="true"
    android:layout_marginTop="15dp" />

#### 2.在Activity中找到组件：

mGADownloadingView = (GADownloadingView) findViewById(R.id.ga_downloading);

#### 核心接口：

1.performAnimation();
执行动画，包括背景和下载箭头抖动部分、背景镂空、圆变换为进度条、进度条抖动、下载箭头变换为承载文字的线框；
2.updateProgress(int progress)；
更新进度；
3.onFail()；
下载失败、调用则执行失败部分动效；


