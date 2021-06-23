package com.example.hci_demo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class camara2 extends AppCompatActivity {




    private setting_state app_state=MainActivity.app_state;
    private Button btnCapture;
    private TextureView textureView;
    private int[] passwdImgNum=new int[9];
    ImageView []iv=new ImageView[9];

    int curpos;
    //Check state orientation of output image
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static{
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    private String cameraId;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSessions;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;

    //Save to FILE
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    private void set_view_img(ImageView v,int n){
        if(n==0)v.setImageResource(R.drawable.mouse0);
        else if(n==1)v.setImageResource(R.drawable.mouse1);
        else if(n==2)v.setImageResource(R.drawable.mouse2);
        else if(n==3)v.setImageResource(R.drawable.mouse3);
        else if(n==4)v.setImageResource(R.drawable.mouse4);
        else if(n==5)v.setImageResource(R.drawable.mouse5);
    }
    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            cameraDevice.close();
            cameraDevice=null;
        }
    };

    private void create_passwdimg(){
        for(int i=0;i<9;i++){
            Random rand = new Random(); //instance of random class
            int upperbound = 4;
            //generate random values from 0-24
            int int_random = rand.nextInt(upperbound)+2;
            passwdImgNum[i]=int_random;
        }
    }
    private void check_passwd(int n){
        Log.d("checking passwd","ing");

        if(app_state.mode==0){
            //easy mode
            Log.d("check","easymode");
            if(passwdImgNum[4]==n){
                Log.d("easymode","right");
                //iv[4].setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                curpos++;
            }else{
                Log.d("easymode","wrong");
            }
        }else{
            Log.d("check","hardmode");
            if(passwdImgNum[app_state.gesture_passwd.get(curpos)]==n){

                //iv[app_state.gesture_passwd.get(curpos)].setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                iv[app_state.gesture_passwd.get(curpos)].setImageDrawable(null);
                curpos++;
                Log.d("hardmode","right");
            }else{
                Log.d("hardmode","wrong");
            }
        }
        if((app_state.mode==1 && curpos>=app_state.gesture_passwd.size()) || (app_state.mode==0 && curpos==1)){
            //完成解鎖
            new AlertDialog.Builder(camara2.this)
                    .setTitle("完成解鎖!")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(camara2.this ,new_menu.class);
                            startActivity(intent);
                        }
                    }).show();
        }


    }
    private void setup_passwd_and_img(){
        curpos=0;

        iv[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("texture","onclick");
                takePicture();
            }
        });
        if(app_state.mode==0){
            //easy mode
            Random rand = new Random(); //instance of random class
            int upperbound = 6;
            //generate random values from 0-24
            int int_random = rand.nextInt(upperbound);
            passwdImgNum[4]=int_random;

            passwdImgNum[0]=-1;
            passwdImgNum[1]=-1;
            passwdImgNum[2]=-1;
            passwdImgNum[3]=-1;
            passwdImgNum[5]=-1;
            passwdImgNum[6]=-1;
            passwdImgNum[7]=-1;
            passwdImgNum[8]=-1;
        }else{
            //hard mode
            create_passwdimg();
        }

        for(int i=0;i<9;i++){
            set_view_img(iv[i],passwdImgNum[i]);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara2);
        curpos=0;
        iv[0]=findViewById(R.id.pos0);
        iv[1]=findViewById(R.id.pos1);
        iv[2]=findViewById(R.id.pos2);
        iv[3]=findViewById(R.id.pos3);
        iv[4]=findViewById(R.id.pos4);
        iv[5]=findViewById(R.id.pos5);
        iv[6]=findViewById(R.id.pos6);
        iv[7]=findViewById(R.id.pos7);
        iv[8]=findViewById(R.id.pos8);

        textureView = (TextureView)findViewById(R.id.textureView);
        //From Java 1.4 , you can use keyword 'assert' to check expression true or false
//        assert textureView != null;



        if(app_state.mode==1){
            if(app_state.gesture_passwd.size()==0){
                new AlertDialog.Builder(camara2.this)
                        .setTitle("請先設定密碼")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClass(camara2.this ,setting_gesture_passwd.class);
                                startActivity(intent);
                            }
                        }).show();
                return;
            }

        }

        textureView.setSurfaceTextureListener(textureListener);
        for(int i=0;i<9;i++){
            iv[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("iv","onclick");
                    takePicture();
                }
            });
        }
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("texture","onclick");
                takePicture();
            }
        });
        iv[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("texture","onclick");
                takePicture();
            }
        });

        setup_passwd_and_img();

//        btnCapture = (Button)findViewById(R.id.btnCapture);
//        btnCapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takePicture();
//            }
//        });

    }

    private void takePicture() {
        if(cameraDevice == null)
            return;
        CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if(characteristics != null)
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                        .getOutputSizes(ImageFormat.JPEG);

            //Capture image with custom size
            int width = 640;
            int height = 480;
            if(jpegSizes != null && jpegSizes.length > 0)
            {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            final ImageReader reader = ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
            List<Surface> outputSurface = new ArrayList<>(2);
            outputSurface.add(reader.getSurface());
            outputSurface.add(new Surface(textureView.getSurfaceTexture()));

            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            //Check orientation base on device
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));

            file = new File(Environment.getExternalStorageDirectory()+"/"+UUID.randomUUID().toString()+".jpg");
            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader imageReader) {
                    Image image = null;
                    try{
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
                        httpUrlConnPost(encoded);
                        save(bytes);

                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        {
                            if(image != null)
                                image.close();
                        }
                    }
                }
                private void save(byte[] bytes) throws IOException {
                    OutputStream outputStream = null;
                    try{
                        outputStream = new FileOutputStream(file);
                        outputStream.write(bytes);
                    }finally {
                        if(outputStream != null)
                            outputStream.close();
                    }
                }
            };

            reader.setOnImageAvailableListener(readerListener,mBackgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    Toast.makeText(camara2.this, "Saved "+file, Toast.LENGTH_SHORT).show();
                    createCameraPreview();
                    Log.d("finish cpature",file.toString());
                }
            };

            cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    try{
                        cameraCaptureSession.capture(captureBuilder.build(),captureListener,mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                }
            },mBackgroundHandler);


        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void createCameraPreview() {
        try{
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert  texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(),imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if(cameraDevice == null)
                        return;
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(camara2.this, "Changed", Toast.LENGTH_SHORT).show();
                }
            },null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if(cameraDevice == null)
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE,CaptureRequest.CONTROL_MODE_AUTO);
        try{
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(),null,mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    private void openCamera() {
        CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try{
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            //Check realtime permission if run higher API 23
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId,stateCallback,null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You can't use camera without permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        setup_passwd_and_img();
        if(textureView.isAvailable())
            openCamera();
        else
            textureView.setSurfaceTextureListener(textureListener);
    }

    @Override
    protected void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try{
            mBackgroundThread.join();
            mBackgroundThread= null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }
    public int httpUrlConnPost(String stringimg){
        HttpURLConnection urlConnection = null;
        URL url = null;
        int result=-1;
        try {


            url = new URL("http://140.114.252.108:5000/uploader");
            Log.d("url",url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();//開啟http連線
            urlConnection.setConnectTimeout(3000);//連線的超時時間
            urlConnection.setUseCaches(false);//不使用快取
            //urlConnection.setFollowRedirects(false);是static函式，作用於所有的URLConnection物件。
            urlConnection.setInstanceFollowRedirects(true);//是成員函式，僅作用於當前函式,設定這個連線是否可以被重定向
            urlConnection.setReadTimeout(5000);//響應的超時時間
            urlConnection.setDoInput(true);//設定這個連線是否可以寫入資料
            urlConnection.setDoOutput(true);//設定這個連線是否可以輸出資料
            urlConnection.setRequestMethod("POST");//設定請求的方式
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//設定訊息的型別
            urlConnection.connect();// 連線，從上述至此的配置必須要在connect之前完成，實際上它只是建立了一個與伺服器的TCP連線
            JSONObject json = new JSONObject();//建立json物件
//            json.put("name", URLEncoder.encode(name, "UTF-8"));//使用URLEncoder.encode對特殊和不可見字元進行編碼
//            json.put("password", URLEncoder.encode(password, "UTF-8"));//把資料put進json物件中
            json.put("img",stringimg);
            String jsonstr = json.toString();//把JSON物件按JSON的編碼格式轉換為字串
            //-------------使用位元組流傳送資料--------------
            //OutputStream out = urlConnection.getOutputStream();
            //BufferedOutputStream bos = new BufferedOutputStream(out);//緩衝位元組流包裝位元組流
            //byte[] bytes = jsonstr.getBytes("UTF-8");//把字串轉化為位元組陣列
            //bos.write(bytes);//把這個位元組陣列的資料寫入緩衝區中
            //bos.flush();//重新整理緩衝區，傳送資料
            //out.close();
            //bos.close();
            //------------字元流寫入資料------------
            OutputStream out = urlConnection.getOutputStream();//輸出流，用來發送請求，http請求實際上直到這個函式裡面才正式傳送出去
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));//建立字元流物件並用高效緩衝流包裝它，便獲得最高的效率,傳送的是字串推薦用字元流，其它資料就用位元組流
            bw.write(jsonstr);//把json字串寫入緩衝區中
            bw.flush();//重新整理緩衝區，把資料傳送出去，這步很重要
            out.close();
            bw.close();//使用完關閉

            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){//得到服務端的返回碼是否連線成功
                //------------位元組流讀取服務端返回的資料------------
                //InputStream in = urlConnection.getInputStream();//用輸入流接收服務端返回的迴應資料
                //BufferedInputStream bis = new BufferedInputStream(in);//高效緩衝流包裝它，這裡用的是位元組流來讀取資料的，當然也可以用字元流
                //byte[] b = new byte[1024];
                //int len = -1;
                //StringBuffer buffer = new StringBuffer();//用來接收資料的StringBuffer物件
                //while((len=bis.read(b))!=-1){
                //buffer.append(new String(b, 0, len));//把讀取到的位元組陣列轉化為字串
                //}
                //in.close();
                //bis.close();
                //Log.d("zxy", buffer.toString());//{"json":true}
                //JSONObject rjson = new JSONObject(buffer.toString());//把返回來的json編碼格式的字串資料轉化成json物件
                //------------字元流讀取服務端返回的資料------------
                InputStream in = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while((str = br.readLine())!=null){//BufferedReader特有功能，一次讀取一行資料
                    buffer.append(str);
                }
                in.close();
                br.close();
                JSONObject rjson = new JSONObject(buffer.toString());
                result=rjson.getInt("result");
                check_passwd(result);
                Log.d("zxy66776", "rjson="+rjson);//rjson={"json":true}
//                boolean result = rjson.getBoolean("json");//從rjson物件中得到key值為"json"的資料，這裡服務端返回的是一個boolean型別的資料

            }else{
                Log.d("",Integer.toString(urlConnection.getResponseCode()));
            }
        } catch (Exception e) {

        }finally{
            urlConnection.disconnect();//使用完關閉TCP連線，釋放資源
            return result;
        }
    }
}