package com.yaya.baby.ui.activity;

import android.os.Bundle;

import com.yaya.baby.R;
import com.yaya.baby.ui.common.BaseActivity;
import com.yaya.baby.view.TopView;

import butterknife.BindView;

/**
 * 用户个人信息界面
 *
 * @author Darcy
 */
public class UserDataActivity extends BaseActivity {
    @BindView(R.id.topView)
    TopView topView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_data;
    }
}
