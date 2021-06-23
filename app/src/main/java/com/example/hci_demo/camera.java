package com.example.hci_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class camera extends AppCompatActivity {


    static Camera camera;
    CameraPreview cameraPreview;
    private CameraHandlerThread mThread = null;
    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Log.d("","camera activity create0000");

        newOpenCamera();
        camera.setDisplayOrientation(90);
        if(camera==null){
            Log.d("camera","is null");
        }else {
            Log.d("camera","not null");
        }
        SurfaceView my_view=findViewById(R.id.surfaceView3);
        cameraPreview=new CameraPreview(this,camera);




        Button btn=findViewById(R.id.capture);

        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
//                take picture function
            }
        });
        Button infoBtn=findViewById(R.id.info);
        infoBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {


            }
        });
    }

    @Override
    protected  void onResume() {
        super.onResume();
        Log.d("onresume","123456");


//        camera=Camera.open();
//        FrameLayout cpreview=findViewById(R.id.camera_preview);
//        cameraPreview=new CameraPreview(this,camera);
//        cpreview.addView(cameraPreview);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void openFrontFacingCameraGingerbread() {
        try{
            int cameraCount = 0;
            Camera cam = null;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    try {
                        cam = Camera.open(camIdx);
                    } catch (RuntimeException e) {
                        Log.e("openFrontCamera", "Camera failed to open: " + e.getLocalizedMessage());
                    }
                }
            }

            camera= cam;}
        catch (RuntimeException e) {
            Log.e("", "failed to open front camera");
        }
    }

    private static void oldOpenCamera() {
        try {
            camera = Camera.open(0);
        }
        catch (RuntimeException e) {
            Log.e("", "failed to open front camera");
        }
    }
    private void newOpenCamera() {
        Log.d("","newOpenCamera2");
        if (mThread == null) {
            mThread = new CameraHandlerThread();
        }

        synchronized (mThread) {
            mThread.openCamera();
        }
    }


    private static class CameraHandlerThread extends HandlerThread {
        Handler mHandler = null;

        CameraHandlerThread() {
            super("CameraHandlerThread");
            start();
            mHandler = new Handler(getLooper());
        }

        synchronized void notifyCameraOpened() {
            notify();
        }

        void openCamera() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    oldOpenCamera();
                    notifyCameraOpened();
                }
            });
            try {
                wait();
            }
            catch (InterruptedException e) {
                Log.w("CameraHandlerThread", "wait was interrupted");
            }
        }
    }



}