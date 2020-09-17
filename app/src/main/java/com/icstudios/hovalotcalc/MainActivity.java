package com.icstudios.hovalotcalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newOrder, orderPage, dairy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newOrder = (Button)findViewById(R.id.new_order);
        orderPage = (Button)findViewById(R.id.order_page);
        dairy = (Button)findViewById(R.id.dairy);

        final Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        final Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OrderCreateActivity.class);
                startActivity(i);
            }
        });

        newOrder.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                dairy.clearAnimation();
                orderPage.clearAnimation();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        newOrder.startAnimation(zoomin);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        newOrder.startAnimation(zoomout);
                        break;
                    }
                }
                return false;
            }
        });

//        newOrder.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//                        Button view = (Button) v;
//                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                        v.invalidate();
//                        break;
//                    }
//                    case MotionEvent.ACTION_UP:
//                        // Your action here on button click
//                    case MotionEvent.ACTION_CANCEL: {
//                        Button view = (Button) v;
//                        view.getBackground().clearColorFilter();
//                        view.invalidate();
//                        break;
//                    }
//                }
//                return true;
//            }
//        });

        orderPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OrdersPage.class);
                startActivity(i);
            }
        });

        orderPage.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                dairy.clearAnimation();
                newOrder.clearAnimation();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        orderPage.startAnimation(zoomin);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        orderPage.startAnimation(zoomout);
                        break;
                    }
                }
                return false;
            }
        });



        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // A date-time specified in milliseconds since the epoch.
                long startMillis = System.currentTimeMillis();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                startActivity(intent);
            }
        });

        dairy.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                newOrder.clearAnimation();
                orderPage.clearAnimation();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        dairy.startAnimation(zoomin);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        dairy.startAnimation(zoomout);
                        break;
                    }
                }
                return false;
            }
        });

        // Need to ask for write permissions on SDK 23 and up, this is ignored on older versions
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}