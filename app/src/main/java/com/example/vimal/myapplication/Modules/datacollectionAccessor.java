package com.example.vimal.myapplication.Modules;

/**
 * Created by Vimal on 4/24/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.example.vimal.myapplication.MainActivity;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandException;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.SampleRate;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import com.example.vimal.myapplication.sensors_MSBand.*;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.example.vimal.myapplication.MainActivity;
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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.example.vimal.myapplication.MainActivity;
import com.opencsv.CSVWriter;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;


/**
 * Created by Vimal on 4/24/2017.
 */
public class datacollectionAccessor extends Activity {

    public BandClient client= null;
    final boolean[] fileCreated = { false };
    final int sampleCount = 240;
    final ArrayList<float[]> accValues = new ArrayList<>();
    falldetectionAccessor fd=new falldetectionAccessor();
    AssetManager assetManagerfinal;


    public MainActivity context;

    public datacollectionAccessor()
    {

    }
    public datacollectionAccessor(Context context) {
        this.context = (MainActivity) context;
    }

    public float max(float[] input){
        float output = -1;
        for (float i : input){
            if (i>output){ output = i; }
        }
        return output;
    }

    public float min(float[] input){
        float output = Float.MAX_VALUE;
        for (float i : input){
            if (i<output){ output = i; }
        }
        return output;
    }

    public float resultant(float[] input){
        return ((float) Math.sqrt((input[0]*input[0]) + (input[1]*input[1]) + (input[2]*input[2])));
    }
    private void copyFile(String inputPath, String inputFile, String outputPath) {


        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath+"/"+inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }
    public void dataCollect(float x,float y,float z) {
       // System.out.println(x + "/" + y + "/" + z);


        if (accValues.size() < sampleCount) {
            System.out.println("1");
            float[] data = {0, 0, 0};
            data[0] = x;
            data[1] = y;
            data[2] = z;
            accValues.add(data);

            System.out.println("Creating CSV file");

            String baseDir ="/data/user/0/com.example.vimal.myapplication/files";


            String fileName = "AccelerationData.csv";
            String outputPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath().toString();
            System.out.println("outputPath" + outputPath);
            String filePath = baseDir + File.separator + fileName;
            String[] dataString = {"0", "0", "0", "0", "not fall"};
            File f = new File(filePath);
            f.delete();
            CSVWriter writer = null;
            // File exist
            if (f.exists() && !f.isDirectory()) {
                FileWriter mFileWriter;
                try {
                    mFileWriter = new FileWriter(filePath, false);
                    writer = new CSVWriter(mFileWriter);
                } catch (Exception ex) {
                    System.out.println("Failed to create FileWriter");
                }
            } else {
                try {
                    writer = new CSVWriter(new FileWriter(filePath));
                } catch (Exception ex) {
                    System.out.println("Failed to create file");
                }
            }

            if (writer != null) {
                try {
                    String[] header = {"resultant", "cvfast", "smax", "smin", "outcome"};
                    float[] previousDatapoint1 = {0, 0, 0};
                    float[] previousDatapoint2 = {0, 0, 0};
                    float previousResultant1 = 0;
                    float previousResultant2 = 0;
                    float[] lastThreeResultants = {0, 0, 0};
                    writer.writeNext(header);
                    for (float[] datapoint : accValues) {
                        lastThreeResultants[0] = resultant(datapoint);
                        lastThreeResultants[1] = previousResultant1;
                        lastThreeResultants[2] = previousResultant2;
                        dataString[0] = Float.toString(lastThreeResultants[0]);
                        dataString[1] = Float.toString(max(lastThreeResultants) - min(lastThreeResultants));
                        dataString[2] = Float.toString(max(lastThreeResultants));
                        dataString[3] = Float.toString(min(lastThreeResultants));
                        writer.writeNext(dataString);
                        previousResultant2 = previousResultant1;
                        previousResultant1 = resultant(datapoint);
                        System.arraycopy(previousDatapoint1, 0, previousDatapoint2, 0, 3);
                        System.arraycopy(datapoint, 0, previousDatapoint1, 0, 3);
                        if (dataString[4] == "fall") {
                            dataString[4] = "not fall";
                        } else if (dataString[4] == "not fall") {
                            dataString[4] = "fall";
                        }
                    }
                    //  displayFromThread("CSV File Created and written");
                    fileCreated[0] = true;
                } catch (Exception ex) {
                    System.out.println("Failed to write data from array");
                } finally {
                    try {
                        writer.close();
                    } catch (Exception ex) { /* do nothing */ }
                }
            }
           copyFile(filePath, fileName, outputPath);

            //for fall detection Prediction
//predict(sampleCount,filePath);
        }
    }


    public void executeAccessor(BandClient client)throws BandException
    {
       //call respective sensor
       // assetManagerfinal=assetManager;
        this.client=client;
        addMSBandSensors(client);

    }

    public void addMSBandSensors(BandClient client) throws BandException {
        this.client=client;
        client.getSensorManager().registerAccelerometerEventListener(this.listener, SampleRate.MS128);

    }

    public final BandAccelerometerEventListener listener = new BandAccelerometerEventListener() {
        @Override
        public void onBandAccelerometerChanged(BandAccelerometerEvent event) {

           dataCollect(event.getAccelerationX(),event.getAccelerationY(),event.getAccelerationZ());
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







