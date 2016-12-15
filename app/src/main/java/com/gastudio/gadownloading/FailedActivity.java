package com.gastudio.gadownloading;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.gastudio.gadownloading.ui.GADownloadingView;

/**
 * author: GAStudio
 * qq:1935400187
 * 技术交流qq群:277582728
 */
public class FailedActivity extends Activity {

    private static final int UPDATE_PROGRESS_DELAY = 500;

    private GADownloadingView mDownLoadLoadingView;
    private int mProgress;
    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mProgress += 10;
            if (mProgress >= 60) {
                mDownLoadLoadingView.onFail();
            }
            mDownLoadLoadingView.updateProgress(mProgress);
            mHandler.postDelayed(mRunnable, UPDATE_PROGRESS_DELAY);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgress = 0;
        mDownLoadLoadingView = ((GADownloadingView) findViewById(R.id.downloading));
        mDownLoadLoadingView.performAnimation();
        mDownLoadLoadingView.updateProgress(mProgress);
        mHandler.post(mRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }
}
