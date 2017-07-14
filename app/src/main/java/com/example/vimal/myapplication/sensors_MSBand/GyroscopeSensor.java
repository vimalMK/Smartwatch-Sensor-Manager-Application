package com.example.vimal.myapplication.sensors_MSBand;

import android.content.Context;

import com.example.vimal.myapplication.MainActivity;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandException;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.BandGyroscopeEvent;
import com.microsoft.band.sensors.BandGyroscopeEventListener;
import com.microsoft.band.sensors.SampleRate;

/**
 * Created by Vimal on 4/24/2017.
 */
public class GyroscopeSensor {

    public BandClient client= null;



    protected MainActivity context;

    public GyroscopeSensor()
    {

    }
    public GyroscopeSensor(Context context) {
        this.context = (MainActivity) context;
    }

    public void addMSBandSensors(BandClient client) throws BandException {
        this.client=client;
        client.getSensorManager().registerGyroscopeEventListener(this.listener, SampleRate.MS128);

    }

    public final BandGyroscopeEventListener listener = new BandGyroscopeEventListener() {
        @Override
        public void onBandGyroscopeChanged(BandGyroscopeEvent event) {
            updateTV(String.format(" X = %.3f \n Y = %.3f\n Z = %.3f", event.getAccelerationX(),
                    event.getAccelerationY(), event.getAccelerationZ()));
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
