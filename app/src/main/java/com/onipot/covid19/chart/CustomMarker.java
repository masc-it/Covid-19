package com.onipot.covid19.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.onipot.covid19.R;

import java.util.List;

public class CustomMarker extends MarkerView {

    private TextView value, dim, dimDate;
    private List<ILineDataSet> dataSets;
    public CustomMarker(Context context, int layoutResource, List<ILineDataSet> dataSets) {
        super(context, layoutResource);

        this.dataSets = dataSets;
        value = findViewById(R.id.chart_marker_value);
        dim = findViewById(R.id.dim_name);
        dimDate = findViewById(R.id.dim_date);
    }

    private MPPointF mOffset;
    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            mOffset = new MPPointF(-(getWidth() + 80 ), -getHeight());
        }
        return mOffset;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        dim.setText(dataSets.get(highlight.getDataSetIndex()).getLabel() );
        value.setText(": " + String.format("%d", (int) e.getY()) );

        dimDate.setText(getDate(e.getX()));
        super.refreshContent(e, highlight);
    }

    private final String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    private String getDate(float value){
        int days = (int) value;

        int year = determineYear(days);

        int month = determineMonth(days);
        String monthName = mMonths[month % mMonths.length];
        String yearName = String.valueOf(year);

        int dayOfMonth = determineDayOfMonth(days, month + 12 * (year - 2016));

        return dayOfMonth == 0 ? "" : dayOfMonth + " " + monthName;

    }
    @Override
    public void draw(Canvas canvas, float posX, float posY) {


        super.draw(canvas, posX, posY);


    }


    private int getDaysForMonth(int month, int year) {

        // month is 0-based

        if (month == 1) {
            boolean is29Feb = false;

            if (year < 1582)
                is29Feb = (year < 1 ? year + 1 : year) % 4 == 0;
            else if (year > 1582)
                is29Feb = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);

            return is29Feb ? 29 : 28;
        }

        if (month == 3 || month == 5 || month == 8 || month == 10)
            return 30;
        else
            return 31;
    }

    private int determineMonth(int dayOfYear) {

        int month = -1;
        int days = 0;

        while (days < dayOfYear) {
            month = month + 1;

            if (month >= 12)
                month = 0;

            int year = determineYear(days);
            days += getDaysForMonth(month, year);
        }

        return Math.max(month, 0);
    }

    private int determineDayOfMonth(int days, int month) {

        int count = 0;
        int daysForMonths = 0;

        while (count < month) {

            int year = determineYear(daysForMonths);
            daysForMonths += getDaysForMonth(count % 12, year);
            count++;
        }

        return days - daysForMonths;
    }

    private int determineYear(int days) {

        if (days <= 366)
            return 2016;
        else if (days <= 730)
            return 2017;
        else if (days <= 1094)
            return 2018;
        else if (days <= 1458)
            return 2019;
        else
            return 2020;

    }
}
