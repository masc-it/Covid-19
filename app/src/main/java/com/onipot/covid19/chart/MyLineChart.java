package com.onipot.covid19.chart;

import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.onipot.covid19.R;
import com.onipot.covid19.chart.formatter.DayAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class MyLineChart {

    public List<ILineDataSet> dataSets = new ArrayList<>();
    private LineChart chart;

    private LineDataSet dataSet;
    private LineData lineData;

    public float getAvg() {
        return avg;
    }

    private float avg = 0;
    private float sum = 0;

    public void setSum(float sum){
        this.sum += sum;
    }
    public void setAverageValue(int count){
        avg = sum / count;
    }

    public MyLineChart(LineChart chart){
        this.chart = chart;

        Legend l = chart.getLegend();
        l.setEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);

    }

    public LineChart getChart() {
        return chart;
    }

    public int addData(List<Entry> data, int color, String title){

        LineDataSet dataSet = new LineDataSet(data, title);
        dataSet.setLineWidth(1.4f);
        dataSet.setCircleRadius(5f);
        dataSet.setCircleHoleRadius(2.5f);
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
        dataSet.setHighLightColor(color);
        dataSet.setDrawValues(false);

        dataSets.add(dataSet);

        return dataSets.size() -1;
    }

    public void load(){
        chart.setData(new LineData(dataSets));
        setCommonChartProps();
        setBackgroundColor(Color.parseColor("#ffffff"));
        setupXAxis();
        setupYAxis();
    }

    List<Entry> data ;
    public void loadData(List<Entry> data, List<Entry> data2){

        this.data = data;
        dataSet = new LineDataSet(data, "Tamponi");
        dataSet.setLineWidth(1.4f);
        dataSet.setCircleRadius(5f);
        dataSet.setCircleHoleRadius(2.5f);
        dataSet.setColor(Color.BLACK);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setHighLightColor(Color.BLACK);
        dataSet.setDrawValues(false);

        lineData = new LineData(dataSet);
        //chart.setData(lineData);

        LineDataSet dataSet2 = new LineDataSet(data2, "Casi totali");
        dataSet2.setLineWidth(1.4f);
        dataSet2.setCircleRadius(5f);
        dataSet2.setCircleHoleRadius(2.5f);
        dataSet2.setColor(Color.RED);
        dataSet2.setCircleColor(Color.RED);
        dataSet2.setHighLightColor(Color.RED);
        dataSet2.setDrawValues(false);


        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(dataSet2);
        chart.setData(new LineData(dataSets));

        // test
        setCommonChartProps();
        setBackgroundColor(Color.parseColor("#ffffff"));
        setupXAxis();
        setupYAxis();

    }


    public void showChart(){

        load();

        IMarker marker = new CustomMarker(chart.getContext(), R.layout.chart_marker, dataSets);
        chart.setMarker(marker);

        chart.setVisibility(View.VISIBLE);

        //chart.getLayoutParams().height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
        //chart.setViewPortOffsets(0, 0, 0, 0f);


        chart.invalidate();
    }

    public void setCommonChartProps(){

        //((LineDataSet) lineData.getDataSetByIndex(0)).setCircleHoleColor(Color.parseColor("#000000"));

        chart.getDescription().setEnabled(false);

        chart.setDrawGridBackground(false);
        // barChart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);

        chart.setTouchEnabled(true);
        chart.getViewPortHandler().setMaximumScaleX(4);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(false);

        chart.setHighlightPerTapEnabled(true);
        chart.getAxisRight().setEnabled(false);

        chart.setAutoScaleMinMaxEnabled(true);



    }

    public void setBackgroundColor(int color){
        chart.setBackgroundColor(color);

    }

    public void setOnChartValueSelectedListener(OnChartValueSelectedListener listener){
        chart.setOnChartValueSelectedListener(listener);
    }


    public void setupXAxis(){
        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setMaxHighlightDistance(200);

        x.setDrawGridLines(false);
        x.setGranularity(1f); // only intervals of 1 day
        x.setLabelCount(5);
        x.setTextColor(Color.BLACK);

        x.setValueFormatter(new DayAxisValueFormatter(chart));

       // chart.getXAxis().setAxisMinimum(data.get(0).getX() - 0.1f);
        //chart.getXAxis().setAxisMaximum(data.get(data.size()-1).getX() + 0.1f);


    }


    public void setupYAxis(){
        YAxis y = chart.getAxisLeft();

        y.setLabelCount(6, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.BLACK);


    }

    public void addLimitLine(float value, String label, int textColor, int lineColor, LimitLine.LimitLabelPosition pos ){
        LimitLine cl = new LimitLine(value, label);
        cl.setLineWidth(2f);
        cl.enableDashedLine(10f, 10f, 0f);
        cl.setLabelPosition(pos);
        cl.setTextColor(textColor);
        cl.setLineColor(lineColor);
        cl.setTextSize(10f);

        chart.getAxisLeft().addLimitLine(cl);

    }

}
