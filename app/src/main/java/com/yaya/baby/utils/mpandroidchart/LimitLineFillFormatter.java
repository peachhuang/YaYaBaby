package com.yaya.baby.utils.mpandroidchart;

import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


/**
 * @author youhai.cai
 * @project AppFramework
 * @package com.app.framework.utils.mpandroidchart
 * @time 2018-10-17 9:32
 */

public class LimitLineFillFormatter implements IFillFormatter {

    private float minValue=0;

    public LimitLineFillFormatter(float minValue) {
        this.minValue = minValue;
    }

    @Override
    public float getFillLinePosition(ILineDataSet iLineDataSet, LineDataProvider lineDataProvider) {
        return minValue;
    }
}
