package com.newraah.mysecondapp.ui.techSuppo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class techSupp extends Fragment {

    public void onCreate( Bundle savedInstanceState) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UC3E5F8SFC1cxJVGkHOfZuvw"));
        startActivity(intent);
        super.onCreate(savedInstanceState);


    }
}