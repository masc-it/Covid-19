package com.onipot.covid19.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.lzyzsd.randomcolor.RandomColor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onipot.covid19.CSV;
import com.onipot.covid19.Dato;
import com.onipot.covid19.Dimension;
import com.onipot.covid19.R;
import com.onipot.covid19.chart.MyLineChart;
import com.onipot.covid19.dialog.DimensionsDialog;
import com.onipot.covid19.net.FetchData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java9.util.stream.StreamSupport;

public class NazioneFragment extends Fragment {

    private LineChart chart;

    private MyLineChart myLineChart;

    CSV data;
    private List<Dimension> dimensions = new ArrayList<>();
    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nazione, container, false);
        chart = v.findViewById(R.id.linechart1);

        myLineChart = new MyLineChart(chart);

        fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(view -> {


            new DimensionsDialog(getContext(), dimensions ).showNew(() -> {
                RandomColor randomColor = new RandomColor();

                for (Dimension d: dimensions){

                    long i = StreamSupport.stream(myLineChart.dataSets).filter(s -> s.getLabel().equals(d.getName()) ).count();
                    if (d.isVisible() && i == 0 )
                        addData(data.getValues("data"), new Dato(data.getValues(d.getName()), d.getName(), randomColor.randomColor()));

                    if ( !d.isVisible() && i > 0){ // remove
                        for ( int j = 0; j < myLineChart.dataSets.size(); j++) {
                            if( myLineChart.dataSets.get(j).getLabel().equals(d.getName())){
                                myLineChart.dataSets.remove(myLineChart.dataSets.get(j));
                                break;
                            }
                        }
                    }
                }
                myLineChart.getChart().invalidate();
                myLineChart.getChart().notifyDataSetChanged();
                myLineChart.getChart().refreshDrawableState();

            });

        });
        loadData();


        return v;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getDayCount(String start, String end) {
        long diff = -1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateStart = simpleDateFormat.parse(start);
            Date dateEnd = simpleDateFormat.parse(end);

            //time is always 00:00:00, so rounding should help to ignore the missing hour when going from winter to summer time, as well as the extra hour in the other direction
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diff;
    }

    private void loadData(){
        RandomColor randomColor = new RandomColor();

        new FetchData("https://github.com/pcm-dpc/COVID-19/raw/master/dati-andamento-nazionale/dpc-covid19-ita-andamento-nazionale.csv",
                csv -> {

            data = csv;

                    for (String s: csv.getHeaders()) {
                        if ( !s.equals("data") && !s.equals("stato") )
                            dimensions.add(new Dimension(s.trim()));
                    }

                    List<String> date = csv.getValues("data");


                    addData(date, new Dato(csv.getValues("tamponi"), "tamponi", randomColor.randomColor()));
                    fab.show();
                    showChart();

                }

        ).execute();
    }

    @SuppressLint("SimpleDateFormat")
    private int addData(List<String> x, Dato y){


        List<Entry> entries = new ArrayList<>();

        try {
        for ( int i = 0; i < x.size(); i++){

            Float val = Float.parseFloat(y.getEntries().get(i));

            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(x.get(i));

            long days = getDayCount("2016-01-01", new SimpleDateFormat("yyyy-MM-dd").format(parsedDate)) + 1;

            entries.add(new Entry(days, val));

        }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return myLineChart.addData(entries, y.getColor(), y.getTitolo());


    }

    public void showChart(){

        myLineChart.showChart();

    }
}
