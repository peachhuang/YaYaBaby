package com.yaya.baby.utils.bluetooth;

import android.text.TextUtils;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.yaya.baby.BabyApplication;
import com.yaya.baby.utils.ByteAndStringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.inuker.bluetooth.library.Code.REQUEST_SUCCESS;

/**
 * @author : Darcy
 * @Date ${Date} 2018-11-06
 * @Description 蓝牙相关的逻辑类
 */
public class BluetoothManage {
    //蓝牙服务uuid
    public static final String SERVICE_UUID = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
    //蓝牙写入命令的uuid
    public static final String WRITE_UUID = "6e400002-b5a3-f393-e0a9-e50e24dcca9e";
    //通知UUID
    public static final String NOTIFY_UUID = "6e400003-b5a3-f393-e0a9-e50e24dcca9e";

    /********************************蓝牙命令模块********************************/
    //获取胎心和胎动的命令
    public static final String CMD_GET_HEART_FETAL = "010B0400";
    //找胎心命令
    public static final String CMD_BEGIN_FIND_HEART = "02010400";
    //停止找胎心命令
    public static final String CMD_STOP_FIND_HEART = "01880400";


    //作为全局管理的蓝牙类
    private BluetoothClient bluetoothClient;

    private List<BluetoothCallback> callbacks = new ArrayList(5);

    public void addCallback(BluetoothCallback callback) {
        callbacks.add(callback);
    }

    public void removeCallback(BluetoothCallback callback) {
        callbacks.remove(callback);
    }

    public void clear() {
        callbacks.clear();
    }

    /**
     * 在activity销毁时调用
     *
     * @date 2018-11-06
     */
    public void onDestory() {
        clear();
    }

    /**
     * 监听心率和胎动的数据
     *
     * @param mac 蓝牙的mac地址
     * @date 2018-11-06
     */
    public void listenerHeartRateAndFetal(final String mac) {
        notify(mac, new BleNotifyResponse() {
            @Override
            public void onNotify(UUID service, UUID character, byte[] value) {
                //将字节流转化为16进制的字符
                String result = ByteAndStringUtils.bytesToHexString(value);

                //校验获取的数据
                if (TextUtils.isEmpty(result)) {
                    return;
                }
                //当前的时间
                long currentTime = new Date().getTime();

                int fetalValue = ParseUtil.parseFetalValue(result);
                if (fetalValue > 0 && callbacks.size() > 0) {
                    for (BluetoothCallback callback : callbacks) {
                        callback.onFetalResponse(currentTime, fetalValue);
                    }
                }

                //心率值
                int heartRateValue = ParseUtil.parseHeartRate(result);
                if (callbacks.size() > 0) {
                    for (BluetoothCallback callback : callbacks) {
                        callback.onHeartRateResponse(currentTime, heartRateValue);
                    }
                }

            }

            @Override
            public void onResponse(int code) {
                if (code == REQUEST_SUCCESS) {
                    getHeartRateFetal(mac);
                } else {
                    if (callbacks.size() > 0) {
                        for (BluetoothCallback callback : callbacks) {
                            callback.onNotifyFailed();
                        }
                    }
                }
            }
        });
    }

    /**
     * 打开通知获取来自设备的数据
     *
     * @date 2018-11-06
     */
    public void notify(final String mac, BleNotifyResponse response) {
        notify(mac, SERVICE_UUID, NOTIFY_UUID, response);
    }

    /**
     * 打开通知
     *
     * @param mac         蓝牙的Mac地址
     * @param serviceUUID 蓝牙服务的uuid
     * @param notifyUUID  蓝牙通知的uuid
     * @param response    蓝牙通知的回调结果
     */
    private void notify(final String mac, String serviceUUID, String notifyUUID, BleNotifyResponse response) {
        UUID SERVICE_UUID = UUID.fromString(serviceUUID);
        UUID NOTIFY_UUID = UUID.fromString(notifyUUID);
        bluetoothClient.notify(mac, SERVICE_UUID, NOTIFY_UUID, response);
    }


    /**
     * 找心率和胎动
     *
     * @param mac 蓝牙的mac地址
     * @date 2018-11-07
     */
    private void getHeartRateFetal(String mac) {
        write(mac, CMD_GET_HEART_FETAL, new BleWriteResponse() {
            @Override
            public void onResponse(int code) {

            }
        });
    }

    /**
     * 发送命令
     *
     * @param mac
     * @param cmd
     * @param response 蓝牙写入后的回调
     * */
    public void write(String mac,String cmd,BleWriteResponse response){
        byte[] btyeCMD= ByteAndStringUtils.hexStringToByte(cmd);
        write(mac,SERVICE_UUID,WRITE_UUID,btyeCMD,response);
    }

    /**
     * 发送命令
     * @param mac
     * @param serviceUUID
     * @param writeUUID
     * @param bytes
     * */
    private void write(String mac,String serviceUUID,String writeUUID,byte[]bytes,BleWriteResponse response){
        //蓝牙服务uuid
        UUID SERVICE_UUID = UUID.fromString(serviceUUID);
        UUID WRITE_UUID = UUID.fromString(writeUUID);
        bluetoothClient.write(mac, SERVICE_UUID, WRITE_UUID, bytes,response);
    }



    /**
     * 取消蓝牙通知
     * @param mac mac地址
     * @time 2018-11-1 9:21
     * @return void
     **/
    public void unnotify(String mac,BleUnnotifyResponse unnotifyResponse){
        unnotify(mac,SERVICE_UUID,NOTIFY_UUID,unnotifyResponse);
    }

    /**
     * 取消蓝牙通知
     * @param mac mac地址
     * @param serviceUUID serviceUUID
     * @param notifyUUID notifyUUID
     * @time 2018-11-1 9:21
     * @return void
     **/
    public void unnotify(String mac, String serviceUUID, String notifyUUID, final BleUnnotifyResponse unnotifyResponse){
        //蓝牙服务uuid
        UUID SERVICE_UUID = UUID.fromString(serviceUUID);
        //字段的uuid
        UUID NOTIFY_UUID = UUID.fromString(notifyUUID);
        bluetoothClient.unnotify(mac, SERVICE_UUID, NOTIFY_UUID, new BleUnnotifyResponse() {
            @Override
            public void onResponse(int code) {
                // TODO: 2018-11-1 这里可以做公共逻辑的处理
                if (unnotifyResponse!=null){
                    unnotifyResponse.onResponse(code);
                }
            }
        });
    }


    //作为一个全局单例，管理所有BLE设备的连接。
    public BluetoothClient getBluetoothClient() {
        return bluetoothClient;
    }


    private static BluetoothManage instance;
    private BluetoothManage(){
        bluetoothClient = new BluetoothClient(BabyApplication.getInstance());
    }

    public static BluetoothManage getInstance(){
        if (null==instance){
            synchronized (BluetoothManage.class){
                if (null==instance){
                    instance=new BluetoothManage();
                }
            }
        }
        return instance;
    }
}
