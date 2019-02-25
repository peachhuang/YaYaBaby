package com.yaya.baby.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.yaya.baby.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yaya.baby.BabyApplication.getBluetoothClient;

/**
 * 我的设备宝宝胎动帖界面
 *
 * @author Darcy
 */
public class MyFetalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_my_fetal);

        ButterKnife.bind(this);
    }


    @OnClick({R.id.iv_back, R.id.btn_bind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_bind:
                scan();
                showDialog();
                break;
        }
    }

    //展示搜索的弹框
    private void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final android.support.v7.app.AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_search_equipment, null);
        Button stopSearch = view.findViewById(R.id.btn_stop_search);
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
    }

    //扫描蓝牙设备
    private void scan() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                //.searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                //.searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        getBluetoothClient().search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {

            }

            @Override
            public void onDeviceFounded(SearchResult device) {
//                Beacon beacon = new Beacon(device.scanRecord);
//                BluetoothLog.v(String.format("beacon for %s\n%s", device.getAddress(), beacon.toString()));

                Log.i("hello", "onDeviceFounded: "+device.getAddress());
            }

            @Override
            public void onSearchStopped() {

            }

            @Override
            public void onSearchCanceled() {

            }
        });
    }

}
