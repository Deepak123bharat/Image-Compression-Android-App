package com.newraah.mysecondapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int INTERNET_PERMISSION = 10;
    private static final int LOAD_IMAGE_INT = 1;


    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    Uri sourceUri;
    String sourceUriText;
    Button plus;
    Button savefile;
    Bitmap orignalImage;
    String extension = ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        plus = findViewById(R.id.fab);

        MobileAds.initialize(this, "ca-app-pub-4190280832454489~8527133531");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);





        //FloatingActionButton fab = findViewById(R.id.fab);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // asking permission if required
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE )
                        == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, LOAD_IMAGE_INT);
                }else
                    requestStoragePermission();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share,R.id.nav_techsupp)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(MainActivity.this, "String test ", Toast.LENGTH_SHORT);
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
//                    Toast.makeText(MainActivity.this, "String test " + filePath.substring(filePath.lastIndexOf(".") + 1), Toast.LENGTH_LONG).show();
                    try {
                        orignalImage = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(sourceUri));



                        Bitmap bitmap = orignalImage;

                        File filepath = Environment.getExternalStorageDirectory();
                        File dir = new File(filepath.getAbsoluteFile().getAbsolutePath() + "/cmpImg/transfer/");
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
                        intent.setClass(MainActivity.this,Main2Activity.class);

                        intent.putExtra("Bitmap",file.toString());
                        startActivity(intent);
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
                            ActivityCompat.requestPermissions(MainActivity.this,
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



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
