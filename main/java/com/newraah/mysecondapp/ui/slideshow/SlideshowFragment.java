package com.newraah.mysecondapp.ui.slideshow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.fragment.app.Fragment;

import java.io.File;

public class SlideshowFragment extends Fragment {

    public void onCreate( Bundle savedInstanceState) {


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                +  File.separator + "CompressedImageNR" + File.separator);
        intent.setDataAndType(uri, "image/*");
        startActivity(Intent.createChooser(intent, "Open folder"));


        super.onCreate(savedInstanceState);
    }
}