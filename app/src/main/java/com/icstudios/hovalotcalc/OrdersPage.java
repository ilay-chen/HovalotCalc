package com.icstudios.hovalotcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class OrdersPage extends AppCompatActivity {

    Bitmap pageImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_page);


    }

    /**
     * Helper method for drawing the result of renderFile() on screen
     */
//    private void displayRenderedImage() {
//        new Thread() {
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ImageView imageView = (ImageView) findViewById(R.id.renderedImageView);
//                        imageView.setImageBitmap(pageImage);
//                    }
//                });
//            }
//        }.start();
//    }
}