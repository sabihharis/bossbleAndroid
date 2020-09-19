package com.bossble.bossble.CameraWork;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.provider.Settings;
/*
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
*/
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.Campaigns.Create_Campaign_Acitivity;
import com.bossble.bossble.Challenges.Create_Challenge_Acitivity;
import com.bossble.bossble.Challenges.Text_Challenge_Activity;
import com.bossble.bossble.Constant.Constant;
import com.bossble.bossble.Home.Home_Activity;
import com.bossble.bossble.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Camera_Activity2 extends AppCompatActivity {

    ImageView captureimg, flash_icon,gallery,text;
    TextureView textureview;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


    private static final SparseIntArray ORIENTATIONS2 = new SparseIntArray();

    static {
        /*ORIENTATIONS2.append(Surface.ROTATION_0, 180);
        ORIENTATIONS2.append(Surface.ROTATION_90, 270);
        ORIENTATIONS2.append(Surface.ROTATION_180, 0);
        ORIENTATIONS2.append(Surface.ROTATION_270, 90);*/

/*        ORIENTATIONS2.append(Surface.ROTATION_0, 270);
        ORIENTATIONS2.append(Surface.ROTATION_90, 180);
        ORIENTATIONS2.append(Surface.ROTATION_180, 90);
        ORIENTATIONS2.append(Surface.ROTATION_270, 0);*/

        ORIENTATIONS2.append(Surface.ROTATION_0, 270);
        ORIENTATIONS2.append(Surface.ROTATION_90, 90);
        ORIENTATIONS2.append(Surface.ROTATION_180, 180);
        ORIENTATIONS2.append(Surface.ROTATION_270, 0);
    }



    String camid;
    CameraDevice cameraDevice;
    CameraDevice cameraDevice2;
    CameraManager cameraManager;
    CameraCaptureSession cameraCaptureSession;
    CaptureRequest captureRequest;
    CaptureRequest.Builder captureRequestBuilder;
    Size imageDimensions;
    ImageReader imageReader;
    File file;
    Handler backgroundHandler;
    HandlerThread handlerThread;
    ImageView frontcamera;

    int id = 0;
    int flash = 0;
    int front = 0;

    String from="";
    String fromputextra="";
    String type="";
    File outputFile;
    TextView photo,video;
    View view1,view2;

    String click="click";
    String rotate="click";
    String gal="click";
    ArrayList<String> paths = new ArrayList<>();


    String title="",desc="",hash="";
    ArrayList<String> paths2 = new ArrayList<>();

    String maptext="",lat_param="",long_param="";
    String pos="";
    String preview="";
    String join="";
    String cid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_2);

        textureview = findViewById(R.id.textureview);
        captureimg = findViewById(R.id.capture);
        frontcamera = findViewById(R.id.frontcamera);
        flash_icon = findViewById(R.id.flashicon);
        gallery = findViewById(R.id.gallery);
        photo = findViewById(R.id.photo);
        video = findViewById(R.id.video);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        text = findViewById(R.id.text);


        //from join
        if (getIntent().hasExtra("from") && getIntent().hasExtra("join")){
            from = getIntent().getStringExtra("from");
            join = getIntent().getStringExtra("join");
            cid = getIntent().getStringExtra("cid");
            type = getIntent().getStringExtra("type");

            if (from.equals("campaign")){
                fromputextra="campaign";
            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }
        }



        if (getIntent().hasExtra("from")){
            from = getIntent().getStringExtra("from");
            if (from.equals("campaign")){
                fromputextra="campaign";
            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }
        }

        // from challenge screen
        if (getIntent().hasExtra("title") && getIntent().hasExtra("type")){
            if (from.equals("campaign")){
                fromputextra="campaign";
                //type = getIntent().getStringExtra("type");

            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }
            title = getIntent().getStringExtra("title");
            desc = getIntent().getStringExtra("description");
            hash = getIntent().getStringExtra("hashtags");
            paths2 = getIntent().getStringArrayListExtra("list");
            type = getIntent().getStringExtra("type");
            maptext = getIntent().getStringExtra("maptext");
            lat_param = getIntent().getStringExtra("latitude");
            long_param = getIntent().getStringExtra("longitude");

            if (getIntent().hasExtra("pos")){
                pos = getIntent().getStringExtra("pos");
            }
        }

        //from campaign screen
        if (getIntent().hasExtra("title") && !getIntent().hasExtra("type")){
            from = getIntent().getStringExtra("from");
            if (from.equals("campaign")){
                fromputextra="campaign";
                //type = getIntent().getStringExtra("type");

            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }

            title = getIntent().getStringExtra("title");
            desc = getIntent().getStringExtra("description");
            hash = getIntent().getStringExtra("hashtags");
            paths2 = getIntent().getStringArrayListExtra("list");

            if (getIntent().hasExtra("pos")){
                pos = getIntent().getStringExtra("pos");
            }
            paths2 = getIntent().getStringArrayListExtra("list");

        }

        //from image preview campaign
        if (getIntent().hasExtra("preview") && !getIntent().hasExtra("type")){
            from = getIntent().getStringExtra("from");
            preview = getIntent().getStringExtra("preview");
            if (from.equals("campaign")){
                fromputextra="campaign";
            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }
            paths2 = getIntent().getStringArrayListExtra("list");

        }

        //from image preview challenge
        if (getIntent().hasExtra("preview") && getIntent().hasExtra("type")){
            from = getIntent().getStringExtra("from");
            preview = getIntent().getStringExtra("preview");
            if (from.equals("campaign")){
                fromputextra="campaign";
            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }
            paths2 = getIntent().getStringArrayListExtra("list");
        }

        //from image preview join campaign
        if (getIntent().hasExtra("preview") && getIntent().hasExtra("join") && !getIntent().hasExtra("type")){
            from = getIntent().getStringExtra("from");
            preview = getIntent().getStringExtra("preview");
            join = getIntent().getStringExtra("join");
            cid = getIntent().getStringExtra("cid");
            type = getIntent().getStringExtra("type");
            if (from.equals("campaign")){
                fromputextra="campaign";
            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }
            paths2 = getIntent().getStringArrayListExtra("list");

        }


        //from image preview join challenge
        if (getIntent().hasExtra("preview") && getIntent().hasExtra("join") && getIntent().hasExtra("type")){
            from = getIntent().getStringExtra("from");
            preview = getIntent().getStringExtra("preview");
            join = getIntent().getStringExtra("join");
            cid = getIntent().getStringExtra("cid");
            type = getIntent().getStringExtra("type");

            if (from.equals("campaign")){
                fromputextra="campaign";
            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }
            paths2 = getIntent().getStringArrayListExtra("list");
        }

        textureview.setSurfaceTextureListener(surfaceTextureListener);

        captureimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click.equals("click")){
                    takePicture();
                    click = "";
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        click = "click";
                    }
                },5000);

            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Camera_Activity2.this);
                final CharSequence[] options = {getResources().getString(R.string.Images), getResources().getString(R.string.Videos)};
                builder.setTitle(getResources().getString(R.string.SelectFrom));
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals(getResources().getString(R.string.Images))) {

                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(Intent.createChooser(intent, "Select Pictures (Max 6)"), 1);

                        }
                        else if (options[item].equals(getResources().getString(R.string.Videos))) {
                            if(paths2.size()==0){
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                                intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY,0);
                                startActivityForResult(intent, 2);
                            }
                            else {
                                Constant.ErrorToast(Camera_Activity2.this,getResources().getString(R.string.Cannotselectvideo));
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });


        frontcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rotate.equals("click")){

                    cameraDevice.close();
                    cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    if (id == 0) {
                        id = 1;
                        if (ActivityCompat.checkSelfPermission(Camera_Activity2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        if (textureview.isAvailable()) {
                            openCamera(id);
                        } else {
                            textureview.setSurfaceTextureListener(surfaceTextureListener);
                        }
                    }
                    else {
                        id = 0;
                        if (textureview.isAvailable()) {
                            openCamera(id);
                        } else {
                            textureview.setSurfaceTextureListener(surfaceTextureListener);
                        }
                    }

                    rotate = "";
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rotate = "click";
                    }
                },3000);
            }
        });



        flash_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id==1){

                }
                else {
                    if (flash==0){
                        updatePreview2();
                        flash=1;
                    }
                    else {
                        updatePreview();
                        flash=0;
                    }
                }
            }
        });


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view1.setBackgroundColor(getResources().getColor(R.color.white));
                view2.setBackgroundColor(getResources().getColor(R.color.black));

            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (paths2.size()==0){
                    view2.setBackgroundColor(getResources().getColor(R.color.white));
                    view1.setBackgroundColor(getResources().getColor(R.color.black));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent videointent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            //videointent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY,0);
                            //videointent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY,-1);
                            videointent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, -1);
                            //videointent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5491520L);//5*1048*1048=5MB
                            videointent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,30);
                            //videointent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


                            if (videointent.resolveActivity(getPackageManager())!=null){
                                startActivityForResult(videointent,102);
                            }

                        }
                    }, 500);

                }
                else {
                    Constant.ErrorToast(Camera_Activity2.this,getResources().getString(R.string.Cannotselectvideo));
                }

            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(paths2.size()==0){
                    if (!join.equals("")){
                        Intent intent = new Intent(Camera_Activity2.this,Text_Challenge_Activity.class);
                        intent.putExtra("join","join");
                        intent.putExtra("cid", cid);

                        if (fromputextra.equals("campaign")){
                            intent.putExtra("from","campaign");
                            intent.putExtra("type",type);

                        }
                        else if (fromputextra.equals("challenge")){
                            intent.putExtra("from","challenge");
                            intent.putExtra("type",type);
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    else {
                        Intent intent = new Intent(Camera_Activity2.this,Text_Challenge_Activity.class);
                        if (fromputextra.equals("campaign")){
                            intent.putExtra("from","campaign");
                        }
                        else if (fromputextra.equals("challenge")){
                            intent.putExtra("from","challenge");
                            intent.putExtra("type",type);
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                }
                else {
                    Constant.ErrorToast(Camera_Activity2.this,getResources().getString(R.string.CannotselectTextChallenge));
                }


            }
        });

    }

    private void takePicture(){

        if (cameraDevice==null){
            return;
        }
        cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics cc = cameraManager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegsize =null;
            jpegsize = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);

            int width =640;
            int height=480;

            if (jpegsize!=null && jpegsize.length>0){
                width = jpegsize[0].getWidth();
                height = jpegsize[0].getHeight();
            }

            final ImageReader reader = ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
            List<Surface> outputSurfaces = new ArrayList<>(2);
            outputSurfaces.add(reader.getSurface());

            outputSurfaces.add(new Surface(textureview.getSurfaceTexture()));
            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE,CameraMetadata.CONTROL_MODE_AUTO);

            if (flash==1){
                captureBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
            }


            int roation = getWindowManager().getDefaultDisplay().getRotation();
            if (id==0){
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(roation));
            }
            else {
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS2.get(roation));
            }

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            file = getOutputMediaFile(); //new File(Environment.getExternalStorageDirectory()+"/"+ts+".jpg");


            ImageReader.OnImageAvailableListener readerlistener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader imageReader) {

                    Image image = null;
                    image=reader.acquireLatestImage();
                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[buffer.capacity()];
                    buffer.get(bytes);
                    save(bytes);

                    if (image!=null){
                        image.close();
                    }
                }
            };
            reader.setOnImageAvailableListener(readerlistener,backgroundHandler);

            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);

                    if (paths2.size() == 0) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startCameraPreview();

                                if (!join.equals("")){

                                    Intent intent = new Intent(Camera_Activity2.this,ImagePreview_Activity.class);
                                    intent.putExtra("loadimage",file.getAbsolutePath());
                                    intent.putExtra("join","join");
                                    intent.putExtra("cid",cid);
                                    if (fromputextra.equals("campaign")){
                                        intent.putExtra("from","campaign");
                                        intent.putExtra("type",type);

                                    }
                                    else if (fromputextra.equals("challenge")){
                                        intent.putExtra("from","challenge");
                                        intent.putExtra("type",type);
                                    }
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent(Camera_Activity2.this,ImagePreview_Activity.class);
                                    intent.putExtra("loadimage",file.getAbsolutePath());
                                    if (fromputextra.equals("campaign")){
                                        intent.putExtra("from","campaign");
                                    }
                                    else if (fromputextra.equals("challenge")){
                                        intent.putExtra("from","challenge");
                                        intent.putExtra("type",type);
                                    }
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        }, 1000);
                    }
                    else{
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startCameraPreview();

                                if (pos.equals("")){

                                    if (!join.equals("")){
                                        paths.clear();
                                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                        File [] files =folder_gui.listFiles();
                                        int index =  folder_gui.listFiles().length;

                                        Collections.sort(Arrays.asList(files),new Comparor());

                                        String path =files[index-1].getAbsolutePath();
                                        paths2.add(path);
                                        if (fromputextra.equals("campaign")) {
                                            if(!preview.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("join", "join");
                                                intent.putExtra("list", "list");
                                                intent.putExtra("cid", cid);
                                                intent.putExtra("type",type);

                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                }
                                                startActivity(intent);

                                            }
                                        }
                                        else if (fromputextra.equals("challenge")) {
                                            if(!preview.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("join", "join");
                                                intent.putExtra("list", "list");
                                                intent.putExtra("cid", cid);
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                startActivity(intent);

                                            }
                                        }
                                    }
                                    else {
                                        paths.clear();
                                        File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                        File [] files =folder_gui.listFiles();
                                        int index =  folder_gui.listFiles().length;

                                        Collections.sort(Arrays.asList(files),new Comparor());

                                        String path =files[index-1].getAbsolutePath();
                                        paths2.add(path);
                                        if (fromputextra.equals("campaign")) {
                                            if(preview.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                }
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                }
                                                startActivity(intent);
                                            }

                                        }
                                        else if (fromputextra.equals("challenge")) {
                                            if(preview.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                intent.putExtra("maptext",maptext);
                                                intent.putExtra("latitude",lat_param);
                                                intent.putExtra("longitude",long_param);
                                                startActivity(intent);

                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                intent.putExtra("maptext",maptext);
                                                intent.putExtra("latitude",lat_param);
                                                intent.putExtra("longitude",long_param);
                                                startActivity(intent);

                                            }

                                        }
                                    }
                                }

                                else {
                                    paths.clear();
                                    File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
                                    File [] files =folder_gui.listFiles();
                                    int index =  folder_gui.listFiles().length;

                                    Collections.sort(Arrays.asList(files),new Comparor());


                                    String path =files[index-1].getAbsolutePath();
                                    paths2.remove(Integer.parseInt(pos));
                                    paths2.add(Integer.parseInt(pos),path);
                                    if (fromputextra.equals("campaign")) {
                                        Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                        intent.putStringArrayListExtra("galleryimage", paths2);
                                        intent.putExtra("list", "list");
                                        intent.putExtra("title", title);
                                        intent.putExtra("description", desc);
                                        intent.putExtra("hashtags", hash);
                                        if (fromputextra.equals("campaign")) {
                                            intent.putExtra("from", "campaign");
                                        }
                                        startActivity(intent);
                                    }
                                    else if (fromputextra.equals("challenge")) {
                                        Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                        intent.putStringArrayListExtra("galleryimage", paths2);
                                        intent.putExtra("list", "list");
                                        if (fromputextra.equals("challenge")) {
                                            intent.putExtra("from", "challenge");
                                            intent.putExtra("type", type);
                                        }
                                        intent.putExtra("title", title);
                                        intent.putExtra("description", desc);
                                        intent.putExtra("hashtags", hash);
                                        intent.putExtra("maptext",maptext);
                                        intent.putExtra("latitude",lat_param);
                                        intent.putExtra("longitude",long_param);
                                        startActivity(intent);

                                    }
                                }
                            }}, 1000);
                    }
                }

                @Override
                public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
                    super.onCaptureFailed(session, request, failure);

                    Toast.makeText(getApplicationContext(),failure.getReason(),Toast.LENGTH_SHORT).show();

                }
            };

            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {

                    try {
                        session.capture(captureBuilder.build(),captureListener,backgroundHandler);

                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                    Toast.makeText(getApplicationContext(),"failed session",Toast.LENGTH_SHORT).show();

                }
            },backgroundHandler);


        }
        catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private File getOutputMediaFile() {

        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)){
            return null;
        }
        else {
            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"Bossble_Images");
            if (!folder_gui.exists()){
                folder_gui.mkdirs();
                outputFile =  new File(folder_gui,"bossble_image.jpg");
                //filepath = outputFile.getAbsolutePath();
            }
            else {
                outputFile =  new File(folder_gui,"bossble_image"+folder_gui.listFiles().length+".jpg");


            }
            return outputFile;
        }
    }

    private void save(byte[] bytes) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera(id);
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

    private void openCamera(int id) {

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            //camid = cameraManager.getCameraIdList()[0];
            camid = cameraManager.getCameraIdList()[id];
            CameraCharacteristics cc = cameraManager.getCameraCharacteristics(camid);
            StreamConfigurationMap map = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            imageDimensions = map.getOutputSizes(SurfaceTexture.class)[0];

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Camera_Activity2.this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
                return;
            }
            cameraManager.openCamera(camid, stateCallback, null);
            //cameraManager.openCamera(camid, stateCallback, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback(){

        @Override
        public void onOpened(@NonNull CameraDevice camera) {

            cameraDevice = camera;
            cameraDevice2 = camera;
            startCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            if (cameraDevice!=null){
                cameraDevice.close();
            }
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int i) {
            if (cameraDevice!=null){
                cameraDevice.close();
                cameraDevice=null;
            }
        }
    };

    private void startCameraPreview()  {
        SurfaceTexture texture = textureview.getSurfaceTexture();
        texture.setDefaultBufferSize(imageDimensions.getWidth(),imageDimensions.getHeight());

        Surface surface = new Surface(texture);

        try {
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {

                    if (cameraDevice==null){

                        return;
                    }

                    cameraCaptureSession = session;

                    if (flash==0){
                        updatePreview();
                    }
                    else {
                        updatePreview2();
                    }

                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            },null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void updatePreview(){
        if (cameraDevice==null){
            return;
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE,CameraMetadata.CONTROL_MODE_AUTO);
        captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
        flash_icon.setColorFilter(ContextCompat.getColor(Camera_Activity2.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);

        try {
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(),null,backgroundHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void updatePreview2(){
        if (cameraDevice==null){
            return;
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE,CameraMetadata.CONTROL_MODE_AUTO);
        captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
        flash_icon.setColorFilter(ContextCompat.getColor(Camera_Activity2.this, R.color.golden), android.graphics.PorterDuff.Mode.MULTIPLY);

        try {
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(),null,backgroundHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void startBackgroundThread(){
        handlerThread = new HandlerThread("Camera Background");
        handlerThread.start();

        backgroundHandler = new Handler(handlerThread.getLooper());
    }

    private void stopBackgroundThread(){
        handlerThread.quitSafely();
        try {
            handlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        backgroundHandler=null;
        handlerThread = null;

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        startBackgroundThread();

        if (textureview.isAvailable()){

            if (front==0){
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openCamera(id);
                }

                //flash handle work
                if (flash==1){
                    if (cameraDevice!=null){
                        captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
                    }
                    flash_icon.setColorFilter(ContextCompat.getColor(Camera_Activity2.this, R.color.golden), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
                else {
                    if (cameraDevice!=null){
                        captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
                    }
                    flash_icon.setColorFilter(ContextCompat.getColor(Camera_Activity2.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                }
                //end of flash work
            }
            else {
                textureview.setSurfaceTextureListener(surfaceTextureListener);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openCamera(id);
                }
            }
        }
        else {
            textureview.setSurfaceTextureListener(surfaceTextureListener);
        }

        view1.setBackgroundColor(getResources().getColor(R.color.white));
        view2.setBackgroundColor(getResources().getColor(R.color.black));

    }

    @Override
    protected void onPause() {

        stopBackgroundThread();
        super.onPause();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode==101){
            if (grantResults.length>0){


                int hasPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
                int hasPermission2 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (hasPermission != PackageManager.PERMISSION_GRANTED || hasPermission2 != PackageManager.PERMISSION_GRANTED) {

                    //if (grantResults[0]==PackageManager.PERMISSION_DENIED){


                    final AlertDialog.Builder builder = new AlertDialog.Builder(Camera_Activity2.this);
                    builder.setCancelable(false);
                    builder.setTitle(getResources().getString(R.string.PermissionRequired));
                    builder.setCancelable(false);
                    builder.setMessage(getResources().getString(R.string.GotoAppsettingsPermissionsCameraandStorageenableit));
                    builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Camera_Activity2.this.onBackPressed();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                        }
                    });
                    builder.show();
                    // }


                }
            }
            else {

                final AlertDialog.Builder builder = new AlertDialog.Builder(Camera_Activity2.this);
                builder.setCancelable(false);
                builder.setTitle(getResources().getString(R.string.PermissionRequired));
                builder.setCancelable(false);
                builder.setMessage(getResources().getString(R.string.GotoAppsettingsPermissionsCameraandStorageenableit));
                builder.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Camera_Activity2.this.onBackPressed();

                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
                builder.show();

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                ClipData clipData = data.getClipData();
                if (clipData != null) {

                    if (paths2.size() == 0) {

                        if (!join.equals("")){
                            if (clipData.getItemCount() > 6) {
                                Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Cannotselectmorethan6pictures));
                            } else {

                                paths.clear();
                                for (int i = 0; i < clipData.getItemCount(); i++) {
                                    paths.add(getRealPathFromURI(Camera_Activity2.this, clipData.getItemAt(i).getUri()));
                                }

                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                intent.putStringArrayListExtra("galleryimage", paths);
                                intent.putExtra("list", "list");
                                intent.putExtra("join", "join");
                                intent.putExtra("cid", cid);
                                if (fromputextra.equals("campaign")) {
                                    intent.putExtra("from", "campaign");
                                    intent.putExtra("type",type);

                                } else if (fromputextra.equals("challenge")) {
                                    intent.putExtra("from", "challenge");
                                    intent.putExtra("type", type);
                                }
                                startActivity(intent);
                            }
                        }
                        else {
                            if (clipData.getItemCount() > 6) {
                                Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Cannotselectmorethan6pictures));
                            } else {

                                paths.clear();
                                for (int i = 0; i < clipData.getItemCount(); i++) {
                                    paths.add(getRealPathFromURI(Camera_Activity2.this, clipData.getItemAt(i).getUri()));
                                }

                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                intent.putStringArrayListExtra("galleryimage", paths);
                                intent.putExtra("list", "list");
                                if (fromputextra.equals("campaign")) {
                                    intent.putExtra("from", "campaign");
                                } else if (fromputextra.equals("challenge")) {
                                    intent.putExtra("from", "challenge");
                                    intent.putExtra("type", type);
                                }
                                startActivity(intent);
                            }
                        }
                    }
                    else {

                        if (pos.equals("")){

                            if (paths2.size() == 1) {
                                if (clipData.getItemCount() > 5) {
                                    Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Cannotselectmorethan5pictures));
                                } else {
                                    paths.clear();
                                    for (int i = 0; i < clipData.getItemCount(); i++) {
                                        paths.add(getRealPathFromURI(Camera_Activity2.this, clipData.getItemAt(i).getUri()));
                                        paths2.add(paths.get(i));
                                    }
                                    if (fromputextra.equals("campaign")) {
                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            if (fromputextra.equals("campaign")) {
                                                intent.putExtra("from", "campaign");
                                            }
                                            startActivity(intent);
                                        }
                                        else {
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                    intent.putExtra("type",type);

                                                }
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                }
                                                startActivity(intent);
                                            }
                                        }
                                    } else if (fromputextra.equals("challenge")) {
                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            if (fromputextra.equals("challenge")) {
                                                intent.putExtra("from", "challenge");
                                                intent.putExtra("type", type);
                                            }
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            intent.putExtra("maptext",maptext);
                                            intent.putExtra("latitude",lat_param);
                                            intent.putExtra("longitude",long_param);
                                            startActivity(intent);
                                        }
                                        else{
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                intent.putExtra("maptext",maptext);
                                                intent.putExtra("latitude",lat_param);
                                                intent.putExtra("longitude",long_param);
                                                startActivity(intent);

                                            }
                                        }
                                    }
                                }
                            } else if (paths2.size() == 2) {
                                if (clipData.getItemCount() > 4) {
                                    Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Cannotselectmorethan4pictures));
                                } else {
                                    paths.clear();
                                    for (int i = 0; i < clipData.getItemCount(); i++) {
                                        paths.add(getRealPathFromURI(Camera_Activity2.this, clipData.getItemAt(i).getUri()));
                                        paths2.add(paths.get(i));
                                    }
                                    if (fromputextra.equals("campaign")) {
                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            if (fromputextra.equals("campaign")) {
                                                intent.putExtra("from", "campaign");
                                            }
                                            startActivity(intent);
                                        }
                                        else {
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                    intent.putExtra("type",type);

                                                }
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                }
                                                startActivity(intent);

                                            }
                                        }
                                    } else if (fromputextra.equals("challenge")) {

                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            if (fromputextra.equals("challenge")) {
                                                intent.putExtra("from", "challenge");
                                                intent.putExtra("type", type);
                                            }
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            intent.putExtra("maptext",maptext);
                                            intent.putExtra("latitude",lat_param);
                                            intent.putExtra("longitude",long_param);
                                            startActivity(intent);
                                        }
                                        else {
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                intent.putExtra("maptext",maptext);
                                                intent.putExtra("latitude",lat_param);
                                                intent.putExtra("longitude",long_param);
                                                startActivity(intent);

                                            }
                                        }

                                    }
                                }
                            } else if (paths2.size() == 3) {
                                if (clipData.getItemCount() > 3) {
                                    Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Cannotselectmorethan3pictures));
                                } else {
                                    paths.clear();
                                    for (int i = 0; i < clipData.getItemCount(); i++) {
                                        paths.add(getRealPathFromURI(Camera_Activity2.this, clipData.getItemAt(i).getUri()));
                                        paths2.add(paths.get(i));
                                    }
                                    if (fromputextra.equals("campaign")) {
                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            if (fromputextra.equals("campaign")) {
                                                intent.putExtra("from", "campaign");
                                            }
                                            startActivity(intent);
                                        }
                                        else {
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);

                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                    intent.putExtra("type",type);

                                                }
                                                startActivity(intent);

                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                }
                                                startActivity(intent);

                                            }
                                        }


                                    } else if (fromputextra.equals("challenge")) {

                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            if (fromputextra.equals("challenge")) {
                                                intent.putExtra("from", "challenge");
                                                intent.putExtra("type", type);
                                            }
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            intent.putExtra("maptext",maptext);
                                            intent.putExtra("latitude",lat_param);
                                            intent.putExtra("longitude",long_param);
                                            startActivity(intent);
                                        }
                                        else {

                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);

                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                intent.putExtra("maptext",maptext);
                                                intent.putExtra("latitude",lat_param);
                                                intent.putExtra("longitude",long_param);
                                                startActivity(intent);

                                            }
                                        }



                                    }
                                }
                            } else if (paths2.size() == 4) {
                                if (clipData.getItemCount() > 2) {
                                    Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Cannotselectmorethan2pictures));
                                } else {
                                    paths.clear();
                                    for (int i = 0; i < clipData.getItemCount(); i++) {
                                        paths.add(getRealPathFromURI(Camera_Activity2.this, clipData.getItemAt(i).getUri()));
                                        paths2.add(paths.get(i));
                                    }
                                    if (fromputextra.equals("campaign")) {
                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            if (fromputextra.equals("campaign")) {
                                                intent.putExtra("from", "campaign");
                                            }
                                            startActivity(intent);
                                        }
                                        else {
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);

                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                    intent.putExtra("type",type);

                                                }
                                                startActivity(intent);

                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                }
                                                startActivity(intent);

                                            }
                                        }
                                    } else if (fromputextra.equals("challenge")) {
                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            if (fromputextra.equals("challenge")) {
                                                intent.putExtra("from", "challenge");
                                                intent.putExtra("type", type);
                                            }
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            intent.putExtra("maptext",maptext);
                                            intent.putExtra("latitude",lat_param);
                                            intent.putExtra("longitude",long_param);

                                            startActivity(intent);

                                        }
                                        else {
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);

                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                startActivity(intent);
                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                intent.putExtra("maptext",maptext);
                                                intent.putExtra("latitude",lat_param);
                                                intent.putExtra("longitude",long_param);
                                                startActivity(intent);

                                            }

                                        }

                                    }
                                }
                            }
                            else if (paths2.size() == 5) {
                                if (clipData.getItemCount() > 1) {
                                    Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Cannotselectmorethan1pictures));
                                } else {
                                    paths.clear();
                                    for (int i = 0; i < clipData.getItemCount(); i++) {
                                        paths.add(getRealPathFromURI(Camera_Activity2.this, clipData.getItemAt(i).getUri()));
                                        paths2.add(paths.get(i));
                                    }
                                    if (fromputextra.equals("campaign")) {
                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            if (fromputextra.equals("campaign")) {
                                                intent.putExtra("from", "campaign");
                                            }
                                            startActivity(intent);
                                        }
                                        else {
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);

                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                    intent.putExtra("type",type);

                                                }
                                                startActivity(intent);

                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                if (fromputextra.equals("campaign")) {
                                                    intent.putExtra("from", "campaign");
                                                }
                                                startActivity(intent);

                                            }
                                        }
                                    } else if (fromputextra.equals("challenge")) {
                                        if (preview.equals("")){
                                            Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                            intent.putStringArrayListExtra("galleryimage", paths2);
                                            intent.putExtra("list", "list");
                                            if (fromputextra.equals("challenge")) {
                                                intent.putExtra("from", "challenge");
                                                intent.putExtra("type", type);
                                            }
                                            intent.putExtra("title", title);
                                            intent.putExtra("description", desc);
                                            intent.putExtra("hashtags", hash);
                                            intent.putExtra("maptext",maptext);
                                            intent.putExtra("latitude",lat_param);
                                            intent.putExtra("longitude",long_param);

                                            startActivity(intent);

                                        }
                                        else {
                                            if (!join.equals("")){
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                intent.putExtra("join", "join");
                                                intent.putExtra("cid", cid);

                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                startActivity(intent);

                                            }
                                            else {
                                                Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                                intent.putStringArrayListExtra("galleryimage", paths2);
                                                intent.putExtra("list", "list");
                                                if (fromputextra.equals("challenge")) {
                                                    intent.putExtra("from", "challenge");
                                                    intent.putExtra("type", type);
                                                }
                                                intent.putExtra("title", title);
                                                intent.putExtra("description", desc);
                                                intent.putExtra("hashtags", hash);
                                                intent.putExtra("maptext",maptext);
                                                intent.putExtra("latitude",lat_param);
                                                intent.putExtra("longitude",long_param);
                                                startActivity(intent);

                                            }

                                        }

                                    }
                                }
                            }
                        }
                        else {


                            if (clipData.getItemCount() > 1) {
                                Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Cannotselectmorethan1pictures));
                            }
                            else {
                                paths.clear();
                                paths2.remove(Integer.parseInt(pos));
                                for (int i = 0; i < clipData.getItemCount(); i++) {
                                    paths.add(getRealPathFromURI(Camera_Activity2.this, clipData.getItemAt(i).getUri()));
                                    //paths2.add(paths.get(i));
                                    paths2.add(Integer.parseInt(pos),paths.get(i));
                                }
                                if (fromputextra.equals("campaign")) {
                                    Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                    intent.putStringArrayListExtra("galleryimage", paths2);
                                    intent.putExtra("list", "list");
                                    intent.putExtra("title", title);
                                    intent.putExtra("description", desc);
                                    intent.putExtra("hashtags", hash);
                                    if (fromputextra.equals("campaign")) {
                                        intent.putExtra("from", "campaign");
                                    }
                                    startActivity(intent);
                                } else if (fromputextra.equals("challenge")) {
                                    Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                    intent.putStringArrayListExtra("galleryimage", paths2);
                                    intent.putExtra("list", "list");
                                    if (fromputextra.equals("challenge")) {
                                        intent.putExtra("from", "challenge");
                                        intent.putExtra("type", type);
                                    }
                                    intent.putExtra("title", title);
                                    intent.putExtra("description", desc);
                                    intent.putExtra("hashtags", hash);
                                    intent.putExtra("maptext",maptext);
                                    intent.putExtra("latitude",lat_param);
                                    intent.putExtra("longitude",long_param);

                                    startActivity(intent);

                                }


                            }


                        }

                    }

                }
                else if (clipData == null) {

                    if (paths2.size() == 0) {
                        paths.clear();
                        paths.add(getRealPathFromURI(Camera_Activity2.this, data.getData()));

                        if (!join.equals("")){
                            Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                            intent.putStringArrayListExtra("galleryimage", paths);
                            intent.putExtra("list", "list");
                            intent.putExtra("join", "join");
                            intent.putExtra("cid", cid);

                            if (fromputextra.equals("campaign")) {
                                intent.putExtra("from", "campaign");
                                intent.putExtra("type",type);

                            } else if (fromputextra.equals("challenge")) {
                                intent.putExtra("from", "challenge");
                                intent.putExtra("type", type);
                            }
                            startActivity(intent);

                        }
                        else {
                            Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                            intent.putStringArrayListExtra("galleryimage", paths);
                            intent.putExtra("list", "list");
                            if (fromputextra.equals("campaign")) {
                                intent.putExtra("from", "campaign");
                            } else if (fromputextra.equals("challenge")) {
                                intent.putExtra("from", "challenge");
                                intent.putExtra("type", type);
                            }
                            startActivity(intent);

                        }
                    }
                    else {

                        if (pos.equals("")){
                            paths.clear();
                            paths2.add(getRealPathFromURI(Camera_Activity2.this, data.getData()));

                            if (fromputextra.equals("campaign")) {
                                if(preview.equals("")){
                                    Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                    intent.putStringArrayListExtra("galleryimage", paths2);
                                    intent.putExtra("list", "list");
                                    intent.putExtra("title", title);
                                    intent.putExtra("description", desc);
                                    intent.putExtra("hashtags", hash);
                                    if (fromputextra.equals("campaign")) {
                                        intent.putExtra("from", "campaign");
                                    }
                                    startActivity(intent);
                                }
                                else {
                                    if (!join.equals("")){
                                        Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                        intent.putStringArrayListExtra("galleryimage", paths2);
                                        intent.putExtra("list", "list");
                                        intent.putExtra("join", "join");
                                        intent.putExtra("cid", cid);

                                        if (fromputextra.equals("campaign")) {
                                            intent.putExtra("from", "campaign");
                                            intent.putExtra("type",type);

                                        }
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                        intent.putStringArrayListExtra("galleryimage", paths2);
                                        intent.putExtra("list", "list");
                                        intent.putExtra("title", title);
                                        intent.putExtra("description", desc);
                                        intent.putExtra("hashtags", hash);
                                        if (fromputextra.equals("campaign")) {
                                            intent.putExtra("from", "campaign");
                                        }
                                        startActivity(intent);

                                    }
                                }
                            } else if (fromputextra.equals("challenge")) {
                                if(preview.equals("")){
                                    Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                    intent.putStringArrayListExtra("galleryimage", paths2);
                                    intent.putExtra("list", "list");
                                    if (fromputextra.equals("challenge")) {
                                        intent.putExtra("from", "challenge");
                                        intent.putExtra("type", type);
                                    }
                                    intent.putExtra("title", title);
                                    intent.putExtra("description", desc);
                                    intent.putExtra("hashtags", hash);
                                    intent.putExtra("maptext",maptext);
                                    intent.putExtra("latitude",lat_param);
                                    intent.putExtra("longitude",long_param);
                                    startActivity(intent);
                                }
                                else {
                                    if (!join.equals("")){
                                        Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                        intent.putStringArrayListExtra("galleryimage", paths2);
                                        intent.putExtra("list", "list");
                                        intent.putExtra("join", "join");
                                        intent.putExtra("cid", cid);

                                        if (fromputextra.equals("challenge")) {
                                            intent.putExtra("from", "challenge");
                                            intent.putExtra("type", type);
                                        }
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                                        intent.putStringArrayListExtra("galleryimage", paths2);
                                        intent.putExtra("list", "list");
                                        if (fromputextra.equals("challenge")) {
                                            intent.putExtra("from", "challenge");
                                            intent.putExtra("type", type);
                                        }
                                        intent.putExtra("title", title);
                                        intent.putExtra("description", desc);
                                        intent.putExtra("hashtags", hash);
                                        intent.putExtra("maptext",maptext);
                                        intent.putExtra("latitude",lat_param);
                                        intent.putExtra("longitude",long_param);
                                        startActivity(intent);

                                    }
                                }

                            }
                        }
                        else {
                            paths.clear();
                            paths2.remove(Integer.parseInt(pos));
                            paths2.add(Integer.parseInt(pos),getRealPathFromURI(Camera_Activity2.this, data.getData()));

                            if (fromputextra.equals("campaign")) {
                                Intent intent = new Intent(Camera_Activity2.this, Create_Campaign_Acitivity.class);
                                intent.putStringArrayListExtra("galleryimage", paths2);
                                intent.putExtra("list", "list");
                                intent.putExtra("title", title);
                                intent.putExtra("description", desc);
                                intent.putExtra("hashtags", hash);
                                if (fromputextra.equals("campaign")) {
                                    intent.putExtra("from", "campaign");
                                }
                                startActivity(intent);
                            } else if (fromputextra.equals("challenge")) {
                                Intent intent = new Intent(Camera_Activity2.this, Create_Challenge_Acitivity.class);
                                intent.putStringArrayListExtra("galleryimage", paths2);
                                intent.putExtra("list", "list");
                                if (fromputextra.equals("challenge")) {
                                    intent.putExtra("from", "challenge");
                                    intent.putExtra("type", type);
                                }
                                intent.putExtra("title", title);
                                intent.putExtra("description", desc);
                                intent.putExtra("hashtags", hash);
                                intent.putExtra("maptext",maptext);
                                intent.putExtra("latitude",lat_param);
                                intent.putExtra("longitude",long_param);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }

        }
        else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    if (!join.equals("")){
                        File file = new File(getRealPathFromURI(Camera_Activity2.this, data.getData()));
                        int file_size = Integer.parseInt(String.valueOf((file.length() / 1024) / 1024));

                        if (file_size > 20) {
                            Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Mediashouldbelessthan20MB));
                        } else {
                            Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                            intent.putExtra("galleryimage", getRealPathFromURI(Camera_Activity2.this, data.getData()));
                            intent.putExtra("join", "join");
                            intent.putExtra("cid", cid);


                            if (fromputextra.equals("campaign")) {
                                intent.putExtra("from", "campaign");
                                intent.putExtra("type",type);

                            } else if (fromputextra.equals("challenge")) {
                                intent.putExtra("from", "challenge");
                                intent.putExtra("type", type);
                            }
                            startActivity(intent);
                        }
                    }
                    else {
                        File file = new File(getRealPathFromURI(Camera_Activity2.this, data.getData()));
                        int file_size = Integer.parseInt(String.valueOf((file.length() / 1024) / 1024));

                        if (file_size > 20) {
                            Constant.ErrorToast(Camera_Activity2.this, getResources().getString(R.string.Mediashouldbelessthan20MB));
                        } else {
                            Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                            intent.putExtra("galleryimage", getRealPathFromURI(Camera_Activity2.this, data.getData()));
                            if (fromputextra.equals("campaign")) {
                                intent.putExtra("from", "campaign");
                            } else if (fromputextra.equals("challenge")) {
                                intent.putExtra("from", "challenge");
                                intent.putExtra("type", type);
                            }
                            startActivity(intent);
                        }
                    }
                }
            }
        }
        else if (requestCode == 102 && resultCode == RESULT_OK) {
            if (data != null) {

                if (!join.equals("")){

                    Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                    intent.putExtra("galleryimage", getRealPathFromURI(Camera_Activity2.this, data.getData()));
                    intent.putExtra("join", "join");
                    intent.putExtra("cid", cid);

                    if (fromputextra.equals("campaign")) {
                        intent.putExtra("from", "campaign");
                        intent.putExtra("type",type);

                    } else if (fromputextra.equals("challenge")) {
                        intent.putExtra("from", "challenge");
                        intent.putExtra("type", type);
                    }
                    startActivity(intent);
                }
                else {

                    Intent intent = new Intent(Camera_Activity2.this, ImagePreview_Activity.class);
                    intent.putExtra("galleryimage", getRealPathFromURI(Camera_Activity2.this, data.getData()));
                    if (fromputextra.equals("campaign")) {
                        intent.putExtra("from", "campaign");
                    } else if (fromputextra.equals("challenge")) {
                        intent.putExtra("from", "challenge");
                        intent.putExtra("type", type);
                    }
                    startActivity(intent);
                }

            }
        }

    }

    private class  Comparor implements Comparator<File> {


        @Override
        public int compare(File file, File t1) {

            long k = file.lastModified() - t1.lastModified();
            if(k > 0){
                return 1;
            }else if(k == 0){
                return 0;
            }else{
                return -1;
            }
        }
    }


}