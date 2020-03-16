package com.onipot.covid19;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

public class Dato {


    private List<String> entries;
    private String titolo;
    private int color;

    public Dato(List<String> entries, String titolo, int color){
        this.entries = entries;
        this.titolo = titolo;
        this.color = color;
    }


    public List<String> getEntries() {
        return entries;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getColor() {
        return color;
    }
}
