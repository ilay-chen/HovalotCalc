package com.icstudios.hovalotcalc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.font.PDType0Font;
import com.tom_roush.pdfbox.rendering.PDFRenderer;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.icstudios.hovalotcalc.appData.saveOrder;
import static com.icstudios.hovalotcalc.appData.sortOrders;

public class MakePDFOffer {

    File root;
    AssetManager assetManager;
    Context context;
    PDPageContentStream contentStream;
    PDDocument document;
    PDType0Font font;
    Bitmap pageImage;
    Activity currentActivity;

    int ACTIVITY_BACK = 1;

    public MakePDFOffer(Context context, Activity activity)
    {
        this.context = context;
        this.currentActivity = activity;
        setup();
    }

    /**
     * Initializes variables used for convenience
     */
    private void setup() {
        // Enable Android-style asset loading (highly recommended)
        PDFBoxResourceLoader.init(context.getApplicationContext());
        // Find the root of the external storage.
        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        assetManager = context.getAssets();
        //tv = (TextView) context.findViewById(R.id.statusTextView);
    }

    /**
     * Creates a new PDF from scratch and saves it to a file
     */
    public void createPdf(OrderObject orderDetails) {
        try {
            document = PDDocument.load(assetManager.open("empty_offer.pdf"));
            font = PDType0Font.load(document, assetManager.open("assistant-regular.ttf"));
            font = PDType0Font.load(document, assetManager.open("nachlieli_light.ttf"));
            PDPage page = document.getPage(0);
            //document.addPage(page);

            // Define a content stream for adding to the PDF
            contentStream = new PDPageContentStream(document, page, true, true);
            
            //name
            writeOneLine(orderDetails.getClientName(),false,551,820);

            //date and o'clock
            writeDate(orderDetails.getDate(), true,513,765);
            writeDate(orderDetails.getHour(), true,528,749);

            //address from
            writeOneLine(orderDetails.getFromCity(), false,561,670);
            writeOneLine(orderDetails.getFromStreet(), false,428,670);
            writeOneLine(orderDetails.getFromNumber(), false,325,670);

//            writeOneLine("0", false,553,620);
            writeOneLine(orderDetails.getFromFloor(), false,549,620);
            String isElevator = "יש";
            if(!orderDetails.getFromElevator()) isElevator = "אין";
            writeOneLine(isElevator, false,415,620);

            //address to
            writeOneLine(orderDetails.getToCity(), false,265,670);
            writeOneLine(orderDetails.getToStreet(), false,130,670);
            writeOneLine(orderDetails.getToNumber(), false,25,670);

//            writeOneLine("0", false,252,620);
            writeOneLine(orderDetails.getToFloor(), false,252,620);
            isElevator = "יש";
            if(!orderDetails.getToElevator()) isElevator = "אין";
            writeOneLine(isElevator, false,118,620);

            //all items
            //writeOneLine("כל הפרטים", true,525,560);
            //String line = "";
            //String roomName = orderDetails.getRoomsAndItems().get(0).roomName.getText().toString();
            int y = 550;
            int x = 580;
            for (roomObject roomLayout: orderDetails.getRoomsAndItems()) {
                if(!roomLayout.roomName.equals(""))
                {
                    if(y <= 190) {
                        y = 560;
                        x = 290;
                    }

                    y-=10;
                    writeRoomLine(roomLayout.roomName,x,y);
                    //writeOneLine(roomLayout.roomName.getText().toString(), true,getXPos(roomLayout.roomName.getText().toString().length()),y);
                    y-=20;
                }

                for(itemObject itemLayout:roomLayout.mItems)
                {
                    if(y <= 190) {
                        y = 560;
                        x = 290;
                    }

                    String itemName = itemLayout.itemName;

                    String extraDetails = "";
                    if(Integer.parseInt(itemLayout.itemCounter) > 1)
                        extraDetails += "כמות " + itemLayout.itemCounter;

                    if(itemLayout.DisassemblyAndAssembly)
                    {
                        if(extraDetails.length() !=0) extraDetails += " ,";
                        extraDetails += "פירוק והרכבה";
                    }
                    if(itemLayout.Disassembly)
                    {
                        if(extraDetails.length() !=0) extraDetails += " ,";
                        extraDetails += "פירוק";
                    }

                    if(itemLayout.Assembly)
                    {
                        if(extraDetails.length() !=0) extraDetails += " ,";
                        extraDetails += "הרכבה";
                    }

                    writeItemLine(itemName,x,y,extraDetails);
                    y-=20;
                }

                //roomName = RoomLayout.roomName.getText().toString();
            }


            //extra details
            writeOneLine(orderDetails.getBoxes(), true,470,150);
            writeOneLine(orderDetails.getSuitcases(), true,470,120);
            writeOneLine(orderDetails.getBags(), true,470,85);

            String packing = "יש";
            if(!orderDetails.isPacking()) packing = "אין";
            writeOneItemLine(packing,335,150, false);

            if(orderDetails.isPacking())
            writeOneLine("25", false,260,118);

            if(orderDetails.isCrane())
                writeOneLine("300", false,260,85);

            String notes = orderDetails.getNotes();
            if(notes != null) {
                notes = notes.replace("\n", "").replace("\r", "");
                y = 128;

                while (notes != null && notes.length() > 0) {
                    int minIndex = 20;
                    int substring = 0;
                    if (notes.length() < 5)
                        minIndex = 0;
                    substring = notes.indexOf(" ", minIndex) + 1;
                    if (substring == -1 || substring == 0)
                        substring = notes.length();

                    String partNote = notes.substring(0, substring);
                    notes = notes.replace(partNote, "");
                    writeNoteLine(partNote, 198, y);
                    y -= 15;
                }
            }

            //writeOneLine("יש כאן עוד משפט ארוך בנודע" + "" + "שורה שנייה של משפט", true,20,145);

            //price
            writePrice(orderDetails.getPrice()+"", 125,33);

            //contentStream.endText();

            saveOrder(orderDetails, context);

//            Long tsLong = System.currentTimeMillis()/1000;
//            String ts = tsLong.toString();
//            orderDetails.setId(orderDetails.getClientName().replaceAll(" ", "_") + "_" + ts);
//
//            // Save the final pdf document to a file
//            //String yourFilePath = context.getFilesDir() + "/" + folder + "/" + fileName;
//
//            appData.makeDir(context, orderDetails.getId());
//
////            renderFile(document, orderDetails, path);
//
//            appData.updateOrderList(orderDetails);
//
//            sortOrders();
//
//            appData.saveData(context);

            String path = appData.getFilePath(orderDetails) + appData.pdfFileName;
            // Make sure that the content stream is closed:
            contentStream.close();
            document.save(path);
            document.close();
            //tv.setText("Successfully wrote PDF to " + path);

            //sharePDF(path);

            finishAndOpenOrdersPage(path, orderDetails);

        } catch (IOException e) {
            Log.e("PdfBox-hovalotCalc", "Exception thrown while creating PDF => ", e);
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void  sharePDF(String path)
    {
        /*Create an ACTION_SEND Intent*/
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
//        String shareBody = "Here is the share content body";
        File outputFile = new File(path);
        Uri uri = Uri.fromFile(outputFile);

        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        /*The type of the content is text, obviously.*/
//        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
//        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
//        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        /*Fire!*/
        context.startActivity(Intent.createChooser(share, "שתף PDF"));
    }

    public void finishAndOpenOrdersPage(final String path, final OrderObject orderDetails)
    {
//        currentActivity.finish();

        final Button shareWhatsapp, shareMail, shareSms;

        final LayoutInflater layoutInflater = (LayoutInflater)context
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.share_popup, null);
        final PopupWindow popupMenu = new PopupWindow(popupView, 1000, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        popupMenu.setAnimationStyle(R.style.Animation);
        popupMenu.showAtLocation(popupView, Gravity.CENTER, 0, 100);

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.RESULT_HIDDEN);

        shareWhatsapp = popupView.findViewById(R.id.send_whatsapp);
        shareSms = popupView.findViewById(R.id.send_sms);
        shareMail = popupView.findViewById(R.id.send_email);



        shareWhatsapp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                File outputFile = new File(path);
                Uri uri =FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", outputFile);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "");
                sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
                sendIntent.putExtra("jid", orderDetails.getPhoneNumber().replaceFirst("0","972") + "@s.whatsapp.net"); //phone number without "+" prefix
                sendIntent.setPackage("com.whatsapp");

                ((Activity)context).startActivityForResult(sendIntent, ACTIVITY_BACK);
            }
        });

        shareWhatsapp.setOnTouchListener(new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            shareSms.clearAnimation();
            shareMail.clearAnimation();
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    shareWhatsapp.startAnimation(OrderCreateActivity.zoomin);
                break;
                case MotionEvent.ACTION_UP :
                    shareWhatsapp.startAnimation(OrderCreateActivity.zoomout);
                break;
            }
            return false;
        }
    });


        shareSms.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                File outputFile = new File(path);
                Uri uri =FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", outputFile);

                Uri uriphine = Uri.parse("smsto:" + orderDetails.getPhoneNumber());
                Intent it = new Intent(Intent.ACTION_SENDTO, uriphine);
                it.putExtra("sms_body", "");
                it.putExtra(Intent.EXTRA_STREAM, uri);
                ((Activity)context).startActivityForResult(it, ACTIVITY_BACK);
            }
        });

        shareSms.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                shareWhatsapp.clearAnimation();
                shareMail.clearAnimation();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        shareSms.startAnimation(OrderCreateActivity.zoomin);
                        break;
                    case MotionEvent.ACTION_UP :
                        shareSms.startAnimation(OrderCreateActivity.zoomout);
                        break;
                }
                return false;
            }
        });

        shareMail.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                File outputFile = new File(path);
                Uri uri =FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", outputFile);

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{orderDetails.getEmail()});
                i.putExtra(Intent.EXTRA_SUBJECT, "הצעת מחיר הובלות");
                i.putExtra(Intent.EXTRA_TEXT, "הצעת מחיר הובלות");
                i.putExtra(Intent.EXTRA_STREAM, uri);
                try {
                    ((Activity)context).startActivityForResult(Intent.createChooser(i, "Send mail"), ACTIVITY_BACK);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(((Activity)context), "There are no email applications installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        shareMail.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                shareSms.clearAnimation();
                shareWhatsapp.clearAnimation();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        shareMail.startAnimation(OrderCreateActivity.zoomin);
                        break;
                    case MotionEvent.ACTION_UP :
                        shareMail.startAnimation(OrderCreateActivity.zoomout);
                        break;
                }
                return false;
            }
        });



//        Intent i = new Intent(context, MainActivity.class);
//        context.startActivity(i);
    }

    public void writeOneLine(String toWrite,boolean disable, int x, int y) throws IOException {
        if(toWrite!=null) {
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0);
            int fontsize = 14;
            contentStream.setFont(font, fontsize);
            float text_width = (font.getStringWidth(toWrite) / 1000.0f) * fontsize;
            contentStream.newLineAtOffset(x-text_width, y);

            if (!isNumeric(toWrite) && !disable) toWrite = new StringBuilder(toWrite).reverse().toString();
            contentStream.showText(toWrite);
            contentStream.endText();
        }
    }

    public void writeDate(String toWrite,boolean disable, int x, int y) throws IOException {
        if(toWrite!=null) {
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0);
            int fontsize = 12;
            contentStream.setFont(font, fontsize);
            float text_width = (font.getStringWidth(toWrite) / 1000.0f) * fontsize;
            contentStream.newLineAtOffset(x-text_width, y);

            if (!isNumeric(toWrite) && !disable) toWrite = new StringBuilder(toWrite).reverse().toString();
            contentStream.showText(toWrite);
            contentStream.endText();
        }
    }

    public void writePrice(String toWrite, int x, int y) throws IOException {
        if(toWrite!=null) {
            contentStream.beginText();
            int fontSize = 16;
            contentStream.setNonStrokingColor(0, 0, 0);
            contentStream.setFont(font, fontSize);
            float text_width = (font.getStringWidth(toWrite) / 1000.0f) * 15;
            contentStream.newLineAtOffset(x-text_width, y);
            contentStream.showText(toWrite);
            contentStream.endText();
        }
    }

    public void writeRoomLine(String toWrite, int x, int y) throws IOException {
        if(toWrite!=null) {
            String itemName;
            contentStream.beginText();
            int fontSize = 16;
            toWrite = new StringBuilder(toWrite + ":").reverse().toString();
            itemName = toWrite;

            contentStream.setNonStrokingColor(46, 204, 94);
            contentStream.setFont(font, fontSize);

            float text_width = (font.getStringWidth(itemName) / 1000.0f) * fontSize;
            contentStream.newLineAtOffset(x-text_width, y);
            contentStream.showText(itemName);
            contentStream.endText();
        }
    }

    public void writeNoteLine(String toWrite, int x, int y) throws IOException {
        if(toWrite!=null) {
            String itemName;
            contentStream.beginText();
            int fontSize = 14;
                toWrite = new StringBuilder(toWrite).reverse().toString();
                itemName = toWrite;

                contentStream.setNonStrokingColor(255, 255, 255);
                contentStream.setFont(font, fontSize);

            float text_width = (font.getStringWidth(itemName) / 1000.0f) * fontSize;
            contentStream.newLineAtOffset(x-text_width, y);
            contentStream.showText(itemName);
            contentStream.endText();
        }
    }

    public void writeItemLine(String toWrite, int x, int y, String extraDetail) throws IOException {
        if(toWrite!=null) {
            String itemName;
            contentStream.beginText();
            int fontSize = 15;

            toWrite = new StringBuilder(toWrite).reverse().toString();
            itemName = toWrite;

            fontSize = 14;
            contentStream.setNonStrokingColor(0, 0, 0);
            contentStream.setFont(font, fontSize);

            float text_width = (font.getStringWidth(itemName) / 1000.0f) * fontSize;
            contentStream.newLineAtOffset(x-text_width, y);
            contentStream.showText(itemName);
            contentStream.endText();

            if (extraDetail.length() > 0) {

                printDetail (")" , x-text_width, y);

                String itemDetails;
                fontSize = 11;
                contentStream.beginText();
                contentStream.setFont(font, fontSize);
                extraDetail = new StringBuilder(extraDetail).reverse().toString();
                itemDetails = extraDetail;

                contentStream.setNonStrokingColor(206, 27, 27);
                contentStream.setFont(font, fontSize);

                float new_text_width = (font.getStringWidth(itemDetails) / 1000.0f) * fontSize;
                contentStream.newLineAtOffset(x-text_width - new_text_width - 4, y);
                contentStream.showText(itemDetails);
                contentStream.endText();

                printDetail ("(" , x-text_width - new_text_width - 1, y);
            }
        }
    }

    public void printDetail(String print, float x, float y) throws IOException {
        int fontSize = 10;
        contentStream.beginText();

        contentStream.setNonStrokingColor(0, 0, 0);
        contentStream.setFont(font, fontSize);

        float new_text_width = (font.getStringWidth(print) / 1000.0f) * fontSize;
        contentStream.newLineAtOffset(x- new_text_width - 2, y);
        contentStream.showText(print);
        contentStream.endText();
    }

    public void writeOneItemLine(String toWrite, int x, int y, Boolean isHeadLine) throws IOException {
        if(toWrite!=null) {
            String itemName;
            contentStream.beginText();
            int fontSize = 15;
            if(isHeadLine)
            {
                toWrite = new StringBuilder(toWrite + ":").reverse().toString();
                itemName = toWrite;

                fontSize = 16;
                contentStream.setNonStrokingColor(206, 27, 27);
                contentStream.setFont(font, fontSize);
            }
            else {
                toWrite = new StringBuilder(toWrite).reverse().toString();
                itemName = toWrite;

                fontSize = 14;
                contentStream.setNonStrokingColor(0, 0, 0);
                contentStream.setFont(font, fontSize);
            }
            float text_width = (font.getStringWidth(itemName) / 1000.0f) * fontSize;
            contentStream.newLineAtOffset(x-text_width, y);
            contentStream.showText(itemName);
            contentStream.endText();
        }
    }

    /**
     * Loads an existing PDF and renders it to a Bitmap
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void renderFile(PDDocument document, OrderObject orderObject) throws IOException {
        // Render the page and save it to an image file

//        File renderFile = new File(path);
//
//        ParcelFileDescriptor pfd = ParcelFileDescriptor.open(renderFile, ParcelFileDescriptor.MODE_READ_ONLY);
//
//        PdfRenderer renderer = new PdfRenderer(pfd);
//
//        Bitmap bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_4444);
//
//        Renderer page = renderer.openPage(0);
//
//        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//
//        page.close();
//        renderer.close();



//        // create a new renderer
//        PdfRenderer renderer = new PdfRenderer(document);
//
//        // let us just render all pages
//        final int pageCount = renderer.getPageCount();
//        for (int i = 0; i < pageCount; i++) {
//            Page page = renderer.openPage(i);
//
//            // say we render for showing on the screen
//            page.render(mBitmap, null, null, Page.RENDER_MODE_FOR_DISPLAY);
//
//            // do stuff with the bitmap
//
//            // close the page
//            page.close();
//        }

//        // close the renderer
//        renderer.close();

        // Create a renderer for the document
        PDFRenderer renderer = new PDFRenderer(document);
        // Render the image to an RGB Bitmap
        pageImage = renderer.renderImage(0, 1, Bitmap.Config.RGBA_F16);
//        Bitmap b = renderer.renderImage(0);
//        PDPage pdpage = (PDPage) document.getDocumentCatalog().
//        Bitmap bitmap = pdpage.convertToImage(Config.RGB_565, 256);
//        imageView.setImageBitmap(bitmap);

        // Save the render result to an image
        String path = appData.getFilePath(orderObject) + appData.picFileName;
        File renderFile = new File(path);
        FileOutputStream fileOut = new FileOutputStream(renderFile);
        pageImage.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
        fileOut.close();
    }
}
