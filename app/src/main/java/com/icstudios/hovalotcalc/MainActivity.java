package com.icstudios.hovalotcalc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    Button newOrder, orderPage, dairy;
    PopupWindow popupMenu;

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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBackPressed() {

        if (popupMenu != null && popupMenu.isShowing()) {
            popupMenu.dismiss();
        } else {


            final LayoutInflater layoutInflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            View popupView = layoutInflater.inflate(R.layout.alert_out, null);
            popupMenu = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            popupMenu.setAnimationStyle(R.style.Animation);

            Button yes = (Button) popupView.findViewById(R.id.yes_button);
            Button no = (Button) popupView.findViewById(R.id.no_button);

            //Close the popup when touch outside
            popupMenu.setOutsideTouchable(false);
            popupMenu.setFocusable(false);

//            popupMenu.setTouchModal(false);

            yes.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupMenu.dismiss();
                    //if you want to kill app . from other then your main avtivity.(Launcher)
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);

                    //if you want to finish just current activity

                    MainActivity.this.finish();
                }

            });

            no.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    popupMenu.dismiss();
                }
            });

            popupMenu.showAtLocation(popupView, Gravity.CENTER, 0, -200);
        }
    }
}