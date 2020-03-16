package com.onipot.covid19;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSV {

    private String[] headers;

    public String[] getHeaders() {
        return headers;
    }

    private Map<Integer, String> headersMap = new LinkedHashMap<>();

    private Map<String, List<String>> rows = new LinkedHashMap<>();

    public CSV(String[] header, String[] rows){

        this.headers = header;

        for (int i = 0; i < header.length; i++) {
            headersMap.put(i, header[i].trim());
            this.rows.put(header[i].trim(), new ArrayList<>());

        }

        for (String s : rows) {

            //Log.d("ONIPOT ROW", rows[i]);
            String[] row = s.split(",");

            for (int j = 0; j < row.length; j++) {
                //Log.d("ONIPOT KEY", headers.get(j));
                List<String> tmp = this.rows.get(headersMap.get(j));
                assert tmp != null;
                tmp.add(row[j]);

            }

        }

        //Log.d("ONIPOT VAL", this.rows.get("data").get(0));

    }


    public List<String> getValues(String colName){
        return rows.get(colName);
    }


    public interface Notify{

        void consumeCSV(CSV csv);

    }

}
