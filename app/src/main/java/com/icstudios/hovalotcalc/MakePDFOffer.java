package com.icstudios.hovalotcalc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.font.PDType0Font;
import com.tom_roush.pdfbox.rendering.PDFRenderer;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
            font = PDType0Font.load(document, assetManager.open("nachlieli_light.ttf"));
            PDPage page = document.getPage(0);
            //document.addPage(page);

            // Define a content stream for adding to the PDF
            contentStream = new PDPageContentStream(document, page, true, true);

            //name
            writeOneLine(orderDetails.getClientName(),true,490,815);

            //date and o'clock
            writeOneLine(orderDetails.getDate(), false,440,788);
            writeOneLine(orderDetails.getHour(), false,475,763);

            //address from
            writeOneLine(orderDetails.getFromCity(), true,510,667);
            writeOneLine(orderDetails.getFromStreet(), true,390,667);
            writeOneLine(orderDetails.getFromNumber(), false,317,667);

            writeOneLine(orderDetails.getFromFloor(), false,545,617);
            writeOneLine("0", false,455,617);
            String isElevator = "יש";
            if(!orderDetails.getFromElevator()) isElevator = "אין";
            writeOneLine(isElevator, true,350,617);

            //address to
            writeOneLine(orderDetails.getFromCity(), true,210,667);
            writeOneLine(orderDetails.getFromStreet(), true,90,667);
            writeOneLine(orderDetails.getFromNumber(), false,17,667);

            writeOneLine(orderDetails.getFromFloor(), false,245,617);
            writeOneLine("0", false,155,617);
            isElevator = "יש";
            if(!orderDetails.getFromElevator()) isElevator = "אין";
            writeOneLine(isElevator, true,50,617);

            //all items
            //writeOneLine("כל הפרטים", true,525,560);
            //String line = "";
            //String roomName = orderDetails.getRoomsAndItems().get(0).roomName.getText().toString();
            int y = 550;
            int x = 570;
            for (roomObject roomLayout: orderDetails.getRoomsAndItems()) {
                if(!roomLayout.roomName.equals(""))
                {
                    if(y <= 180) {
                        y = 560;
                        x = 290;
                    }
                    writeOneItemLine(roomLayout.roomName,x,y, true);
                    //writeOneLine(roomLayout.roomName.getText().toString(), true,getXPos(roomLayout.roomName.getText().toString().length()),y);
                    y-=20;
                }

                for(itemObject itemLayout:roomLayout.mItems)
                {
                    if(y <= 180) {
                        y = 560;
                        x = 290;
                    }

                    String itemName = itemLayout.itemName + ", " + itemLayout.itemCounter;
                    if(itemLayout.DisassemblyAndAssembly)
                    {
                        itemName += ", כולל פירוק והובלה";
                    }
                    writeOneItemLine(itemName,x,y,false);
                    y-=20;
                }

                //roomName = RoomLayout.roomName.getText().toString();
            }


            //extra details
            writeOneLine(orderDetails.getBoxes(), false,457,155);
            writeOneLine(orderDetails.getSuitcases(), false,457,122);
            writeOneLine(orderDetails.getBags(), false,457,90);

            writeOneLine("0", true,342,155);
            writeOneLine("25", false,260,122);
            writeOneLine("350", false,255,90);

            String notes = orderDetails.getNotes();
            y = 145;

            while (notes != null && notes.length()>0)
            {
                int minIndex = 20;
                int substring = 0;
                if(notes.length()<20)
                    minIndex = 0;
                substring = notes.indexOf(" ", minIndex) + 1;
                if(substring==-1 || substring == 0)
                    substring = notes.length();

                String partNote = notes.substring(0,substring);
                notes = notes.replace(partNote, "");
                writeOneItemLine(partNote,20,y, false);
                y+=15;
            }

            //writeOneLine("יש כאן עוד משפט ארוך בנודע" + "" + "שורה שנייה של משפט", true,20,145);

            //price
            writeOneLine(orderDetails.getPrice()+"", false,100,45);

            //contentStream.endText();

            // Make sure that the content stream is closed:
            contentStream.close();

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();
            orderDetails.setId(orderDetails.getClientName().replaceAll(" ", "_") + "_" + ts);

            // Save the final pdf document to a file
            //String yourFilePath = context.getFilesDir() + "/" + folder + "/" + fileName;
            String path = appData.getFilePath(orderDetails) + appData.pdfFileName;

            appData.makeDir(context, orderDetails.getId());

            document.save(path);

            renderFile(document, orderDetails);

            appData.updateOrderList(orderDetails);

            sortOrders();

            appData.saveData(context);

            document.close();
            //tv.setText("Successfully wrote PDF to " + path);
            finishAndOpenOrdersPage();

        } catch (IOException e) {
            Log.e("PdfBox-hovalotCalc", "Exception thrown while creating PDF => ", e);
        }
    }

    public void finishAndOpenOrdersPage()
    {
        currentActivity.finish();
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }

    public void writeOneLine(String toWrite, Boolean isReversible, int x, int y) throws IOException {
        if(toWrite!=null) {
            contentStream.beginText();
            contentStream.setNonStrokingColor(0, 0, 0);
            contentStream.setFont(font, 15);
            contentStream.newLineAtOffset(x, y);
            if (isReversible) toWrite = new StringBuilder(toWrite).reverse().toString();
            contentStream.showText(toWrite);
            contentStream.endText();
        }
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

                fontSize = 20;
                contentStream.setNonStrokingColor(0, 100, 0);
                contentStream.setFont(font, fontSize);
            }
            else {
                toWrite = new StringBuilder(toWrite).reverse().toString();
                itemName = toWrite;

                fontSize = 15;
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
    public void renderFile(PDDocument document, OrderObject orderObject) throws IOException {
        // Render the page and save it to an image file

        // Create a renderer for the document
        PDFRenderer renderer = new PDFRenderer(document);
        // Render the image to an RGB Bitmap
        pageImage = renderer.renderImage(0, 1, Bitmap.Config.RGB_565);

        // Save the render result to an image
        String path = appData.getFilePath(orderObject) + appData.picFileName;
        File renderFile = new File(path);
        FileOutputStream fileOut = new FileOutputStream(renderFile);
        pageImage.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
        fileOut.close();
    }
}
