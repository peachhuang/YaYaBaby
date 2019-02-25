package com.yaya.baby.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.yaya.baby.R;
import com.yaya.baby.ui.common.BaseActivity;

/**
 * app的启动页
 *
 * @author Darcy
 * @Date 2018-11-07
 */
public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.postDelayed(runnable, 2000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            skipPage(LoginActivity.class);
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }
}
