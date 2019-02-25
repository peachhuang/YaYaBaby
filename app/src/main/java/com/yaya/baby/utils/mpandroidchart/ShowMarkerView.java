package com.yaya.baby.utils.mpandroidchart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;


/**
 * @author youhai.cai
 * @project AppFramework
 * @package com.app.framework.utils.mpandroidchart
 * @time 2018-10-17 15:50
 */

public class ShowMarkerView extends MarkerView {
    private TextView tvContent;

    public ShowMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
//        tvContent= (TextView) findViewById(R.id.tvContent);

    }


    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        String text="x:" + entry.getX()+",y:"+entry.getY();
        tvContent.setText(text);

        super.refreshContent(entry, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }


}
