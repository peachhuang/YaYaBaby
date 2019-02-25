package com.yaya.baby.utils.mpandroidchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * @author youhai.cai
 * @project AppFramework
 * @package com.app.test.mpandroidchart
 * @time 2018-10-15 15:25
 */

public class XAxisValueFormatter implements IAxisValueFormatter {


    private final String UNIT="分钟";

    @Override
    public String getFormattedValue(float v, AxisBase axisBase) {

        if (v==0){
            return "";
        }

        String text="";


        int value= (int) v;

//        int res= (int) ((v-value)*10);
        if (v==value){
            text=value+UNIT;
//            LogUtils.i("v:"+v+",text:"+text);
        }
        return text;
    }

}
