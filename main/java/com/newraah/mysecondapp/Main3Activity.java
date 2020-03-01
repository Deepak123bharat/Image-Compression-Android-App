package com.newraah.mysecondapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;

public class Main3Activity extends AppCompatActivity {
    ImageView viewBitmap;
    Bitmap decoded;
    File file;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //byte[] imageInByte = this.getIntent().getByteArrayExtra("Bitmap");
        //decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(imageInByte));
        //decoded = (Bitmap)this.getIntent().getParcelableExtra("Bitmap");
        String str = this.getIntent().getStringExtra("Bitmap");
        file = new File(str);
        //Toast.makeText(Main3Activity.this,file.toString(),Toast.LENGTH_SHORT).show();
        //decoded = BitmapFactory.decodeResource(getResources(),R.drawable.input);
        setContentView(R.layout.activity_main3);
        viewBitmap = findViewById(R.id.imageView2);

        try {
            decoded = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(Uri.fromFile(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        viewBitmap.setImageBitmap(decoded);
    }

    @Override
    public boolean isDestroyed() {
        return super.isDestroyed();
    }
    @Override
    protected void onStop() {
        file.delete();
        super.onStop();
    }

}
