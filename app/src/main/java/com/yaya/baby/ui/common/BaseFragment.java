package com.yaya.baby.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description
 */
public abstract class BaseFragment extends Fragment {

    protected Context context;
    protected Activity activity;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        this.activity=getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(getLayoutId(),container,false);
        unbinder=ButterKnife.bind(this,view);//绑定framgent
        return view;
    }

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    /**
     * 跳转界面
     *
     * @param cls 要跳转的activity
     */
    public void skipPage(Class<? extends Activity> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }
}
