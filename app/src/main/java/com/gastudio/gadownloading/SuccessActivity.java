package com.gastudio.gadownloading;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.gastudio.gadownloading.ui.GADownloadingView;

public class SuccessActivity extends Activity {

    private static final int UPDATE_PROGRESS_DELAY = 500;

    private GADownloadingView mDownLoadLoadingView;
    private int mProgress;
    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mProgress += 10;
            mDownLoadLoadingView.updateProgress(mProgress);
            mHandler.postDelayed(mRunnable, UPDATE_PROGRESS_DELAY);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDownLoadLoadingView = ((GADownloadingView) findViewById(R.id.downloading));
        mDownLoadLoadingView.performAnimation();
        mDownLoadLoadingView.updateProgress(0);
        mProgress = 0;
        mHandler.postDelayed(mRunnable, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }
}
