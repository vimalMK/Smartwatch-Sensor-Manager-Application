package com.example.vimal.myapplication.Modules;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.vimal.myapplication.MainActivity;
import com.example.vimal.myapplication.R;

/**
 * Created by Vimal on 7/5/2017.
 */
import android.os.Bundle;

public class NotificationView extends Activity{

    MainActivity activity;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }


}