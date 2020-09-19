package com.bossble.bossble.CameraWork;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
/*
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
*/
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bossble.bossble.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Camera_Activity extends AppCompatActivity {

    Camera camera;
    FrameLayout cam;
    ShowCamera showCamera;
    ImageView capture,flash_icon,frontcamera,gallery;
    File outputFile;
    String filepath;

    int flash=0;
    int currentCameraId=0;
    String from="";
    String fromputextra="";
    String type="";

    //new work
    TextView photo,video;
    View view1,view2;

    String click="click";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_);

        cam = findViewById(R.id.cam);
        capture = findViewById(R.id.capture);
        flash_icon = findViewById(R.id.flashicon);
        frontcamera = findViewById(R.id.frontcamera);
        gallery = findViewById(R.id.gallery);
        photo = findViewById(R.id.photo);
        video = findViewById(R.id.video);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);



        if (getIntent().hasExtra("from")){
            from = getIntent().getStringExtra("from");
            if (from.equals("campaign")){
                fromputextra="campaign";
                type = getIntent().getStringExtra("type");

            }
            else if (from.equals("challenge")){
                fromputextra="challenge";
                type = getIntent().getStringExtra("type");
            }
        }


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
                view2.setBackgroundColor(getResources().getColor(R.color.white));
                view1.setBackgroundColor(getResources().getColor(R.color.black));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent videointent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                        videointent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY,-1);

                        if (videointent.resolveActivity(getPackageManager())!=null){
                            startActivityForResult(videointent,102);
                        }

                    }
                }, 500);

            }
        });







        //camera open and permission left
        requestCameraPermission();
        showCamera = new ShowCamera(Camera_Activity.this,camera,0,Camera_Activity.this);
        cam.addView(showCamera);


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

/*
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
*/

                //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //intent.setType("*/*");
                //startActivityForResult(intent, 1);


                final CharSequence[] options = {"Images", "Videos"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Camera_Activity.this);
                builder.setTitle("Select From...");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Images")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(intent, 1);
                        } else if (options[item].equals("Videos")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY,0);
                            startActivityForResult(intent, 1);
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });


        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click.equals("click")){
                    CaptureImage();
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



        flash_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                    Camera.Parameters p = camera.getParameters();

                    if (flash==0){
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        flash=1;
                        flash_icon.setColorFilter(ContextCompat.getColor(Camera_Activity.this, R.color.golden), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }
                    else {
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        flash=0;
                        flash_icon.setColorFilter(ContextCompat.getColor(Camera_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }
                    camera.setParameters(p);

                }
                else {
                    Toast.makeText(getApplicationContext(),"This device does not support flash",Toast.LENGTH_SHORT).show();
                }
            }
        });

/*
        frontcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //pause
                if (camera != null) {
                    //camera.stopPreview();
                    camera.release();        // release the camera for other applications
                    camera = null;
                }
                if (showCamera != null) {
                    cam.removeView(showCamera);
                    showCamera = null;
                }




                //resume
                if (camera == null){
                    camera = Camera.open(1);
                    setCameraDisplayOrientation(Camera_Activity.this, 1, camera);
                    camera.startPreview();
                }

                 if (showCamera == null) {
                     showCamera = new ShowCamera(Camera_Activity.this, camera,0,Camera_Activity.this);
                     cam.addView(showCamera);

                }

*/
/*
                showCamera = new ShowCamera(Camera_Activity.this,camera,1,Camera_Activity.this);
                cam.addView(showCamera);
*//*



            }
        });
*/
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        Log.e("onactivityimage", getRealPathFromURI(Camera_Activity.this,data.getData()));

                        Intent intent = new Intent(Camera_Activity.this,ImagePreview_Activity.class);
                        intent.putExtra("galleryimage",getRealPathFromURI(Camera_Activity.this,data.getData()));
                        if (fromputextra.equals("campaign")){
                            intent.putExtra("from","campaign");
                        }
                        else if (fromputextra.equals("challenge")){
                            intent.putExtra("from","challenge");
                            intent.putExtra("type",type);
                        }
                        startActivity(intent);

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else if (requestCode==102 && resultCode==RESULT_OK){
            Intent intent = new Intent(Camera_Activity.this,ImagePreview_Activity.class);
            intent.putExtra("galleryimage",getRealPathFromURI(Camera_Activity.this,data.getData()));
            if (fromputextra.equals("campaign")){
                intent.putExtra("from","campaign");
            }
            else if (fromputextra.equals("challenge")){
                intent.putExtra("from","challenge");
                intent.putExtra("type",type);
            }
            startActivity(intent);

        }
    }

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    private File getOutputMediaFile() {

        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)){
            return null;
        }
        else {
            File folder_gui = new File(Environment.getExternalStorageDirectory()+File.separator+"GUI");
            if (!folder_gui.exists()){
                folder_gui.mkdirs();
                outputFile =  new File(folder_gui,"temp.jpg");
                filepath = outputFile.getAbsolutePath();
            }
            else {
                outputFile =  new File(folder_gui,"temp"+folder_gui.listFiles().length+".jpg");
                filepath = outputFile.getAbsolutePath();
            }
            return outputFile;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    camera = Camera.open();

                }
                else {
                    Toast.makeText(getApplicationContext(),"Permission Denied for camera",Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 101: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                else {
                    Toast.makeText(getApplicationContext(),"Permission Denied for Storage",Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }


    private void requestCameraPermission()
    {
        String permission = Manifest.permission.CAMERA;
        int grant = ContextCompat.checkSelfPermission(this,permission);

        String permission2 = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int grant2 = ContextCompat.checkSelfPermission(this,permission2);


        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);

        }
        else if (grant2 != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                }
                else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                }
            }

        }
        else {
            camera = Camera.open();
        }
    }

    private void CaptureImage(){
        if (camera!=null){
            camera.takePicture(null,null,mPictureCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera == null){
            camera = Camera.open();
            flash_icon.setColorFilter(ContextCompat.getColor(Camera_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
            flash=1;
            view1.setBackgroundColor(getResources().getColor(R.color.white));
        }

        if (showCamera == null) {
            showCamera = new ShowCamera(this, camera,0,Camera_Activity.this);
            cam.addView(showCamera);
        }
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();        // release the camera for other applications
            camera = null;
            flash_icon.setColorFilter(ContextCompat.getColor(Camera_Activity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
            flash=1;
        }
        if (showCamera != null) {
            cam.removeView(showCamera);
            showCamera = null;
        }
        super.onPause();
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

            File picture = getOutputMediaFile();
            if (picture==null){
                return;
            }
            else {
                try {
                    FileOutputStream fos = new FileOutputStream(picture);
                    fos.write(bytes);
                    fos.close();

                    Intent intent = new Intent(Camera_Activity.this,ImagePreview_Activity.class);
                    intent.putExtra("loadimage",filepath);
                    if (fromputextra.equals("campaign")){
                        intent.putExtra("from","campaign");
                    }
                    else if (fromputextra.equals("challenge")){
                        intent.putExtra("from","challenge");
                        intent.putExtra("type",type);
                    }

                    startActivity(intent);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
