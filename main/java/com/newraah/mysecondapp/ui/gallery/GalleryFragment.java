package com.newraah.mysecondapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class GalleryFragment extends Fragment {

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent11 = new Intent();
        intent11.setAction(android.content.Intent.ACTION_VIEW);
        intent11.setType("image/*");
        intent11.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent11);
    }
}