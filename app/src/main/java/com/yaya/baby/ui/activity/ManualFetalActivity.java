package com.yaya.baby.ui.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yaya.baby.R;
import com.yaya.baby.utils.CountDownTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 数胎动的界面
 *
 * @author Darcy
 * @date 2018-10-08
 */
public class ManualFetalActivity extends AppCompatActivity {

    @BindView(R.id.tv_fetal_number)
    TextView tvFetalNumber;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.tv_real_fetal)
    TextView tvRealFetal;
    @BindView(R.id.tv_time_count)
    TextView tvTimeCount;

    private int realNumber = 0;
    private long exitTime = 0;
    private String currentTime = "";
    private int fetalNUmber = 0;
    //倒计时工具
    private CountDownTimeUtil count;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetal);
        ButterKnife.bind(this);
        tvFetalNumber.setText("" + fetalNUmber);
        getTime();
        tvCurrentTime.setText(currentTime);

        count = new CountDownTimeUtil(tvTimeCount);
        count.timerStart();

    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.iv_foot, R.id.rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_foot:
                checkPressTime();
                break;
            case R.id.rl_back:
                finish();
                break;
        }
    }

    //获取当前的时间
    private void getTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");// HH:mm:ss
        // 获取当前时间
        Date date = new Date(System.currentTimeMillis());
        currentTime = simpleDateFormat.format(date);
    }


    //检查是否是5分钟外按的
    @SuppressLint("SetTextI18n")
    public void checkPressTime() {
        if ((System.currentTimeMillis() - exitTime) > 1000 * 60 * 5) {
            realNumber += 1;
            tvRealFetal.setText("" + realNumber);
            exitTime = System.currentTimeMillis();
        }
        fetalNUmber += 1;
        tvFetalNumber.setText("" + fetalNUmber);

    }
}
