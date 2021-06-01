package com.icstudios.hovalotcalc;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.icstudios.hovalotcalc.OrderObject;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class appData extends Application {

    public static ArrayList<OrderObject> allOrders;
    public static String rootPath, pdfFileName, picFileName, allOrdersFileName;

    @Override
    public void onCreate() {
        super.onCreate();
        rootPath = getFilesDir().getAbsolutePath();
        pdfFileName = "filled_offer.pdf";
        picFileName = "filled_offer.jpg";
        allOrdersFileName = "allOrders";

        readData(this);
    }

    public static String getFilePath(OrderObject orderObject)
    {
        return rootPath + "/" + orderObject.getId() + "/";
    }

    public static void saveOrder(OrderObject orderDetails, Context context) {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        orderDetails.setId(orderDetails.getClientName().replaceAll(" ", "_") + "_" + ts);

        // Save the final pdf document to a file
        //String yourFilePath = context.getFilesDir() + "/" + folder + "/" + fileName;

        appData.makeDir(context, orderDetails.getId());

//            renderFile(document, orderDetails, path);

        appData.updateOrderList(orderDetails);

        if(orderDetails.getDate()==null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());
            orderDetails.setDate(currentDateandTime);
        }
        sortOrders();

        saveData(context);
    }

    public static void makeDir(Context context, String folderName)
    {
        File dir = new File(context.getFilesDir(), folderName);
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    public static void sortOrders()
    {
        Collections.sort(allOrders, new Comparator<OrderObject>() {
            @Override
            public int compare(OrderObject o1, OrderObject o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
    }

    public static void saveData(Context c)
    {
        FileOutputStream fos = null;
        try {
            fos = c.openFileOutput(allOrdersFileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(allOrders);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //errorAlert(c, c.getString(R.string.error_save_files));

        } catch (IOException e) {
            e.printStackTrace();
            //errorAlert(c, c.getString(R.string.error_save_files));

        }
    }

    public static void updateOrderList(OrderObject newOrder)
    {
        int i = 0;
        for (;i < allOrders.size(); i++) {
            if (allOrders.get(i).getId().equals(newOrder.getId())) {
                allOrders.remove(i);
                break;
            }
        }
        allOrders.add(i, newOrder);
    }

    public static void removeOrder(OrderObject order)
    {
        int i = 0;
        for (;i < allOrders.size(); i++) {
            if (allOrders.get(i).getId().equals(order.getId())) {
                allOrders.remove(i);
                break;
            }
        }
    }

    public static void readData(Context c)
    {
        ObjectInputStream is = null;
        try {
            FileInputStream fis = c.openFileInput(allOrdersFileName);
            is = new ObjectInputStream(fis);
            ArrayList<OrderObject> allData = (ArrayList<OrderObject>) is.readObject();
            is.close();
            fis.close();

            if(allData!=null) {
                allOrders = allData;
            }
            else
            {
                allOrders = new ArrayList<OrderObject>();
            }

        } catch (IOException e) {
            e.printStackTrace();
            //errorAlert(c,"לא הצלחנו לאחזר את הקבצים הישנים, ניצור חדשים במקום");
            allOrders = new ArrayList<OrderObject>();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //errorAlert(c,"לא הצלחנו לאחזר את הקבצים הישנים, ניצור חדשים במקום");
            allOrders = new ArrayList<OrderObject>();

        }
    }

    public void writeFileOnInternalStorage(Context mcoContext, String folderName, String sFileName, Object object){
        File dir = new File(mcoContext.getFilesDir(), folderName);
        if(!dir.exists()){
            dir.mkdir();
        }

        String filename = mcoContext.getFilesDir().getAbsolutePath() + "/" + sFileName;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create file");
                }
                // else { //prompt user to confirm overwrite }

                FileOutputStream fileout = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fileout);
                out.writeObject(object);
                out.close();
                fileout.close();
            }
        }
        catch (Exception ex)
        {
            //show the error message
        }
    }

    public static void errorAlert(Context context, String text)
    {
        new AlertDialog.Builder(context)
                .setTitle(R.string.error_save_files_title)
                .setMessage(text)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
