package com.yaya.baby.utils.bluetooth;

/**
 * @author : Darcy
 * @Date ${Date}2018-11-06
 * @Description 解析胎心值和心率值的工具类
 */
public class ParseUtil {


    /**
     * 解析心率值
     * @time 2018-11-1 11:10
     * @param result 设备返回的16进制数据
     * */
    public static int parseHeartRate(String result){
        String heartRateValue = result.substring(result.length()-4,result.length()-2);
        return Integer.parseInt(heartRateValue,16);
    }

    /**
     * 解析胎动值
     *
     * @param result 设备返回结果
     * @time 2018-11-1 11:20
     * @return int 时间段内的胎动值，10秒内的胎动值，比如10秒说动了一下就是1，动了2下就是2，没有动就是0
     **/
    public static int parseFetalValue(String result){
        //胎动值
        String strFetalValue = result.substring(result.length() - 2, result.length());
        //时间段内的胎动值，10秒内的胎动值，比如10秒说动了一下就是1，动了2下就是2，没有动就是0
        return Integer.parseInt(strFetalValue, 16);
    }
}
