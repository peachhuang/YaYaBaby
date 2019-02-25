package com.yaya.baby.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yaya.baby.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的设备的界面
 *
 * @author Darcy
 */
public class MyEquipmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_equipment);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.rl_baby_fetal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_baby_fetal:
                startActivity(new Intent(this,MyFetalActivity.class));
                break;

            default:

                break;
        }
    }
}
