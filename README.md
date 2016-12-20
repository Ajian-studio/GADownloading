## GADownloading 
## [中文文档](https://github.com/Ajian-studio/GADownloading/blob/master/README_CN.md)
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

## License
    Copyright 2016 GAStudio

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
