package com.yaya.baby.ui.activity;

import android.os.Bundle;

import com.yaya.baby.R;
import com.yaya.baby.ui.common.BaseActivity;
import com.yaya.baby.view.TopView;

import butterknife.BindView;

/**
 * 家庭成员显示的界面
 *
 * @author Darcy
 *
 * @date 2018-11-09
 */
public class FamilyMemberActivity extends BaseActivity {
    @BindView(R.id.topView)
    TopView topView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topView.setTitle("家庭成员管理");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_member;
    }
}
