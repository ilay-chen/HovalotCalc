package com.icstudios.hovalotcalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Need to ask for write permissions on SDK 23 and up, this is ignored on older versions
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}