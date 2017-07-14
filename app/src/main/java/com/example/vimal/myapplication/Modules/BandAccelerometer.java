package com.example.vimal.myapplication.Modules;

import com.example.vimal.myapplication.MainActivity;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;

/**
 * Created by Vimal on 2/21/2017.
 */
public class BandAccelerometer {

    MainActivity ObjectMain = new MainActivity();


    public void getTimeAccData() {

         BandAccelerometerEventListener mAccelerometerEventListener = new BandAccelerometerEventListener() {
            @Override
            public void onBandAccelerometerChanged(final BandAccelerometerEvent event) {
                if (event != null) {
                    ObjectMain.appendToUI(String.format(" X = %.3f \n Y = %.3f\n Z = %.3f", event.getAccelerationX(),
                            event.getAccelerationY(), event.getAccelerationZ()));
                }
            }
        };
    }
}
