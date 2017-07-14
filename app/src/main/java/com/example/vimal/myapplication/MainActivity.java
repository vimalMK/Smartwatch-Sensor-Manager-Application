package com.example.vimal.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.example.vimal.myapplication.Modules.*;
import com.google.android.gms.common.api.GoogleApiClient;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;


public class MainActivity extends AppCompatActivity   {
    private  Context context;
    private WebView mWebView;
    String tag="";
    public BandClient client = null;
    private Button ConnectButton,heart;
    private TextView  textViewStatus;
    private RadioButton test,data,alert,fall;
    public TextView textViewXYZ,heartView;
    private Switch switch1;
    String deviceName;
    String deviceMacAddress;
    AccessorCall call=new AccessorCall();
    String AccessorName;
    String AccessorCall="";
    String J2V8Access="";





    final datacollectionAccessor myobj=new datacollectionAccessor(MainActivity.this);
    final falldetectionAccessor myobj1=new falldetectionAccessor(MainActivity.this);
    final NotificationView myobj2=new NotificationView();

  //  final notication obj2=new notication(MainActivity.this);


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client1;





    @Override
   protected void onCreate(Bundle savedInstanceState)   {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectButton = (Button) findViewById(R.id.ConnectButton);
         heart = (Button) findViewById(R.id.notify);

        textViewStatus=(TextView) findViewById(R.id.textViewStatus);
        textViewXYZ=(TextView) findViewById(R.id.textViewXYZ);
        heartView=(TextView) findViewById(R.id.heartView);
        switch1=(Switch) findViewById(R.id.switch1);
        test = (RadioButton) findViewById(R.id.radioButtontest);
        data = (RadioButton) findViewById(R.id.radioButton2data);
        alert = (RadioButton) findViewById(R.id.radioButtonalert);
        fall = (RadioButton) findViewById(R.id.radioButtonfall);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d("Bluetooth Error :","Device Does not support Bluetooth");
        }
        else {

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                     deviceName = device.getName();
                     deviceMacAddress = device.getAddress(); // MAC address

                }
                System.out.println("Device Name:"+deviceName);

            }

            else
            {
                Log.d("Bluetooth Error :","Not Paired Devices");

            }
        }
        if (client == null) {
            BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
            if (devices.length == 0) {
                appendToUI("Band isn't paired with your phone.\n");

            }
            client = BandClientManager.getInstance().create(getBaseContext(), devices[0]);
        } else if (ConnectionState.CONNECTED == client.getConnectionState()) {

        }


        ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStatus.setText("");
                //call.getAccessor('BandAccelerometer');
               new AccelerometerSubscriptionTask().execute();





            }
        });
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call.getAccessor('BandAccelerometer');

                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                J2V8Access="testAccessor.js";
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                J2V8Access="datacollectionAccessor.js";
            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                J2V8Access="alertAccessor.js";
            }
        });
        fall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                J2V8Access="fallDetectionAccessor.js";
            }
        });

      mWebView = (WebView) findViewById(R.id.activity_main_webview);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
       // mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new WebViewJavaScriptInterface(this), "app");

        // Stop local links and redirects from opening in browser instead of WebView
        mWebView.setWebViewClient(new MyAppWebViewClient());
        mWebView.setWebViewClient(new WebViewClient());    //the lines of code added
        mWebView.setWebChromeClient(new WebChromeClient()); //same as above

      //mWebView.loadUrl("file:///android_asset/page.html");
     //   mWebView.setVisibility(View.GONE);
       mWebView.loadUrl("file:///android_asset/index.html");

      /*  try {
            V8 runtime = V8.createV8Runtime();
            // String res=ReadJS();
            String result = runtime.executeStringScript(ReadJS());
            runtime.release();
            Log.d(tag, result);

        }
        catch (Exception e)
        {

        }

*/
    }


    //notification


    //########Connection Status module########
    public BandAccelerometerEventListener mAccelerometerEventListener = new BandAccelerometerEventListener() {
        @Override
        public void onBandAccelerometerChanged(final BandAccelerometerEvent event) {
            if (event != null) {
               appendToUIAttribute(String.format(" X = %.3f \n Y = %.3f\n Z = %.3f", event.getAccelerationX(),
                        event.getAccelerationY(), event.getAccelerationZ()));
            }
        }
    };

    public class AccelerometerSubscriptionTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (getConnectedBandClient()) {
                    appendToUI("Microsoft Band 2  is connected. \n");
                    appendSwitch();
                   callAccessor();
                } else {
                    appendToUI("Band isn't connected. Please make sure bluetooth is on and the band is in range.\n");
                }
            } catch (BandException e) {
                String exceptionMessage="";
                switch (e.getErrorType()) {
                    case UNSUPPORTED_SDK_VERSION_ERROR:
                        exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.\n";
                        break;
                    case SERVICE_ERROR:
                        exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.\n";
                        break;
                    default:
                        exceptionMessage = "Unknown error occured: " + e.getMessage() + "\n";
                        break;
                }
                appendToUI(exceptionMessage);

            } catch (Exception e) {
                appendToUI(e.getMessage());
            }
            return null;
        }




    }

    public void callAccessor() throws BandException
    {

        if(AccessorName.equals("datacollectionAccessor")) {
            myobj.executeAccessor(client);
        }
         if(AccessorName.equals("alertAccessor"))
        {
            notificationShow("alertAccessor Plugged in!!!!!");
        }
        if(AccessorName.equals("testAccessor"))
        {

        }
        if(AccessorName.equals("fallDetectionAccessor"))
        {
            myobj1.execu();
        }

    }



    public static class WebViewJavaScriptInterface{

        private Context context;

        /*
         * Need a reference to the context in order to sent a post message
         */
        public WebViewJavaScriptInterface(Context context){
            this.context = context;
        }

        /*
         * This method can be called from Android. @JavascriptInterface
         * required after SDK version 17.
         */
        @JavascriptInterface
        public  void disp()
        {

            System.out.println("Method call from JAVA into JS and compiling with J2V8 . working!!!!!");
        }
        public  void display(String s)
        {

            System.out.println(s);
        }

        public  String input(final String inputname,final String type)
        {

            String s="Java Connected to JS -STATUS OK!";
            return inputname+"and"+type;
        }
        public  String getAccessorModule(String AccessorName)
        {

            return AccessorName;
        }





    }

      public void predict(int count, String filePath){
        boolean fall = false;  //used to keep track if any of the instances are a fall.
        int start = 0;  //used to keep track of where in the csv file to start predicting from
        File fallData = new File(filePath);// + "/default.csv");
        System.out.println("----------Collecting Data----------");
        System.out.println("Save path is: " + fallData.getAbsolutePath());

        try{
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(fallData.getAbsolutePath());
            System.out.println(fallData.getAbsolutePath());
            Instances predictionData = source.getDataSet();                      //getting csv to predict from
            System.out.println(predictionData.toSummaryString());
            System.out.println(source.getDataSet().toString());
            System.out.println(start + " to " + predictionData.numInstances());
            System.out.println("this many "+count);
            count = predictionData.numInstances();
            //for every new group of samples we receive we will predict each instance
            for (int i = start; i < predictionData.numInstances(); ++i){
                try {
                    //set which column will be used for prediction
                    if (predictionData.classIndex() == -1)
                        predictionData.setClassIndex(predictionData.numAttributes() - 1);
                    Instance newinst = predictionData.instance(i);
                    System.out.println("fall predict newinst: " + newinst.toString());
                    try {
                        //get model from assets that will be used for prediction
                        AssetManager assetManager = getAssets();
                        InputStream is = assetManager.open("fallsvmmodel.model");
                        if (is == null)
                            System.out.println("fallprediction: " + "not opening model");
                        //read in that model.
                        Classifier newsvm = (Classifier) weka.core.SerializationHelper.read(is);
                        if (is == null)
                            System.out.println("fallprediction: " + "not reading in model");
                        else
                            System.out.println("fallprediction: " + newsvm.toString());
                        //predict instance
                        double prediction = newsvm.classifyInstance(newinst);
                        if (is == null)
                            System.out.println("fallprediction: " + "having trouble predicting");
                        //get string representation of prediction
                        String predictionoutput = predictionData.classAttribute().value((int) prediction);
                        if (predictionoutput == "")
                            System.out.println("fallprediction: " + "trouble converting to string");
                        System.out.println("*****This was predicted: " + predictionoutput);
                        //test if prediction was fall, if so set the final prediction to fall.
                        if (predictionoutput.equals("fall") || predictionoutput.equals(" fall") || predictionoutput.equals("fall "))
                            fall = true;

                    } catch (Exception e) {
                        System.out.println("Prediction: " + "could not find model");
                    }
                }catch (Exception e) {
                    System.out.println("Prediction: " + "found source, trouble working with it.");
                }}
        }catch (Exception e){
            System.out.println("Prediction: " + "could not find data source" + e.getMessage());
        }
        //see if any of the predictions were fall then write it to the console
        if (fall == true) {
            System.out.println("*********************************");
            System.out.println("***********" + " FALL" + "************");
            System.out.println("*********************************");
            notificationShow("FALL");
        } else {
            System.out.println("*********************************");
            System.out.println("***********" + " NO FALL" + "*************");
            System.out.println("*********************************");
        }

        start = count;
    }


    JavaCallback display = new JavaCallback() {
        @Override
        public Object invoke(V8Object v8Object, V8Array v8Array) {
            String s="Java Connected to JS -STATUS OK!";
            return s;
        }
    };


    public void ReadJS( View v) throws IOException
    {
        V8 runtime = V8.createV8Runtime();
        runtime.registerJavaMethod(display,"display");
        WebViewJavaScriptInterface w=new WebViewJavaScriptInterface(this);
        V8Object v8Con=new V8Object(runtime);
        runtime.add("w",v8Con);
        String script=ReadRawJS();
        v8Con.registerJavaMethod(display,"display");
        v8Con.registerJavaMethod(w, "disp", "disp",new Class<?>[] { });
        v8Con.registerJavaMethod(w, "display", "display",new Class<?>[] {String.class  });
        v8Con.registerJavaMethod(w, "getAccessorModule", "getAccessorModule",new Class<?>[] {String.class });
        v8Con.registerJavaMethod(w, "input", "input",new Class<?>[] {String.class ,String.class  });
       runtime.executeScript(script);
        String result=runtime.executeStringFunction("fire",null);
        checkAccessorPlug(result);

       // runtime.executeVoidFunction("display",null);
         v8Con.release();


            }


 public void checkAccessorPlug(String plugStatus)
    {

    AccessorName=plugStatus;
    System.out.println(AccessorName);

    }
public String ReadRawJS() throws IOException
{String str="";
    StringBuffer buf = new StringBuffer();
    AssetManager assetManager = getAssets();
    InputStream input;
    String strLine;
    String script="";

    try {
        input = assetManager.open("lib/"+J2V8Access);


        BufferedReader br = new BufferedReader(new InputStreamReader(input));



        //Read File Line By Line
        while ((strLine = br.readLine()) != null)   {
            // Print the content on the console

            script =script+strLine;

        }

        //Close the input stream
        br.close();
        System.out.println(script);





    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    return script;

}

    public void notificationShow(String status)
    {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.n_y)
                        .setContentTitle("NOTIFICATION!!")
                        .setContentText(status);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, NotificationView.class);
        PendingIntent resultPendingIntent =PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }



    public void appendToUI(final String string) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewStatus.setText(string);
            }
        });
    }
    public void appendToUIAttribute(final String string) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewXYZ.setText(string);
            }
        });
    }

    public void appendSwitch() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch1.setChecked(true);
            }
        });
    }

    public boolean getConnectedBandClient() throws InterruptedException, BandException {
        if (client == null) {
            BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
            if (devices.length == 0) {
                appendToUI("Band isn't paired with your phone.\n");
                return false;
            }
            client = BandClientManager.getInstance().create(getBaseContext(), devices[0]);
        } else if (ConnectionState.CONNECTED == client.getConnectionState()) {
            return true;
        }

        appendToUI("Band is connecting...\n");
        return ConnectionState.CONNECTED == client.connect().await();
    }



   /* private class mWebView extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }*/
}



