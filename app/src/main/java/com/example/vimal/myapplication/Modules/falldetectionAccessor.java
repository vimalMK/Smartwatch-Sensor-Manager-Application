package com.example.vimal.myapplication.Modules;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.InputStream;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.converters.ConverterUtils;
import android.app.Activity;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

// These are for prediction functionality
import com.example.vimal.myapplication.MainActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandInfo;
import com.microsoft.band.BandIOException;
import com.microsoft.band.BandPendingResult;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.SampleRate;
import com.opencsv.CSVWriter;

import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import android.content.res.AssetManager;


/**
 * Created by Vimal on 6/8/2017.
 */
public class falldetectionAccessor extends Activity {

    public MainActivity context;
   // context= (MainActivity) context
int i=0;
    public BandClient client= null;
    public falldetectionAccessor()
    {

    }
    public falldetectionAccessor(Context context) {
        this.context = (MainActivity) context;
    }

public void execu()
    {
        String baseDir ="/data/user/0/com.example.vimal.myapplication/files";


        String fileName = "AccelerationData.csv";
        String outputPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath().toString();
        System.out.println("outputPath" + outputPath);
        String filePath = baseDir + File.separator + fileName;


            context.predict(240, filePath);

        }
    }


