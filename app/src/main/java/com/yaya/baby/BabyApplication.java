package com.yaya.baby;

import android.app.Application;
import android.content.Context;

import com.inuker.bluetooth.library.BluetoothClient;
/**
 * 运用的入口
 *
 * @author Darcy
 * */
public class BabyApplication extends Application {

    private static BabyApplication mInstance;

    private static BluetoothClient client;

    public static Context getInstance(){
        return mInstance == null ? null : mInstance.getApplicationContext();
    }
    //作为一个全局单例，管理所有BLE设备的连接。
    public static BluetoothClient getBluetoothClient(){
        if (null==client)
            client = new BluetoothClient(mInstance);

        return client;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        client = new BluetoothClient(this);
    }
}
