package com.onipot.covid19.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.onipot.covid19.CSV;
import com.onipot.covid19.R;
import com.onipot.covid19.net.FetchData;

import java.util.List;

public class RegioneFragment extends Fragment {

    LineChart chart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_regione, container, false);

        chart = v.findViewById(R.id.linechart1);
        new FetchData("https://github.com/pcm-dpc/COVID-19/raw/master/dati-andamento-nazionale/dpc-covid19-ita-andamento-nazionale.csv",
                csv -> {



                }

        ).execute();

        return v;
    }


    private void showChart(List<String> x, List<String> y){

    }
}