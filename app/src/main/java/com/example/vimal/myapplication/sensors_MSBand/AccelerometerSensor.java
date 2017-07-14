package com.example.vimal.myapplication.sensors_MSBand;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.example.vimal.myapplication.MainActivity;
import com.example.vimal.myapplication.Modules.*;
import com.example.vimal.myapplication.R;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.BandSensorManager;
import com.microsoft.band.sensors.SampleRate;
import com.microsoft.band.BandException;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.example.vimal.myapplication.sensors_MSBand.AccelerometerSensor;
import com.google.android.gms.common.api.GoogleApiClient;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.SampleRate;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.example.vimal.myapplication.MainActivity;
import com.opencsv.CSVWriter;


/**
 * Created by Vimal on 4/24/2017.
 */
public class AccelerometerSensor extends Activity {

    public BandClient client= null;
    datacollectionAccessor datacollect=new datacollectionAccessor();


    protected MainActivity context;

    public AccelerometerSensor()
    {

    }
    public AccelerometerSensor(Context context) {
        this.context = (MainActivity) context;
    }

    public void addMSBandSensors(BandClient client) throws BandException {
            this.client=client;
        client.getSensorManager().registerAccelerometerEventListener(this.listener, SampleRate.MS128);

    }

        public final BandAccelerometerEventListener listener = new BandAccelerometerEventListener() {
        @Override
        public void onBandAccelerometerChanged(BandAccelerometerEvent event) {

            datacollect.dataCollect(event.getAccelerationX(),event.getAccelerationY(),event.getAccelerationZ());
            updateTV(String.format(" X = %.3f \n Y = %.3f\n Z = %.3f", event.getAccelerationX(),
                    event.getAccelerationY(), event.getAccelerationZ()));
           // String baseDir = getFilesDir().getAbsolutePath().toString();

        }
    };

    public void updateTV(final String str1) {

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                context.textViewXYZ.setText(str1);

            }
        });


    }
}






