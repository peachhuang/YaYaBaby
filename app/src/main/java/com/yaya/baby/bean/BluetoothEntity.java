package com.yaya.baby.bean;

/**
 * @author : Darcy
 * @Date ${Date}
 * @Description 蓝牙设备实体
 */
public class BluetoothEntity {

    private String macStr;
    private String nameStr;

    public BluetoothEntity(String macStr,String nameStr) {
        this.macStr = macStr;
        this.nameStr = nameStr;
    }

    public String getMacStr() {
        return macStr;
    }

    public String getNameStr() {
        return nameStr;
    }
}
