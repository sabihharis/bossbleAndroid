package com.bossble.bossble.CameraWork;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    Camera mcamera;
    SurfaceHolder surfaceHolder;
    Activity mcontext;
    int mflash;
    public ShowCamera(Context context, Camera camera, int flash, Activity activity) {
        super(context);
        mcamera=camera;
        mflash=flash;
        mcontext=activity;

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {



        Camera.Parameters parameters = mcamera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Camera.Size mSize = null;
        for (Camera.Size size : sizes){
            mSize=size;
        }
        mcamera.setDisplayOrientation(90);
        parameters.setRotation(90);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        //parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);

        parameters.setPictureSize(mSize.width,mSize.height);



/*
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
        }
*/
        mcamera.setParameters(parameters);


        if (mflash==0){

        }
        else {

            if (mcamera != null) {
                mcamera.stopPreview();
                mcamera.release();        // release the camera for other applications
                mcamera = null;

            }

            if (mcamera == null){
                mcamera = Camera.open(1);
                setCameraDisplayOrientation(mcontext, 1, mcamera);
                //mcamera.startPreview();
            }

        }

        try {
            mcamera.setPreviewDisplay(surfaceHolder);
            mcamera.startPreview();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mcamera.stopPreview();
        mcamera.release();
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

}
