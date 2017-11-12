package com.example.xjh786.myapplication;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WBF486 on 4/12/2017.
 */

public class add extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "https://amirrasyid.000webhostapp.com/Dev/adddata.php";

    private Map<String, String> param;
    private static final String TAG = "WearApp";

    public add(String accel, String Lat, String Long, String Heart, String Temp, String timeD, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        param = new HashMap<>();

        param.put("accelData", accel);
        param.put("GPSLatData", Lat);
        param.put("GPSLongData", Long);
        param.put("HearBeatData", Heart);
        param.put("TemperatureData", Temp);
        param.put("TimeData", timeD);

        Log.d(TAG, "Data Passed");
    }

    @Override
    public Map<String, String> getParams(){
        return param;
    }
}
