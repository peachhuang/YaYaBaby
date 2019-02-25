package com.yaya.baby.utils.bluetooth;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 关于蓝牙胎动和心跳的接口回调
 */
public interface BluetoothCallback {


    /**
     * 胎动回调
     *
     * @param currentTime 获取胎动值的时间
     * @param fetalValue  胎动值
     */
    void onFetalResponse(long currentTime, int fetalValue);

    /**
     * 心率回调
     *
     * @param currentTime 获取心率的时间值
     * @param heartRate   心率值
     */
    void onHeartRateResponse(long currentTime, float heartRate);

    //通知失败
    void onNotifyFailed();
}
