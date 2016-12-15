package com.gastudio.gadownloading;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.gastudio.gadownloading.ui.GADownloadingView;

/**
 * author: GAStudio
 * qq:1935400187
 * 技术交流qq群:277582728
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mProgress += 10;
            if (!isSuccess && mProgress >= 60) {
                mGADownloadingView.onFail();
            }
            mGADownloadingView.updateProgress(mProgress);
            mHandler.postDelayed(mRunnable, UPDATE_PROGRESS_DELAY);
        }
    };

    private static final int UPDATE_PROGRESS_DELAY = 500;

    private GADownloadingView mGADownloadingView;
    private View mShowSuccess, mShowFailed;
    private int mProgress;
    private Handler mHandler = new Handler();
    private boolean isSuccess = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


    }

    private void initViews() {
        mGADownloadingView = (GADownloadingView) findViewById(R.id.ga_downloading);
        mShowSuccess = findViewById(R.id.show_success);
        mShowSuccess.setOnClickListener(this);
        mShowFailed = findViewById(R.id.show_failed);
        mShowFailed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mShowSuccess == v) {

            mGADownloadingView.releaseAnimation();
            mHandler.removeCallbacks(mRunnable);

            isSuccess = true;
            mProgress = 0;
            mGADownloadingView.performAnimation();
            mGADownloadingView.updateProgress(0);
            mHandler.postDelayed(mRunnable, 0);
        } else if (mShowFailed == v) {

            mGADownloadingView.releaseAnimation();
            mHandler.removeCallbacks(mRunnable);

            isSuccess = false;
            mProgress = 0;
            mGADownloadingView.performAnimation();
            mGADownloadingView.updateProgress(0);
            mHandler.postDelayed(mRunnable, 0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);

    }
}
