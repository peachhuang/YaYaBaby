package com.yaya.baby.utils.mpandroidchart;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线
 * @author youhai.cai
 * @project AppFramework
 * @package com.app.framework.utils.mpandroidchart
 * @time 2018-10-16 10:29
 */
public class LineChartHelper {

    private LineChart lineChart;
    private XAxis xAxis;
    private YAxis leftAxis;
    private YAxis rightAxis;


    private List<ILineDataSet> lineDataSets = new ArrayList<>();
    private LineData lineData;

    private final String LABEL_LIMIT_LINE_UP="limit_line_up";
    public static final String LABEL_HEART_RATE="heart rate";
    public static  final String LABEL_TETAL="fetal";

    //1分钟5格：xAxisVisibleValueRange=3.75f;xAxisLabelCount=16;
    /**
     * x轴显示的值范围大小,
     **/
    private float xAxisVisibleValueRange=4.5f;
    /**
     * X轴的刻度数,
     **/
    private int xAxisLabelCount=19;

    //网格颜色
    private int gridColor=0xffeaeaea;
    //网格线大小
    private  float gridLineWidth=1.0f;
    //坐标轴刻度文字大小
    private  float axisTextSize=8;
    //坐标轴刻度文字颜色
    private int axisTextColor=0xff888888;

    //胎动显示y值位置
    public static final float FETAL_POS_Y=130.0F;
    private final float NORMAL_HEART_RATE_MAX=160;//正常范围边界最大值
    private final float NORMAL_HEART_RATE_MIN=110;//正常范围边界最小值

    public LineChartHelper(LineChart lineChart) {
        this.lineChart = lineChart;

        xAxis=lineChart.getXAxis();
        leftAxis=lineChart.getAxisLeft();
        rightAxis=lineChart.getAxisRight();

        initLineChart();

        initXAxis();

        initYAxis();

        initLineDatas();
    }


    /**
     *
     * @time 2018-10-16 16:25
     * @return void
     **/
    private LineDataSet buildUpLimitDataSet(){
        int fillColor=0xFFE8E8FF;
        float limitLineWidth=0.1f;

        List<Entry> entries=new ArrayList<>();
        entries.add(new Entry(0,NORMAL_HEART_RATE_MAX));
        entries.add(new Entry(xAxisVisibleValueRange,NORMAL_HEART_RATE_MAX));

        LineDataSet dataSet = new LineDataSet(entries, LABEL_LIMIT_LINE_UP);
        dataSet.setLineWidth(limitLineWidth);
        dataSet.setMode(LineDataSet.Mode.LINEAR);//
        dataSet.setDrawValues(false);//不显示数值
        dataSet.setDrawCircles(false);//不显示圆点
        dataSet.setHighlightEnabled(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillFormatter(new LimitLineFillFormatter(NORMAL_HEART_RATE_MIN));
        dataSet.setFillColor(fillColor);

        dataSet.setColor(0xFFC7EACE);
        return dataSet;
    }

    /**
     * 心率的折线图
     * @time 2018-10-16 16:25
     * @return void
     **/
    private LineDataSet buildHeartRateDataSet(List<Entry> entries){

        int lineColor =0xFFF63BB2;
        float lineWidth=1.0f;
        //心率曲线设置
        LineDataSet heartRateDataSet = new LineDataSet(entries, LABEL_HEART_RATE);
        heartRateDataSet.setLineWidth(lineWidth);
        heartRateDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//设置为曲线
        heartRateDataSet.setDrawValues(false);//不显示数值
        heartRateDataSet.setDrawCircles(false);//不显示圆点
        heartRateDataSet.setHighlightEnabled(true);//显示高亮
        heartRateDataSet.setHighLightColor(lineColor);
        heartRateDataSet.setColor(lineColor);
        return heartRateDataSet;
    }

    /**
     * 胎动的折线图
     * @time 2018-10-16 16:25
     * @return void
     **/
    private LineDataSet buildFetalDataSet(List<Entry> entries){
        float lineWidth=0.1f;
        float circleRadius=4f;//单位是dp
        int color =0xFF47BA5C;
        //胎动曲线设置
        LineDataSet fetalDataset = new LineDataSet(entries, LABEL_TETAL);
        fetalDataset.setLineWidth(lineWidth);
        fetalDataset.setColor(Color.WHITE);
        fetalDataset.setMode(LineDataSet.Mode.LINEAR);//设置为曲线

        fetalDataset.setCircleRadius(circleRadius);
        fetalDataset.setCircleColorHole(color);//圆点空心颜色
        fetalDataset.setCircleColor(color);
        fetalDataset.setHighLightColor(color);
        fetalDataset.setDrawValues(false);//不显示数值
        fetalDataset.setHighlightEnabled(false);//不显示高亮
        return fetalDataset;
    }

    /**
     * 初始化折线（多条线）
     * @time 2018-10-16 11:11
     * @return void
     **/
    private void initLineDatas() {
        LineDataSet dataSet=buildUpLimitDataSet();
        lineDataSets.add(dataSet);

        //添加一个 LineData
        lineData = new LineData(lineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
//        lineChart.setVisibleXRangeMinimum(xAxisVisibleValueRange);//可视范围的值的范围
        lineChart.setVisibleXRangeMaximum(xAxisVisibleValueRange);//可视范围的值的范围
    }

    /**
     * Y坐标轴设置
     * @time 2018-10-16 11:09
     * @return void
     **/
    private void initYAxis() {

        float min=60;//显示最小值
        float max=200;//显示最大值
        // Y轴的刻度数
        int yAxisLabelCount =15;

        leftAxis.setDrawAxisLine(true);
        leftAxis.setAxisLineColor(gridColor);

        leftAxis.setDrawGridLines(true);
        leftAxis.setGridLineWidth(gridLineWidth);
        leftAxis.setGridColor(gridColor);
        leftAxis.setTextSize(axisTextSize);
        leftAxis.setTextColor(axisTextColor);
//        leftAxis.setSpaceMin(10);
//        leftAxis.setSpaceMax(10);
        leftAxis.setAxisMinimum(min);
        leftAxis.setAxisMaximum(max);
        leftAxis.setLabelCount(yAxisLabelCount);

        leftAxis.setValueFormatter(new YAxisValueFormatter());
        //////////////////////////////////
        rightAxis.setEnabled(false);
        rightAxis.setDrawAxisLine(false);


    }

    /**
     * X坐标轴设置
     * @time 2018-10-16 11:07
     * @return void
     **/
    private void initXAxis() {

        xAxis.setDrawGridLines(true);//竖的线(与X轴有关)
        xAxis.setGridLineWidth(gridLineWidth);
        xAxis.setGridColor(gridColor);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxis.setDrawAxisLine(true);//是否显示X轴
        xAxis.setAxisLineColor(gridColor);
        xAxis.setTextSize(axisTextSize);
        xAxis.setTextColor(axisTextColor);
//        xAxis.setSpaceMin(5);//小于最小值5开始画
        xAxis.setSpaceMax(0);//大于最大值10
        xAxis.setAxisMinimum(0);//x轴显示的最小值
//        xAxis.setAxisMaximum(xAxisVisibleValueRange);//x轴显示的最大值
//        xAxis.mAxisRange=2f;
        xAxis.setLabelCount(xAxisLabelCount,false);
        xAxis.setValueFormatter(new XAxisValueFormatter());//设置自定义格式，在绘制之前动态调整x的值。



    }

    /**
     * 图表LineChart设置
     * @time 2018-10-16 11:07
     * @return void
     **/
    private void initLineChart() {


        /*图表设置***/
        //是否启用chart绘图区后面的背景矩形将绘制
        lineChart.setDrawGridBackground(false);
//        //设置网格背景应与绘制的颜色
//        vMutlilineChart.setGridBackgroundColor(Color.WHITE);
        //启用/禁用绘制图表边框（chart周围的线）。
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(true);
        //启用/禁用缩放图表上的两个轴
        lineChart.setScaleEnabled(false);
        //设置在曲线图中显示的最大数量
//        vMutlilineChart.setVisibleXRangeMaximum(40);//可视范围的值的范围
        lineChart.setNoDataText("没有数据喔~~");
        Description description = new Description();
        description.setEnabled(false);
        description.setText("");//我是描述信息
        lineChart.setDescription(description);

        Legend legend = lineChart.getLegend();
//        legend.setWordWrapEnabled(false);//设置标签是否换行（当多条标签时 需要换行显示、如上右图）true：可换行。false：不换行
        legend.setEnabled(false);//隐藏图例
    }

    private LineDataSet heartRateDataSet;
    private LineDataSet fetalDataSet;



    /**
     * 动态增加折线数据
     * @param xValue x轴对应的值
     * @param yValue y轴对应的值
     * @param label 折线的label
     * @time 2018-10-16 14:55
     * @return void
     **/
    public void addEntry(float xValue,float yValue,String label){
        addEntry(new Entry(xValue,yValue),label);
    }
    /**
     * 动态增加折线数据
     * @param entry 折线的数据
     * @param label 折线的label
     * @time 2018-10-16 14:55
     * @return void
     **/
    public void addEntry(Entry entry, String label){
        if (LABEL_HEART_RATE.equals(label)&&null==heartRateDataSet){
            ArrayList<Entry> entries=new ArrayList<>();
            entries.add(entry);
            heartRateDataSet = buildHeartRateDataSet(entries);
            lineDataSets.add(heartRateDataSet);
        }else if (LABEL_TETAL.equals(label)&&null==fetalDataSet){
            ArrayList<Entry> entries=new ArrayList<>();
            entries.add(entry);
            fetalDataSet=buildFetalDataSet(entries);
            lineDataSets.add(fetalDataSet);
        }else {
            ILineDataSet lineDataSet=lineData.getDataSetByLabel(label,true);
            lineDataSet.addEntry(entry);
        }

        //设置上面的limitline动态增加
        ILineDataSet lineDataSet=lineData.getDataSetByLabel(LABEL_LIMIT_LINE_UP,true);
        if (lineDataSet.getEntryCount()>=2 && entry.getX()>xAxisVisibleValueRange){
            lineDataSet.removeEntry(lineDataSet.getEntryCount()-1);
            lineDataSet.addEntry(new Entry(entry.getX(),NORMAL_HEART_RATE_MAX));
        }

        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        //控制x轴值的显示范围
//        lineChart.setVisibleXRangeMinimum(xAxisVisibleValueRange);
        lineChart.setVisibleXRangeMaximum(xAxisVisibleValueRange);
        if (entry.getX()>xAxisVisibleValueRange){
            lineChart.moveViewToX(entry.getX());//移动到最后一个
        }else {
            lineChart.moveViewToX(0);
        }

    }


    /**
     * 添加历史数据
     * @time 2018-10-16 15:30
     * @return void
     **/
    public void addEntries(List<Entry> entries, String label){
        if (LABEL_HEART_RATE.equals(label)&&null==heartRateDataSet){
            heartRateDataSet = buildHeartRateDataSet(entries);
            lineDataSets.add(heartRateDataSet);
        }else if (LABEL_TETAL.equals(label)&&null==fetalDataSet){
            fetalDataSet=buildFetalDataSet(entries);
            lineDataSets.add(fetalDataSet);
        }else {
            ILineDataSet lineDataSet=lineData.getDataSetByLabel(label,true);
            for (Entry entry:entries){
                lineDataSet.addEntry(entry);
            }
        }

        //控制网格不变
        if (entries.size()>0&& entries.get(entries.size()-1).getX()>xAxisVisibleValueRange){
            xAxis.setSpaceMax(0);
        }else {
            float max=entries.size()==0?xAxisVisibleValueRange:(xAxisVisibleValueRange-entries.get(entries.size()-1).getX());
            xAxis.setSpaceMax(max);
        }


        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.setVisibleXRangeMinimum(xAxisVisibleValueRange);
        lineChart.setVisibleXRangeMaximum(xAxisVisibleValueRange);
        lineChart.moveViewToX(0);
    }


}
