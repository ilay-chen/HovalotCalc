package com.icstudios.hovalotcalc.ordercreate;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import com.icstudios.hovalotcalc.OrderObject;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.font.PDType0Font;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;
import java.io.File;
import java.io.IOException;

public class MakePDFOffer {

    File root;
    AssetManager assetManager;
    Context context;
    PDPageContentStream contentStream;
    PDDocument document;
    PDType0Font font;

    public MakePDFOffer(Context context)
    {
        this.context = context;
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
            writeOneLine("מספר בית", false,455,617);
            String isElevator = "יש";
            if(!orderDetails.getFromElevator()) isElevator = "אין";
            writeOneLine(isElevator, true,350,617);

            //address to
            writeOneLine(orderDetails.getFromCity(), true,210,667);
            writeOneLine(orderDetails.getFromStreet(), true,90,667);
            writeOneLine(orderDetails.getFromNumber(), false,17,667);

            writeOneLine(orderDetails.getFromFloor(), false,245,617);
            writeOneLine("מספר בית", false,155,617);
            isElevator = "יש";
            if(!orderDetails.getFromElevator()) isElevator = "אין";
            writeOneLine(isElevator, true,50,617);

            //all items
            //writeOneLine("כל הפרטים", true,525,560);
            String line = "";
            String roomName = orderDetails.getRoomsAndItems().get(0).roomName.getText().toString();
            int y = 550;
            int x = 570;
            for (RoomLayout roomLayout: orderDetails.getRoomsAndItems()) {
                if(!roomLayout.roomName.getText().toString().equals(""))
                {
                    writeOneItemLine(roomLayout.roomName.getText().toString(),x,y, true);
                    //writeOneLine(roomLayout.roomName.getText().toString(), true,getXPos(roomLayout.roomName.getText().toString().length()),y);
                    y-=15;
                }

                for(ItemLayout itemLayout:roomLayout.mItems)
                {
                    String itemName = itemLayout.itemName.getText().toString();
                    writeOneItemLine(itemName,x,y,false);
                    y-=15;
                }

                //roomName = RoomLayout.roomName.getText().toString();
            }


            //extra details
            writeOneLine(orderDetails.getBoxes(), false,457,155);
            writeOneLine(orderDetails.getSuitcases(), false,457,122);
            writeOneLine(orderDetails.getBags(), false,457,90);

//            String isPacking = "יש";
//            if(!orderDetails.get()) isPacking = "אין";
            writeOneLine("אין נתונים", true,342,155);
            writeOneLine("25", false,260,122);
            writeOneLine("350", false,255,90);

            String notes = orderDetails.getNotes();
            y = 145;
            do {

                String partNote = notes.substring(0,notes.indexOf(" ", 20));
                notes = notes.replace(partNote, "");
                writeOneItemLine(partNote,20,y, false);
                y+=15;
            }
            while (notes.length()>0);

            //writeOneLine("יש כאן עוד משפט ארוך בנודע" + "" + "שורה שנייה של משפט", true,20,145);

            //price
            writeOneLine(orderDetails.getPrice()+"", false,100,45);

            //contentStream.endText();

            // Make sure that the content stream is closed:
            contentStream.close();

            // Save the final pdf document to a file
            String path = root.getAbsolutePath() + "/filled_offer.pdf";
            document.save(path);
            document.close();
            //tv.setText("Successfully wrote PDF to " + path);

        } catch (IOException e) {
            Log.e("PdfBox-Android-Sample", "Exception thrown while creating PDF", e);
        }
    }

    public int getXPos(int len)
    {
        int x = 525;
        x = x - (len * 10);
        return x;
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
}
