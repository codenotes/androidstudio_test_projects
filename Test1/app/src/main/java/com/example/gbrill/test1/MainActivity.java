package com.example.gbrill.test1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.DiscoveryListener;
import android.net.nsd.NsdManager.ResolveListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.Reader;


public class MainActivity extends AppCompatActivity implements SensorEventListener,

                DiscoveryListener,
              ResolveListener

{

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private Sensor senGyro;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private String result;

    private NsdManager   manager;
    private String       serviceType;

    //

    private static final String TAG = "AndroidTalker";

    float ax,ay,az,gx,gy,gz;

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }




    @Override
    public void onDiscoveryStarted(String theServiceType)
    {
        Log.d(TAG, "onDiscoveryStarted");
    }

    @Override
    public void onStartDiscoveryFailed(String theServiceType, int theErrorCode)
    {
        Log.d(TAG, "onStartDiscoveryFailed(" + theServiceType + ", " + theErrorCode);
    }

    @Override
    public void onDiscoveryStopped(String serviceType)
    {
        Log.d(TAG, "onDiscoveryStopped");
    }

    @Override
    public void onStopDiscoveryFailed(String theServiceType, int theErrorCode)
    {
        Log.d(TAG, "onStartDiscoveryFailed(" + theServiceType + ", " + theErrorCode);
    }


    private class MyResolveListener implements NsdManager.ResolveListener {
        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            Log.d(TAG, "onResolveFailed(" + serviceInfo + ", " + errorCode);
        }

        @Override
        public void onServiceResolved(NsdServiceInfo theServiceInfo) {
            Log.d(TAG, "onServiceResolved(" + theServiceInfo + ")");
            Log.d(TAG, "name == " + theServiceInfo.getServiceName());
            Log.d(TAG, "type == " + theServiceInfo.getServiceType());
            Log.d(TAG, "host == " + theServiceInfo.getHost());
            Log.d(TAG, "port == " + theServiceInfo.getPort());
        }
    }

    private void serviceFound(NsdServiceInfo theServiceInfo)
    {
        //manager.resolveService(theServiceInfo, this);

        manager.resolveService(theServiceInfo, new MyResolveListener());

    }

    @Override
    public void onServiceFound(NsdServiceInfo theServiceInfo)
    {
        Log.d(TAG, "onServiceFound(" + theServiceInfo + ")");
        Log.d(TAG, "name == " + theServiceInfo.getServiceName());
        Log.d(TAG, "type == " + theServiceInfo.getServiceType());
        serviceFound(theServiceInfo);
    }

    @Override
    public void onServiceLost(NsdServiceInfo theServiceInfo)
    {
        Log.d(TAG, "onServiceLost(" + theServiceInfo + ")");
    }

    // Resolve Listener

    @Override
    public void onServiceResolved(NsdServiceInfo theServiceInfo)
    {
        Log.d(TAG, "onServiceResolved(" + theServiceInfo + ")");
        Log.d(TAG, "name == " + theServiceInfo.getServiceName());
        Log.d(TAG, "type == " + theServiceInfo.getServiceType());
        Log.d(TAG, "host == " + theServiceInfo.getHost());
        Log.d(TAG, "port == " + theServiceInfo.getPort());
    }

    @Override
    public void onResolveFailed(NsdServiceInfo theServiceInfo, int theErrorCode)
    {
        Log.d(TAG, "onResolveFailed(" + theServiceInfo + ", " + theErrorCode);
    }












    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {

       // result = String.format("X:%.2f Y:%.2f Z:%.2f", last_x, last_y,last_z );
        TextView tv=(TextView)findViewById(R.id.txt02);
    //    tv.setText(result);
        Sensor mySensor = sensorEvent.sensor;


        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE)
        {
            //result=String.format("Roll:%.2f\nPitch:%.2f\nYaw:%.2f\n",
             //       sensorEvent.values[2],
              //      sensorEvent.values[1],
               //     sensorEvent.values[0]
                //    );

            gx=sensorEvent.values[2];
            gy=sensorEvent.values[1];
            gz=sensorEvent.values[0];


            //result="Orientation X (Roll) :"+ Float.toString(sensorEvent.values[2]) +"\n"+
              //      "Orientation Y (Pitch) :"+ Float.toString(sensorEvent.values[1]) +"\n"+
                //    "Orientation Z (Yaw) :"+ Float.toString(sensorEvent.values[0]);

            //tv.setText(result);
         //   Log.i("AAA","boobs3");
        }
        else
        if ((mySensor.getType() == Sensor.TYPE_ACCELEROMETER))
         {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            ax= sensorEvent.values[0];
            ay= sensorEvent.values[1];
            az= sensorEvent.values[2];



         //    Log.i("AAA","boobs4");
      //       result +="\n";
            result += String.format("X:%.2f\nY:%.2f\nZ:%.2f\n",
                     last_x,
                     last_y,
                     last_z );



             long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;


                if (speed > SHAKE_THRESHOLD)
                {



                    tv=(TextView)findViewById(R.id.txt02);
                    tv.setText("shake");

                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

        result = String.format("Roll:%.2f\nPitch:%.2f\nYaw:%.2f\nX:%.2f\nY:%.2f\nZ:%.2f\n",
                gx,
                gy,
                gz,
                ax,
                ay,
                az
                );



        tv.setText(result);
        //result="";

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }





    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        manager     = (NsdManager)getSystemService(NSD_SERVICE);
        serviceType =  "_http._tcp";

        manager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv=(TextView)findViewById(R.id.txt02);
        tv.setText("boobs2");

     //   NsdManager nsdMgr = (NsdManager) getApplicationContext().getSystemService(Context.NSD_SERVICE);
     //   nsdMgr.discoverServices("_http._tcp.local.", NsdManager.PROTOCOL_DNS_SD, discoveryListener);


        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senGyro = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        senSensorManager.registerListener(this, senGyro , SensorManager.SENSOR_DELAY_NORMAL);


//Toast.makeText(getApplicationContext(), "Sample Text", Toast.LENGTH_LONG).show();
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String s;



                    new Thread() {
                        public void run() {
                            try
                            {
                                downloadUrl("http://10.0.0.150:8888/?CommandID=INSERT%20INTO%20Friends%20(UID,FUID)%20VALUES%20(%22foo!!%22,%20%22bar!!%22);token=allgood");
                            } catch (java.io.IOException io)
                            {

                            }

                        }

                    }.start();




                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });







    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
