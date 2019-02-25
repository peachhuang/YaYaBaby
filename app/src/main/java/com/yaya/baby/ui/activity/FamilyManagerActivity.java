package com.yaya.baby.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yaya.baby.R;
import com.yaya.baby.ui.adapter.FamilyManagerAdapter;
import com.yaya.baby.ui.common.BaseActivity;
import com.yaya.baby.view.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 家庭成员管理
 */
public class FamilyManagerActivity extends BaseActivity {
    @BindView(R.id.topView)
    TopView topView;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;


    private FamilyManagerAdapter adapter;

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        topView.setTitle("家庭成员管理");
        initData();

        initRecycleView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_manager;
    }

    @OnClick({R.id.btn_invitation_family})
    public void onClick(View view) {
        switch (view.getId()) {

            //跳转邀请亲友绑定界面
            case R.id.btn_invitation_family:
                skipPage(FamilyCareActivity.class);
                break;
        }
    }


    //测试用数据源
    private void initData() {
        for (int i = 0; i < 5; i++) {
            list.add("成员" + i);
        }
    }

    private void initRecycleView() {
        GridLayoutManager manager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(manager);
        adapter = new FamilyManagerAdapter(list, context);
        recyclerView.setAdapter(adapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
