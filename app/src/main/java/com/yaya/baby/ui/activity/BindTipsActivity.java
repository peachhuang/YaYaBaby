package com.yaya.baby.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.yaya.baby.R;
import com.yaya.baby.ui.adapter.ShowFindEquipmentAdapter;
import com.yaya.baby.bean.BluetoothEntity;
import com.yaya.baby.ui.common.BaseActivity;
import com.yaya.baby.ui.common.RecycleViewItemClickListener;
import com.yaya.baby.utils.PreferencesUtils;
import com.yaya.baby.utils.ToastUtils;
import com.yaya.baby.view.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.inuker.bluetooth.library.Constants.REQUEST_FAILED;
import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;
import static com.yaya.baby.BabyApplication.getBluetoothClient;
import static com.yaya.baby.ui.common.config.Const.MAC_ADDRESS;
import static com.yaya.baby.ui.common.config.Const.SCAN_FLAG;
import static com.yaya.baby.ui.common.config.Const.TEXT_FLAG;

/**
 * 绑定提示界面
 *
 * @author Darcy
 */
public class BindTipsActivity extends BaseActivity {

    @BindView(R.id.topView)
    TopView topView;
    //扫描到设备的适配器
    private ShowFindEquipmentAdapter adapter;
    private List<BluetoothEntity> list = new ArrayList<>();

    private Context mContext;

    private String mac = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        topView.setTitle("绑定提示");
        //mac = PreferencesUtils.getString(this, MAC_ADDRESS);
    }


    @OnClick({R.id.tv_bind_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind_now:
                mac = PreferencesUtils.getString(this, MAC_ADDRESS);
                if (TextUtils.isEmpty(mac))
                    showDialog();
                else
                    ToastUtils.showToast(mContext, "你已经绑定，请先解绑");
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_tips;
    }


    TextView tvDialogTitle;
    //没有设备显示的图片
    ImageView ivNoEquipment;
    //雷达扫描的主要控件
    RelativeLayout rlSearch;
    //显示扫描到设备的recycleView
    RecyclerView recyclerView;
    //停止扫描的按钮
    Button stopSearch;
    //包含两个按钮的主控件
    LinearLayout llTwo;

    Button btnPositive;
    Button btnNavigation;
    //左边按钮的标识
    private int leftFlag = 0;
    //连接成功
    LinearLayout llConnectionSuccess;

    //展示搜索的弹框
    private void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_search_equipment, null);
        tvDialogTitle = view.findViewById(R.id.tv_dialog_title);
        ivNoEquipment = view.findViewById(R.id.iv_no_equipment);
        rlSearch = view.findViewById(R.id.rl_search);
        recyclerView = view.findViewById(R.id.rv_show_equipment);
        stopSearch = view.findViewById(R.id.btn_stop_search);
        llTwo = view.findViewById(R.id.ll_two_button);
        btnPositive = view.findViewById(R.id.btn_positive);
        llConnectionSuccess = view.findViewById(R.id.ll_connection_success);
        //左边按钮的点击事件
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫描
                if (leftFlag == SCAN_FLAG) {
                    list.clear();
                    tvDialogTitle.setText("正在寻找设备");
                    rlSearch.setVisibility(View.VISIBLE);
                    ivNoEquipment.setVisibility(View.GONE);
                    stopSearch.setVisibility(View.VISIBLE);
                    llTwo.setVisibility(View.GONE);
                } else if (leftFlag == TEXT_FLAG) {
                    skipPage(AutoFetalActivity.class);
                }
            }
        });
        btnNavigation = view.findViewById(R.id.btn_navigation);
        //右边的按钮
        btnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                leftFlag = 0;
                dialog.dismiss();
            }
        });

        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        adapter = new ShowFindEquipmentAdapter(this, list, new RecycleViewItemClickListener() {
            @Override
            public void onItemClick(int i, TextView textView) {
                textView.setText("正在连接");
                textView.setTextColor(getResources().getColor(R.color.green_53ce78));
                connection(list.get(i).getMacStr(), textView);
            }
        });
        recyclerView.setAdapter(adapter);

        stopSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 50, 0, 50, 0);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView ivSearchScan = view.findViewById(R.id.iv_search_scan);
        Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotateAnim.setInterpolator(new LinearInterpolator());
        ivSearchScan.startAnimation(rotateAnim);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        //弹框取消时停止扫描
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getBluetoothClient().stopSearch();
            }
        });

        scan();
    }

    //扫描蓝牙设备
    private void scan() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(1000 * 10, 3)   // 先扫BLE设备3次，每次3s
                //.searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                //.searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        getBluetoothClient().search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {
                list.clear();
            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                if (device.getName().contains("Baby")) {
                    tvDialogTitle.setText("选择配对设备");
                    recyclerView.setVisibility(View.VISIBLE);
                    rlSearch.setVisibility(View.GONE);
                    list.add(new BluetoothEntity(device.getAddress(), "宝宝胎动贴"));
                    adapter.notifyDataSetChanged();
                    getBluetoothClient().stopSearch();
                }

                Log.i("hello", "onDeviceFounded: " + device.getName());
            }

            @Override
            public void onSearchStopped() {
                //Log.i("hello", "onSearchStopped:扫描停止了");
                if (list.size() == 0) {
                    rlSearch.setVisibility(View.GONE);
                    ivNoEquipment.setVisibility(View.VISIBLE);
                    stopSearch.setVisibility(View.GONE);
                    llTwo.setVisibility(View.VISIBLE);
                    tvDialogTitle.setText("尚未找到设备");
                    btnPositive.setText("重新扫描");
                    leftFlag = 0;
                }
            }

            @Override
            public void onSearchCanceled() {

            }
        });
    }

    /**
     * 连接蓝牙设备
     *
     * @param mac      蓝牙的mac地址
     * @param textView 连接状态的textView
     */
    private void connection(final String mac, final TextView textView) {
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
                    textView.setTextColor(Color.RED);
                    textView.setText("连接失败");
                } else if (code == REQUEST_SUCCESS) {
                    //将已经配对的设备保存在本地
                    PreferencesUtils.putString(mContext, MAC_ADDRESS, mac);
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
                tvDialogTitle.setText("连接成功");
                llConnectionSuccess.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                llTwo.setVisibility(View.VISIBLE);
                stopSearch.setVisibility(View.GONE);
                btnPositive.setText("进入检测");
                leftFlag = 1;
            } else if (status == STATUS_DISCONNECTED) {
                tvDialogTitle.setText("连接失败");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // getBluetoothClient().unregisterConnectStatusListener(MAC, mBleConnectStatusListener);
    }
}
