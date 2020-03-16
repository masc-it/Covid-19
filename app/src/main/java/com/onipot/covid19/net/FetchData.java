package com.onipot.covid19.net;

import android.os.AsyncTask;

import com.onipot.covid19.CSV;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class FetchData extends AsyncTask<Void, Void, CSV> {

    private String url;
    private CSV.Notify callback;
    public FetchData(String url, CSV.Notify callback){

        this.url = url;
        this.callback = callback;
    }

    @Override
    protected CSV doInBackground(Void... params) {

        try {
            Document doc = Jsoup.connect(url)

                    .get();

            String result = doc.text();

            int firstDateIndex = result.indexOf("2");

            String header = result.substring(0, firstDateIndex).trim();

            String[] fields = header.split(",");

            String body = result.substring(firstDateIndex);

            body = body.replaceAll(" 202", "|202");

            String[] rows = body.split("\\|");

            return new CSV(fields, rows);
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

    }

    @Override
    protected void onPostExecute(CSV result) {

        callback.consumeCSV(result);
    }
}
