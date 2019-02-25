package com.yaya.baby.ui.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaya.baby.R;
import com.yaya.baby.ui.common.BaseActivity;
import com.yaya.baby.utils.WaveHelper;
import com.yaya.baby.view.SpreadWaveView;
import com.yaya.baby.view.TopView;
import com.yaya.baby.view.WaveView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 寻找胎心的界面
 *
 * @author Darcy
 */
public class SearchBabyHeartActivity extends BaseActivity {

    @BindView(R.id.topView)
    TopView topView;
    @BindView(R.id.spreadWaveView)
    SpreadWaveView spreadWaveView;
    @BindView(R.id.wave)
    WaveView waveView;
    @BindView(R.id.iv_fetal_heart)
    ImageView ivFetalHeart;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.tv_fetal_suggest)
    TextView tvFetalSuggest;
    //立即进入的按钮
    @BindView(R.id.btn_now)
    Button btnNow;
    //找不到胎心按钮
    @BindView(R.id.btn_not_find)
    Button btnNotFind;
    //找到胎心的提示
    @BindView(R.id.tv_find_heart)
    TextView tvFindHeart;
    @BindView(R.id.rl_find_heart)
    RelativeLayout llFindHeart;
    @BindView(R.id.tv_second)
    TextView tvSeconds;


    private int mBorderColor = Color.parseColor("#Ff5da3");
    private int mBorderWidth = 2;
    private WaveHelper mWaveHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topView.setTitle("正在寻找胎心");
        initWaveView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_baby_heart;
    }

    //初始化波浪线
    private void initWaveView() {
        waveView.setWaterLevelRatio(0.3f);
        mWaveHelper = new WaveHelper(waveView);
        waveView.setShapeType(WaveView.ShapeType.CIRCLE);
        waveView.setWaveColor(
                Color.parseColor("#33ffffff"),
                Color.parseColor("#33ffffff"));
        mBorderColor = Color.parseColor("#ff5da3");
        waveView.setBorder(mBorderWidth, mBorderColor);

        //外扩散圆
        spreadWaveView.setDuration(4000);
        spreadWaveView.setStyle(Paint.Style.STROKE);
        spreadWaveView.setSpeed(800);
        spreadWaveView.setColor(Color.parseColor("#ff5da3"));
        spreadWaveView.setInterpolator(new AccelerateInterpolator(1.2f));
        //最小的半径
        spreadWaveView.setInitialRadius(250);
        //spreadWaveView.setMaxRadiusRate(LinearLayout.LayoutParams.MATCH_PARENT);
        spreadWaveView.start();
    }

    @OnClick({R.id.iv_fetal_heart, R.id.btn_now})
    public void onClick(View view) {
        switch (view.getId()) {

            //寻找胎心贴的界面
            case R.id.iv_fetal_heart:
                changeText();
                break;
            //进入到实时测胎动的界面
            case R.id.btn_now:
                skipPage(AutoFetalActivity.class);
                break;

            default:

                break;
        }
    }

    //发现胎心后改变相关UI
    private void changeText() {
        topView.setTitle("已发现胎心");
        tvTips.setVisibility(View.GONE);
        tvFetalSuggest.setVisibility(View.GONE);
        ivFetalHeart.setVisibility(View.GONE);
        btnNotFind.setVisibility(View.GONE);

        spreadWaveView.setVisibility(View.VISIBLE);
        waveView.setVisibility(View.VISIBLE);
        btnNow.setVisibility(View.VISIBLE);
        tvFindHeart.setVisibility(View.VISIBLE);
        llFindHeart.setVisibility(View.VISIBLE);
        tvSeconds.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper.start();
    }
}
