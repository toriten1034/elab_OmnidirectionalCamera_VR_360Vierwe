package com.example.elab.opencv_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView = null;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bmp1 = BitmapFactory.decodeResource(getResources(),R.drawable.sample_image);
        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        mImageView = findViewById(R.id.imageView1);
        Mat img1 = new Mat();
        Mat img2 = new Mat();
        Utils.bitmapToMat(bmp1,img1);
        Core.absdiff(img1,new Scalar(255,255,255),img2);
        Utils.matToBitmap(img2,bmp1);
        mImageView.setImageBitmap(bmp1);
        tv.setText("test");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
