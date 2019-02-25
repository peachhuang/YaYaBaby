package com.yaya.baby.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.yaya.baby.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * 他的中心界面
 *
 * @date 2018-10-18
 * @author Darcy
 *
 * */
public class CenterActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    LineChart lineChart;

    ArrayList<Entry> poitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        ButterKnife.bind(this);
        initData();

        initChart();
    }

    //模拟数据源
    private void initData() {
        poitList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            float mult = 30 / 2f;
            float val = (float) (Math.random() * mult) + 130;
            poitList.add(new Entry(i, val));
        }

    }

    //setDragEnabled(boolean b);//启用/禁用平移图表，默认为true
//    setDoubleTapToZoomEnabled(boolean b);//双击缩放 ，默认true
//    setTouchEnabled(boolean b);//开启/禁用所有可能和chart的触摸交互,默认为true
//    setScaleEnabled(boolean b);//启用/禁用缩放图表上的两个轴,默认为true
//    setDrawGridBackground(boolean b);//开启chart绘图区后面的背景矩形将进行绘制
//    setDrawBorders(boolean b);//启用/禁用绘制图表边框
    private void initChart() {
        lineChart.getDescription().setEnabled(false);//隐藏图表描述
        lineChart.setDoubleTapToZoomEnabled(false);
        //lineChart.setDrawGridBackground(true);//chart 绘图区后面的背景矩形将绘制
        lineChart.setBorderColor(R.color.gray_f3f3f3); //设置 chart 边框线的颜色。


        XAxis xAxis = lineChart.getXAxis();//获取x轴
        //xAxis.setDrawAxisLine(true);//绘制X轴，默认为true

        //设置x轴位置，有四个属性。可以说是上下两个位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);//绘制网格线，默认为true
        //xAxis.setGridColor(Color.GRAY);//设置该轴的网格线颜色。
        xAxis.setGridLineWidth(1f);// 设置该轴网格线的宽度。
        xAxis.setAxisLineColor(Color.RED);//设置轴线的轴的颜色。
        xAxis.setDrawLabels(true);//绘制该轴标签
        xAxis.setTextColor(Color.parseColor("#888888"));// 设置轴标签的颜色。
        xAxis.setTextSize(12f);//设置轴标签的文字大小。
        //通过setValueFormatter来转换轴标签
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int i = (int) value;
                Log.i("hello", "getFormattedValue: " + i);
                return i / 10 + 4 + "分钟";


            }
        });
        xAxis.setLabelCount(4);//设置x轴显示的标签个数
        // xAxis.setAxisMinimum(4);
        // xAxis.setAxisMaximum(8);

        LineDataSet dataSet = new LineDataSet(poitList, "胎心图");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setHighLightColor(Color.TRANSPARENT); // 设置点击某个点时，横竖两条线的颜色
        dataSet.setDrawValues(false);//在点上显示数值 默认true
        dataSet.setValueTextSize(10f);//数值字体大小，同样可以设置字体颜色、自定义字体等

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_launcher_background);
        dataSet.setFillDrawable(drawable);//设置范围背景填充

        dataSet.setLineWidth(2f);//设置折线的宽度
        dataSet.setColor(Color.RED);//设置折线的颜色
        dataSet.setDrawCircles(false);//是否显示点相应的圆（圆为外圈圆+内圈圆）
        //dataSet.setCubicIntensity(80f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置为平滑曲线

        LineData data = new LineData(dataSet);
        lineChart.setData(data);

        YAxis leftAxis = lineChart.getAxisLeft();//获取左轴
        // YAxis rightAxis = chart.getAxisRight();//获取右轴
        lineChart.getAxisRight().setEnabled(false);//隐藏右轴  默认显示
        //设置Y轴最大最小值，不设置chart会自己计算
        leftAxis.setAxisMinimum(60f);//设置最小值
        leftAxis.setAxisMaximum(200f);//设置最大值
        leftAxis.setDrawGridLines(true);//绘制网格线 默认为true
        leftAxis.setTextColor(Color.parseColor("#888888"));
        leftAxis.setTextSize(12f);
        leftAxis.setGridLineWidth(1f);
    }
}
