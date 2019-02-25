package com.yaya.baby.utils.mpandroidchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;


/**
 * @author youhai.cai
 * @project AppFramework
 * @package com.app.framework.utils.mpandroidchart
 * @time 2018-10-17 14:58
 */

public class YAxisValueFormatter implements IAxisValueFormatter {
    @Override
    public String getFormattedValue(float v, AxisBase axisBase) {
        int value= (int) (v/10);
        if (value%2==0){
            return (int)v+"";
        }
        return "";
    }
}
