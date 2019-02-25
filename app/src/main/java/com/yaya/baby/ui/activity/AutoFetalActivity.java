package com.yaya.baby.ui.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.yaya.baby.R;
import com.yaya.baby.ui.common.BaseActivity;
import com.yaya.baby.ui.common.config.Const;
import com.yaya.baby.utils.ByteAndStringUtils;
import com.yaya.baby.utils.FormatUtils;
import com.yaya.baby.utils.PreferencesUtils;
import com.yaya.baby.utils.ToastUtils;
import com.yaya.baby.utils.mpandroidchart.LineChartHelper;

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

import static com.inuker.bluetooth.library.Code.REQUEST_SUCCESS;
import static com.yaya.baby.BabyApplication.getBluetoothClient;

/**
 * 自动测胎动的界面
 *
 * @author Darcy
 */
public class AutoFetalActivity extends BaseActivity {

    @BindView(R.id.v_line_chart)
    LineChart vLineChart;
    @BindView(R.id.tv_heart_beat)
    TextView tvHeartBeat;

    @BindView(R.id.timer)
    Chronometer chronometer;


    private static UUID SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    private static UUID WRITE_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    private static UUID NOTIFY_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    private String mac;

    private LineChartHelper lineChartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mac = PreferencesUtils.getString(context, Const.MAC_ADDRESS);
        lineChartHelper = new LineChartHelper(vLineChart);
        initTestData();
        writeData(ByteAndStringUtils.hexStringToByte("010B0400"));
        openNotify();

        chronometer.setBase(SystemClock.elapsedRealtime());//计时器清零
        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60);
        chronometer.setFormat("0"+String.valueOf(hour)+":%s");
        chronometer.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auto_fetal;
    }

    @OnClick({R.id.tv_right_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_button:
                ToastUtils.showToast(context,"结束");
                stopNotify();
                chronometer.stop();
                break;
        }
    }


    float lastXValue = 0;

    private void initTestData() {

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //添加心率数据
                Random random = new Random();
                float xValue = lastXValue + FormatUtils.formatDecimal(random.nextFloat(), 2);
                float yValue = createRandom();

                lastXValue = xValue;
                Entry entry = new Entry(xValue, yValue);
                lineChartHelper.addEntry(entry, LineChartHelper.LABEL_HEART_RATE);

                //添加胎动
                xValue = lastXValue;//0~50的随机数
                yValue = LineChartHelper.FETAL_POS_Y;
                lineChartHelper.addEntry(xValue, yValue, LineChartHelper.LABEL_TETAL);

                if (lastXValue < 50) {
                    handler.postDelayed(this, 1000);
                }

            }
        }, 1000);

    }

    private float createRandom() {
        Random random = new Random();
        int ran = random.nextInt(2);
        int bound;
        if (ran == 1) {
            bound = random.nextInt(5);
        } else {
            bound = -random.nextInt(5);
        }
        float decefal = FormatUtils.formatDecimal(random.nextFloat(), 2);
        return 150 + bound + decefal;
    }

    //取消通知
    private void stopNotify(){
        //先取消通知
        getBluetoothClient().unnotify(mac, SERVICE_UUID, NOTIFY_UUID, new BleUnnotifyResponse() {
            @Override
            public void onResponse(int code) {

            }
        });
    }


    private void openNotify() {
        //先取消通知
        getBluetoothClient().unnotify(mac, SERVICE_UUID, NOTIFY_UUID, new BleUnnotifyResponse() {
            @Override
            public void onResponse(int code) {

            }
        });

        getBluetoothClient().notify(mac, SERVICE_UUID, NOTIFY_UUID, new BleNotifyResponse() {
            @Override
            public void onNotify(UUID service, UUID character, byte[] value) {
                String s = ByteUtils.byteToString(value);
                tvHeartBeat.setText(s.substring(s.length()-2,s.length()));
                Log.i("hello", "onNotify: " + ByteUtils.byteToString(value));
            }

            @Override
            public void onResponse(int code) {
                if (code == REQUEST_SUCCESS) {

                }
            }
        });
    }

    private void writeData(byte[] bytes) {
        getBluetoothClient().write(mac, SERVICE_UUID, WRITE_UUID, bytes, new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                if (code == REQUEST_SUCCESS) {
                    ToastUtils.showToast(context, "写入成功");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // chronometer.stop();
    }
}
