package com.yaya.baby.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.yaya.baby.R;
import com.yaya.baby.ui.fragment.CommunityFragment;
import com.yaya.baby.ui.fragment.FetalFragment;
import com.yaya.baby.ui.fragment.HomeFragment;
import com.yaya.baby.ui.fragment.MyFragment;
import com.yaya.baby.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yaya.baby.BabyApplication.getBluetoothClient;

/***
 * 主界面
 * @Date 2017-10-16
 * @author Darcy
 * */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_fetal)
    LinearLayout llFetal;
    @BindView(R.id.ll_community)
    LinearLayout llCommunity;
    @BindView(R.id.ll_my)
    LinearLayout llMy;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_fetal)
    TextView tvFetal;
    @BindView(R.id.tv_community)
    TextView tvCommunity;
    @BindView(R.id.tv_my)
    TextView tvMy;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_fetal)
    ImageView ivFetal;
    @BindView(R.id.iv_community)
    ImageView ivCommunity;
    @BindView(R.id.iv_my)
    ImageView ivMy;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_left_image)
    ImageView ivLeftImage;
    @BindView(R.id.iv_right_image)
    ImageView ivRightImage;

    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    private long exitTime = 0;
    //作为全局的单例
    private BluetoothClient mClient;
    private boolean isOpened = false;
    //作为是否打开定位权限的依据
    private boolean isLocation = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mClient = getBluetoothClient();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initEvents();
        checkBluetooth();
        checkPermission();
    }

    //检查蓝牙是否打开
    private void checkBluetooth() {
        boolean isOpen = mClient.isBluetoothOpened();
        if (!isOpen)
            mClient.openBluetooth();
        else
            isOpened = true;

        if (!isOpened)
            //注册打开蓝牙的监听
            mClient.registerBluetoothStateListener(mBluetoothStateListener);
    }

    //蓝牙是否打开的监听
    private final BluetoothStateListener mBluetoothStateListener = new BluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {
            if (openOrClosed) {
                isOpened = true;
                ToastUtils.showToast(mContext, "蓝牙已打开");
            } else {
                isOpened = false;
                ToastUtils.showToast(mContext, "蓝牙未打开");
            }
        }
    };

    //检查定位权限
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            isLocation = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    isLocation = true;
                } else {//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(MainActivity.this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                    isLocation = false;
                }
                break;
            default:
                break;
        }
    }

    private void initView() {
        Fragment homeFragment = new HomeFragment();
        Fragment fetalFragment = new FetalFragment();
        Fragment communityFragment = new CommunityFragment();
        Fragment myFragment = new MyFragment();

        mFragments = new ArrayList<>();
        mFragments.add(homeFragment);
        mFragments.add(fetalFragment);
        mFragments.add(communityFragment);
        mFragments.add(myFragment);

        //设置viewpager的最大缓存量为2
        mViewpager.setOffscreenPageLimit(2);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewpager.setAdapter(mAdapter);

        ivRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    //初始化各个按钮的点击事件
    private void initEvents() {
        llHome.setOnClickListener(this);
        llFetal.setOnClickListener(this);
        llCommunity.setOnClickListener(this);
        llMy.setOnClickListener(this);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewpager.setCurrentItem(position);
                switch (position) {
                    case 0:
                        setSelect(0);
                        break;
                    case 1:
                        setSelect(1);
                        break;
                    case 2:
                        setSelect(2);
                        break;
                    case 3:
                        setSelect(3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //重置底部的按钮和文字的状态
    private void resetAll() {
        tvHome.setTextColor(getResources().getColor(R.color.black));
        tvFetal.setTextColor(getResources().getColor(R.color.black));
        tvCommunity.setTextColor(getResources().getColor(R.color.black));
        tvMy.setTextColor(getResources().getColor(R.color.black));

        ivHome.setImageResource(R.mipmap.home_normal);
        ivFetal.setImageResource(R.mipmap.fetal_normal);
        ivCommunity.setImageResource(R.mipmap.community_normal);
        ivMy.setImageResource(R.mipmap.my_normal);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                setSelect(0);
                break;
            case R.id.ll_fetal:
                setSelect(1);
                break;
            case R.id.ll_community:
                setSelect(2);
                break;
            case R.id.ll_my:
                setSelect(3);
                break;
        }
    }

    //选中不同的fragment
    private void setSelect(int id) {
        resetAll();
        switch (id) {
            case 0:
                tvHome.setTextColor(getResources().getColor(R.color.main_color));
                ivHome.setImageResource(R.mipmap.home_press);
                tvTitle.setText(getString(R.string.home));
                ivLeftImage.setVisibility(View.VISIBLE);
                ivRightImage.setVisibility(View.VISIBLE);
                break;
            case 1:
                tvFetal.setTextColor(getResources().getColor(R.color.main_color));
                ivFetal.setImageResource(R.mipmap.fetal_press);
                tvTitle.setText(getString(R.string.fetal));
                ivLeftImage.setVisibility(View.VISIBLE);
                ivRightImage.setVisibility(View.GONE);
                break;
            case 2:
                tvCommunity.setTextColor(getResources().getColor(R.color.main_color));
                ivCommunity.setImageResource(R.mipmap.community_press);
                tvTitle.setText(getString(R.string.community));
                ivLeftImage.setVisibility(View.GONE);
                ivRightImage.setVisibility(View.GONE);
                break;
            case 3:
                tvMy.setTextColor(getResources().getColor(R.color.main_color));
                ivMy.setImageResource(R.mipmap.my_press);
                tvTitle.setText(getString(R.string.my));
                ivLeftImage.setVisibility(View.GONE);
                ivRightImage.setVisibility(View.GONE);
                break;
        }
        mViewpager.setCurrentItem(id, false);
    }


    //获取扫描后的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            ToastUtils.showToast(mContext, result);

            Log.i("hello2", "onActivityResult: " + result);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //按两次退出程序
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            // System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.unregisterBluetoothStateListener(mBluetoothStateListener);
    }
}



