package com.yaya.baby.ui.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.yaya.baby.R;
import com.yaya.baby.ui.activity.ManualFetalActivity;
import com.yaya.baby.ui.adapter.AutoTimeAdapter;
import com.yaya.baby.bean.AutoTimeBean;
import com.yaya.baby.ui.common.BaseFragment;
import com.yaya.baby.ui.common.config.Const;
import com.yaya.baby.utils.PreferencesUtils;
import com.yaya.baby.view.AutoTimeHorizontalView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.inuker.bluetooth.library.Constants.REQUEST_FAILED;
import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DEVICE_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;
import static com.inuker.bluetooth.library.connect.BleConnectManager.refreshCache;
import static com.yaya.baby.BabyApplication.getBluetoothClient;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 首页的fragment
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_statues)
    TextView tvStatues;
    @BindView(R.id.auto_time_view)
    AutoTimeHorizontalView autoTimeHorizontalView;

    @BindView(R.id.iv_first_image)
    ImageView ivFirstImage;
    @BindView(R.id.iv_second_image)
    ImageView ivSecondImage;
    @BindView(R.id.iv_third_image)
    ImageView ivThirdImage;
    @BindView(R.id.iv_play)
    ImageView ivPlay;

    private String mac;

    private boolean isScan = false;
    //横向选择时间的适配器
    private AutoTimeAdapter autoTimeAdapter;
    //数据源
    private List<AutoTimeBean> list = new ArrayList<>();

    //音乐播放器
    MediaPlayer mediaPlayer;// = new MediaPlayer();
    //当前是否在播放
    private boolean isPlay = false;

    private String musicString = "http://www.ytmp3.cn/down/46964.mp3";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mac = PreferencesUtils.getString(context, Const.MAC_ADDRESS);
        if (!TextUtils.isEmpty(mac)) {
            int status = getBluetoothClient().getConnectStatus(mac);
            if (status == STATUS_DEVICE_CONNECTED){

                tvStatues.setText("连接成功");
            }
            else{

                scan();
            }
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                isPlay = true;
                ivPlay.setImageResource(R.mipmap.play_pause);
            } else {
                isPlay = false;
                ivPlay.setImageResource(R.mipmap.play_normal);
            }
        }


        initData();
        initAutoTimeView();  //模拟数据源

    }

    @OnClick({R.id.iv_play, R.id.rl_test_fetal_now, R.id.iv_change_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                if (!isPlay) {
                    mediaPlayer = new MediaPlayer();
                    startMusic();
                    isPlay = true;
                    ivPlay.setImageResource(R.mipmap.play_pause);
                } else {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                    isPlay = false;
                    ivPlay.setImageResource(R.mipmap.play_normal);
                }
                break;

            //去测胎动
            case R.id.rl_test_fetal_now:
                skipPage(ManualFetalActivity.class);
                break;
            //切换宝宝
            case R.id.iv_change_card:
                Log.i("hello", "onClick: ");
                break;
        }
    }

    private void startMusic() {
        try {
            mediaPlayer.setDataSource(musicString);
            //3 准备播放
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁当前activity  音乐暂停
        mediaPlayer.stop();
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            AutoTimeBean bean = new AutoTimeBean();
            bean.setWeek("第38周第" + i + 1 + "天");
            bean.setDate("10月" + i + 1 + "号");
            list.add(bean);
        }
    }

    private void initAutoTimeView() {
        autoTimeAdapter = new AutoTimeAdapter(context, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        autoTimeHorizontalView.setLayoutManager(linearLayoutManager);
        autoTimeHorizontalView.setOnSelectedPositionChangedListener(new AutoTimeHorizontalView.OnSelectedPositionChangedListener() {
            @Override
            public void selectedPositionChanged(int pos) {
                Toast.makeText(context, "pos:" + pos, Toast.LENGTH_SHORT).show();
            }
        });
        autoTimeHorizontalView.setInitPos(5);
        autoTimeHorizontalView.setAdapter(autoTimeAdapter);
    }


    //扫描蓝牙设备
    private void scan() {
        tvStatues.setText("正在连接...");
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(1000 * 10, 3)   // 先扫BLE设备3次，每次3s
                //.searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                //.searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        getBluetoothClient().search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {
            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                if (device.getAddress().equals(mac)) {
                    connection(mac);
                    isScan = true;
                    getBluetoothClient().stopSearch();
                }

                Log.i("hello", "onDeviceFounded: " + device.getName());
            }

            @Override
            public void onSearchStopped() {
                //Log.i("hello", "onSearchStopped:扫描停止了");
                if (!isScan)
                    tvStatues.setText("连接失败");
            }

            @Override
            public void onSearchCanceled() {

            }
        });
    }

    /**
     * 连接蓝牙设备
     *
     * @param mac 蓝牙的mac地址
     */
    private void connection(final String mac) {
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)   // 连接如果失败重试3次
                .setConnectTimeout(30000)   // 连接超时30s
                .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                .build();

        getBluetoothClient().connect(mac, options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile data) {
                if (code == REQUEST_FAILED) {
                    //刷新缓存
                    refreshCache(mac);
                    tvStatues.setText("连接失败");
                } else if (code == REQUEST_SUCCESS) {
                    //将已经配对的设备保存在本地
                }

                Log.i("hello", "onResponse: " + code);
            }
        });
        //注册连接状态的监听
        getBluetoothClient().registerConnectStatusListener(mac, mBleConnectStatusListener);
    }

    //连接状态监听
    private final BleConnectStatusListener mBleConnectStatusListener = new BleConnectStatusListener() {

        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == STATUS_CONNECTED) {
                tvStatues.setText("连接成功");
            } else if (status == STATUS_DISCONNECTED) {
                tvStatues.setText("连接失败");
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!TextUtils.isEmpty(mac))
            getBluetoothClient().unregisterConnectStatusListener(mac, mBleConnectStatusListener);
    }
}
