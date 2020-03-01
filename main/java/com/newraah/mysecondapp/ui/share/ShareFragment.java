package com.newraah.mysecondapp.ui.share;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ShareFragment extends Fragment {

    public void onCreate( Bundle savedInstanceState) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String ShareBody = "https://play.google.com/store/apps/details?id=com.newraah.mysecondapp";
        String ShareSub = "sub here";
        intent.putExtra(Intent.EXTRA_SUBJECT,ShareSub);
        intent.putExtra(Intent.EXTRA_TEXT,ShareBody);
        startActivity(Intent.createChooser(intent,"share using"));

        super.onCreate(savedInstanceState);

    }
}