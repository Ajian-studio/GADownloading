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
目前实现了原型中99%左右的内容，剩下的1%留给大家自行发挥，😄；

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

1.performAnimation();<br>
启动动画，包括背景和下载箭头抖动部分、背景镂空、圆变换为进度条、进度条抖动、下载箭头变换为承载文字的线框；<br>

2.updateProgress(int progress)；<br>
更新进度；<br>

3.onFail()；<br>
下载失败、调用则执行失败部分动效；<br>


