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
            writeOneLine("יוני שוורץ",true,490,815);

            //date and o'clock
            writeOneLine("5.8.2020", false,440,788);
            writeOneLine("07:00", false,475,763);

            //address from
            writeOneLine("חדרה", true,510,667);
            writeOneLine("קוממיות", true,390,667);
            writeOneLine("9", false,317,667);

            writeOneLine("2", false,545,617);
            writeOneLine("4", false,455,617);
            writeOneLine("אין", true,350,617);

            //address to
            writeOneLine("כפר סבא", true,210,667);
            writeOneLine("המלכים", true,90,667);
            writeOneLine("3", false,17,667);

            writeOneLine("12", false,245,617);
            writeOneLine("34", false,155,617);
            writeOneLine("יש", true,50,617);

            //all items
            writeOneLine("כל הפרטים", true,525,560);


            //extra details
            writeOneLine("10", false,457,155);
            writeOneLine("40", false,457,122);
            writeOneLine("30", false,457,90);

            writeOneLine("יש", true,342,155);
            writeOneLine("25", false,260,122);
            writeOneLine("350", false,255,90);

            writeOneLine("יש כאן עוד משפט ארוך בנודע", true,20,145);

            //price
            writeOneLine("1,250", false,100,45);

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

    public void writeOneLine(String toWrite, Boolean isReversible, int x, int y) throws IOException {
        contentStream.beginText();
        contentStream.setNonStrokingColor(0, 0, 0);
        contentStream.setFont(font, 15);
        contentStream.newLineAtOffset(x, y);
        if(isReversible) toWrite = new StringBuilder(toWrite).reverse().toString();
        contentStream.showText(toWrite);
        contentStream.endText();
    }
}
