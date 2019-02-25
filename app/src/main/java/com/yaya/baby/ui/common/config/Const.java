package com.yaya.baby.ui.common.config;

/**
 * 存放常量的工具类
 *
 * @author Darcy
 */
public class Const {
    //正式环境URL
    // public final static String URL_RELEASE="http://47.92.225.170:8080/baby/";

    //测试环境URL
    //public final static String URL_DEBUG = "http://47.92.225.170:8080/baby/";


    public static final String BASE_URL = "http://47.92.225.170:8080";

    public static final String URL = BASE_URL + "/baby/";

    public static final int HTTP_TIME = 15;

    //存放胎动贴的Mac地址
    public static final String FETAL_MAC = "fetal_mac";
    //扫描的标识
    public static final int SCAN_FLAG = 0;
    //进入检测的标识
    public static final int TEXT_FLAG = 1;
    //已配对的蓝牙设备的mac地址
    public static final String MAC_ADDRESS = "mac_address";
}
