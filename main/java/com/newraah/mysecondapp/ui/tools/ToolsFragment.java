package com.newraah.mysecondapp.ui.tools;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class ToolsFragment extends Fragment {

    public void onCreate( Bundle savedInstanceState) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wfter.com"));
        startActivity(intent);
        super.onCreate(savedInstanceState);

    }
}