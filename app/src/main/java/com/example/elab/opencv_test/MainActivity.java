package com.example.elab.opencv_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener  {
    private SensorManager sensorManager;
    private ImageView mImageView = null;
    private Handler _handler = new Handler();
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");

    }
    public native int ConvertNative(long matAddrRgba, long matAddrGray);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.test_image);
        // Example of a call to a native method
        mImageView = findViewById(R.id.LeftImage);
        Mat img1 = new Mat();
        Mat img2 = img1.clone();
        Utils.bitmapToMat(bmp1,img1);

        //Imgproc.resize(img1, img1, new Size(),0.1, 0.1, Imgproc.INTER_CUBIC);
        //ConvertNative(img1.getNativeObjAddr(),img2.getNativeObjAddr());
        Core.absdiff(img1, new Scalar(255, 255, 255), img2);
        Utils.matToBitmap(img2,bmp1);
        Bitmap bmp2 = Bitmap.createScaledBitmap(bmp1,100,100,false);
        mImageView.setImageBitmap(bmp2);


    }

    @Override
    protected  void onResume(){
        super.onResume();
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyro != null){
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_UI);
        }
        else {
            String ns = "NoSuppoer";
        }
    }

    // 解除するコードも入れる!
    @Override
    protected void onPause() {
        super.onPause();
        // Listenerを解除
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("debug","onSensorChanged");

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float sensorX = event.values[0];
            float sensorY = event.values[1];
            float sensorZ = event.values[2];

            String strTmp = String.format(Locale.US, "Gyroscope\n " +
                    " X: %f\n Y: %f\n Z: %f",sensorX, sensorY, sensorZ);
            Log.d("debug",strTmp);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
