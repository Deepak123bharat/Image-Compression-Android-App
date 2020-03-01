package com.newraah.mysecondapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.StrictMath.abs;

public class Main2Activity extends AppCompatActivity {

    private static final int INTERNET_PERMISSION = 10;
    private static final int LOAD_IMAGE_INT = 1;

    ImageView imageArea;
    ImageView inputImg;
    Button compressBtn;
    EditText percentage;
    TextView sizeView;
    TextView OriginalSize;
    TextView CurrentSize;
    Button getImageBtn;
    Button saveImageBtn;
    Button Logo;

    Bitmap orignalImage;
    Bitmap tempImage;
    Bitmap decoded;
    Bitmap showImage;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    Uri sourceUri;
    String sourceUriText;
    String extension = ".jpg";
    int quality = 100;
    File f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //getting reference frome activity_main.xml file
        imageArea = findViewById(R.id.imagedisplay);
        inputImg = findViewById(R.id.inputdisplay);
        compressBtn = findViewById(R.id.compressbutton);
        //getImageBtn = findViewById(R.id.loadImage);
        saveImageBtn = findViewById(R.id.saveImage);
        percentage = findViewById(R.id.percent);
        sizeView = findViewById(R.id.sizedisplay);
        OriginalSize = findViewById(R.id.originalSize);
        CurrentSize = findViewById(R.id.currentSize);
        //Logo = findViewById(R.id.logo);

        MobileAds.initialize(this, "ca-app-pub-4190280832454489~8527133531");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        prepareAD();

        String str = this.getIntent().getStringExtra("Bitmap");
        f = new File(str);

        try {
            orignalImage = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(Uri.fromFile(f)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tempImage = orignalImage.copy(orignalImage.getConfig(), true);



        imageArea.setImageBitmap(orignalImage);
        inputImg.setImageBitmap(orignalImage);
        //sizeView.setText(getsize(100)+"KB");
        OriginalSize.setText("Size : "+getsize(100)+"KB");
        CurrentSize.setText("Size : "+getsize(100)+"KB");
        // setting onClicListener
        imageArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BitmapDrawable drawable = (BitmapDrawable) imageArea.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsoluteFile().getAbsolutePath() + "/cmpImg/tempoimgtouch/");
                dir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File file = new File(dir, timeStamp + extension);

                try {
                    FileOutputStream out = new FileOutputStream(file);

                    // Compress into png format image from 0% - 100%
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    // Toast.makeText(MainActivity.this, "File saved in getsize", Toast.LENGTH_LONG).show();
                }

                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    e.toString();
                    // Toast.makeText(MainActivity.this, "Not saved", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent();
                intent.setClass(Main2Activity.this,Main3Activity.class);

                intent.putExtra("Bitmap",file.toString());
                startActivity(intent);
            }
        });

        inputImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) inputImg.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsoluteFile().getAbsolutePath() + "/cmpImg/tempoimgtouch/");
                dir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File file = new File(dir, timeStamp + extension);

                try {
                    FileOutputStream out = new FileOutputStream(file);

                    // Compress into png format image from 0% - 100%
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    // Toast.makeText(MainActivity.this, "File saved in getsize", Toast.LENGTH_LONG).show();
                }

                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    e.toString();
                    // Toast.makeText(MainActivity.this, "Not saved", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent();
                intent.setClass(Main2Activity.this,Main3Activity.class);

                intent.putExtra("Bitmap",file.toString());
                startActivity(intent);
            }
        });
        percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                          }

            @Override
            public void afterTextChanged(Editable s) {
                sizeView.setText("");
            }
        });
        compressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap bitmap = orignalImage;
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    int reqSize = Integer.parseInt(percentage.getText().toString());


                    if (reqSize>getsize(100) || reqSize<getsize(1))
                    {
                        if(reqSize>getsize(100))
                            sizeView.setText("size should less than" + getsize(100) +"KB");
                        else
                            sizeView.setText("size should greater than "+ getsize(1) +"KB");
                        return;
                    }
                    //sizeView.setText(reqSize +" reqsiz KB");


                    quality = binarySearch(reqSize, 0,100);
                    //int qul =1;
                    if (quality == -1)
                    {
                        quality = 100;
                        sizeView.setText("This size is not possible");
                    }


                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
                    byte[] imageInByte = stream.toByteArray();
                    //sizeView.setText(getsize(quality) +" KB");
                    CurrentSize.setText("Size : "+getsize(quality)+"KB");
                    //OriginalSize.setText("Size : "+getsize(100)+"KB");
                    decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(imageInByte));
                    imageArea.setImageBitmap(decoded);
                    //inputImg.setImageBitmap(decoded);
                    Toast.makeText(Main2Activity.this, "Compressed good job!", Toast.LENGTH_LONG).show();

                }catch (Exception e){

                }
            }
        });

        /*Logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentLogo = new Intent(Intent.ACTION_VIEW,Uri.parse("https://deepak123bharat.github.io/webforluagl"));
                startActivity(intentLogo);
            }
        });*/

        // getting image onClickListener from gallery


        saveImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!(ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_GRANTED)){
                    requestStoragePermission();
                }
                if(!(ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_GRANTED)){
                    return;
                }
                saveImageBtn.setEnabled(false);

                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsoluteFile().getAbsolutePath() + "/CompressedImageNR/");
                dir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                File file = new File(dir, timeStamp + extension);

                try {
                    FileOutputStream out = new FileOutputStream(file);

                    // Compress into png format image from 0% - 100%
                    decoded.compress(Bitmap.CompressFormat.JPEG, quality, out);
                    out.flush();
                    out.close();

                    Toast.makeText(Main2Activity.this, "File saved", Toast.LENGTH_LONG).show();
                }

                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    e.toString();
                    Toast.makeText(Main2Activity.this, "Not saved", Toast.LENGTH_LONG).show();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    final Uri contentUri = Uri.fromFile(file);
                    scanIntent.setData(contentUri);
                    sendBroadcast(scanIntent);
                } else {
                    final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
                    sendBroadcast(intent);
                }
                saveImageBtn.setEnabled(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(Main2Activity.this, "String test ", Toast.LENGTH_SHORT);
        if(requestCode == LOAD_IMAGE_INT && resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:


                    sourceUri = data.getData();
                    sourceUriText = sourceUri.toString();

                    String filePath ="";
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(sourceUri, filePathColumn, null, null, null);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filePath = cursor.getString(columnIndex);
                    }
                    cursor.close();
                    extension = "." + filePath.substring(filePath.lastIndexOf(".") + 1);
                    //extensionView.setText(extension + "");
//                    Toast.makeText(MainActivity.this, "String test " + filePath.substring(filePath.lastIndexOf(".") + 1), Toast.LENGTH_LONG).show();
                    try {
                        orignalImage = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(sourceUri));
                        imageArea.setImageBitmap(orignalImage);
                        inputImg.setImageBitmap(orignalImage);
                        CurrentSize.setText("Size : "+getsize(100)+"KB");
                        OriginalSize.setText("Size : "+getsize(100)+"KB");
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Toast.makeText(this,"file error", Toast.LENGTH_SHORT);
                    }



            }


        }

    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Main2Activity.this,
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, INTERNET_PERMISSION);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, INTERNET_PERMISSION);
        }
    }
    protected int getsize(int n){

        BitmapDrawable drawable = (BitmapDrawable) inputImg.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsoluteFile().getAbsolutePath() + "/cmpImg/tempo1/");
        dir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File file = new File(dir, timeStamp + extension);

        try {
            FileOutputStream out = new FileOutputStream(file);

            // Compress into png format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, n, out);
            out.flush();
            out.close();
            // Toast.makeText(MainActivity.this, "File saved in getsize", Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.toString();
            // Toast.makeText(MainActivity.this, "Not saved", Toast.LENGTH_LONG).show();
        }
        //saveImageBtn.setEnabled(true);
        int length = (int)file.length();
        length = length/1024;
        file.delete();
        return length;
    }
    protected int binarySearch(int reqSize,int l,int h){
        int mid =100;
        while(l<=h)
        {


            if(abs(reqSize - (int)getsize(mid)) < (int)getsize(100)*1/100)
            {
                if((int)getsize(mid)>reqSize && mid>0)
                    mid = mid - 1;
                return mid;
            }
            else if(reqSize>(int)getsize(mid))
                l = mid+1;
            else
                h = mid-1;
            if(l==h)
                return mid;

            mid = ((l+h)/2);

        }
        return mid;

    }
    @Override
    public void onBackPressed(){
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        }
        else{
            super.onBackPressed();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        f.delete();

    }
    public void prepareAD(){
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4190280832454489/1724909707");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
