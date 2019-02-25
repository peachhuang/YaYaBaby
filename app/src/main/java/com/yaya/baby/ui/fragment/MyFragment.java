package com.yaya.baby.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaya.baby.R;
import com.yaya.baby.ui.activity.BindTipsActivity;
import com.yaya.baby.ui.activity.CenterActivity;
import com.yaya.baby.ui.activity.FamilyManagerActivity;
import com.yaya.baby.ui.activity.MyEquipmentActivity;
import com.yaya.baby.ui.activity.SearchBabyHeartActivity;
import com.yaya.baby.ui.activity.UserDataActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : Darcy
 * @Date ${Date
 * @Description 我的fragment
 */
public class MyFragment extends Fragment {
    private View rootView;
    private Unbinder bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_my, container,
                    false);
        }
        bind = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.my_equipment, R.id.my_consultation, R.id.rl_user_data,
            R.id.my_family, R.id.my_collection,R.id.my_opinion})
    public void onClick(View view) {
        switch (view.getId()) {
            //我的设备
            case R.id.my_equipment:
                skipPage(MyEquipmentActivity.class);
                break;
            //我的咨询
            case R.id.my_consultation:
                skipPage(SearchBabyHeartActivity.class);
                break;
            //用户资料
            case R.id.rl_user_data:
                skipPage(UserDataActivity.class);
                break;
            //家庭成员管理
            case R.id.my_family:
                skipPage(FamilyManagerActivity.class);
                break;
            //我的收藏
            case R.id.my_collection:
                skipPage(CenterActivity.class);
                break;
            //意见反馈
            case R.id.my_opinion:
                skipPage(BindTipsActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /**
     * 跳转界面
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void skipPage(Class<? extends Activity> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}
