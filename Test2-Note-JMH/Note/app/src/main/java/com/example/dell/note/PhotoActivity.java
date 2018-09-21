package com.example.dell.note;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity {
    private static final String TAG="myTag";
    String mCurrentPhotoPath;//图像文件路径

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO_AND_SAVE_TO_PUBLIC = 2;
    static final int REQUEST_TAKE_PHOTO_AND_SAVE_TO_PRIVATE = 3;
    ImageView mImageView=null;//缩略图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //拍照并显示
        Button buttonThumbnail=(Button)findViewById(R.id.buttonTakePhoto);
        mImageView=(ImageView)findViewById(R.id.photoThumbnail);

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            buttonThumbnail.setEnabled(false);
        }

        buttonThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        //拍照并保存到公共图片目录
        Button buttonSaveToPublic=(Button)findViewById(R.id.buttonTakePhotoAndSaveToPublic);
        //只有设备上有相机才能执行相机的有关操作
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            buttonSaveToPublic.setEnabled(false);
        }


        buttonSaveToPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFileInPublicDir();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_AND_SAVE_TO_PUBLIC);
                    }
                }
            }
        });

    }

    private File createImageFileInPublicDir() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        if(!storageDir.exists()) {
            Log.v(TAG,"目录不存在");
            if(!storageDir.mkdirs()) {
                Log.v(TAG,"创建失败");
                return null;
            }
        }
        File image = new File(storageDir, imageFileName+"photo.jpg");
        mCurrentPhotoPath =  image.getAbsolutePath();
        Log.v(TAG,"mCurrentPhotoPath:"+mCurrentPhotoPath);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }else if (requestCode == REQUEST_TAKE_PHOTO_AND_SAVE_TO_PUBLIC && resultCode == RESULT_OK) {
            setPic();
            addPicTogallery();
        }  else if (requestCode == REQUEST_TAKE_PHOTO_AND_SAVE_TO_PRIVATE && resultCode == RESULT_OK) {
            setPic();
        }
    }

    private void setPic() {
        int targetW =50;
        int targetH =50;
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;//设置仅加载位图边界信息（相当于位图的信息，但没有加载位图）
        //返回为NULL，即不会返回bitmap,但可以返回bitmap的横像素和纵像素还有图片类型
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    private void addPicTogallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
