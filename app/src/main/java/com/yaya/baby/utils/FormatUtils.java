package com.yaya.baby.utils;

import java.math.BigDecimal;

/**
 * 数据格式工具类
 * @author youhai.cai
 * @project AppFramework
 * @package com.app.framework.utils
 * @time 2018-10-17 14:08
 */
public class FormatUtils {

    /**
     * 保留几位小数
     * @param targetNum 需要转换的数
     * @param scale 设置保留的位数
     * @time 2018-10-17 14:12
     * @return void
     **/
    public static float formatDecimal(double targetNum,int scale){
        BigDecimal bigDecimal  =   new BigDecimal(targetNum);
        bigDecimal  = bigDecimal.setScale(scale,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }

    /**
     * 保留几位小数
     * @param targetNum 需要转换的数
     * @param scale 设置保留的位数
     * @param mode 他舍值方式，例如:{@link BigDecimal#ROUND_HALF_UP}表示四舍五入
     * @time 2018-10-17 14:12
     * @return void
     **/
    public static float formatDecimal(double targetNum,int scale,int mode){
        BigDecimal bigDecimal  =   new BigDecimal(targetNum);
        bigDecimal  = bigDecimal.setScale(scale,mode);
        return bigDecimal.floatValue();
    }

}
