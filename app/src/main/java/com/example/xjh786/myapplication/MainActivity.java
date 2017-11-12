package com.example.xjh786.myapplication;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity  extends Activity implements SensorEventListener {


    private static final String TAG = "MainActivity";
    private TextView mTextView;
    TextView temperature;
    private TextView timeV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        temperature=((TextView)findViewById(R.id.temperatureTxtV));
        timeV = ((TextView)findViewById(R.id.timeV));
        LocationManager mlocation = (LocationManager)getSystemService(LOCATION_SERVICE);
        //Location mnowLoc = mlocation.getAllProviders();




        sensorData();
        // Enables Always-on
    }
    public void onClickSubmit(View view) {

        //String accel, String Lat, String Long, String Heart, String Temp, String timeD
        final String accel = mTextView.getText().toString();
        final String Lat = temperature.getText().toString();//etCoreId.getText().toString();
        final String Long =temperature.getText().toString();//etPassword.getText().toString();
        final String Heart = temperature.getText().toString();//etEmail.getText().toString();
        final String Temp = temperature.getText().toString();
        final String timeD = temperature.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //hidePDialog();
                Log.d(TAG, "onRegisterSubmit: response received :" + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean b_success = jsonResponse.getBoolean("success");

                    Log.d(TAG, "onRegisterSubmit: try to decode content");

                    if (b_success) {
                        Log.d(TAG, "onRegisterSubmit: success");

                        //Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        // intent.putExtra("registerSuccess", true);

                        // RegisterActivity.this.startActivity(intent);
                    } else {
                        Log.d(TAG, "onRegisterSubmit: fail");
                        //AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        //builder.setMessage("Login Failed")
                        //      .setNegativeButton("Retry", null)
                        //      .create()
                        //     .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        add registerRequest = new add(accel, Lat, Long, Heart, Temp, timeD, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(registerRequest);
    }
    private void sensorData(){
        SensorManager mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        Sensor mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }
    public void onSensorChanged(SensorEvent event) {
        timeV.setText("Time: " +currentTimeStr());
       if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            String msg = "Accel Data: " + (int)event.values[0];
            mTextView.setText(msg);
            Log.d(TAG, msg);
        }
        else if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            String msg = "Temperature: "+(int)event.values[0] + "C";
            temperature.setText(msg);
            Log.d(TAG, msg);
        }
        else
            Log.d(TAG, "Unknown sensor type");

    }
    private String currentTimeStr() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(c.getTime());
    }

    // dont know
}
