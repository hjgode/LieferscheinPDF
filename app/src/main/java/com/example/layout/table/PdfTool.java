package com.example.layout.table;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.ChapterAutoNumber;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Jpeg;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ContentHandler;

public class PdfTool {
    Document document;
    String dest;
    Context mContext;

    public PdfTool(Context context, Bundle bundle){
        mContext=context;
        File docudir = new File(getDocumentsStorageDir("Lieferschein"), "Signature_lieferschein.pdf");
        dest = docudir.getAbsolutePath();// getAppPath(mContext) + "123.pdf";
        PdfWriter pdfWriter=null;
        Document document=null;
        PdfDocument pdfDocument=null;
        try {
            document=new Document();
            FileOutputStream fileOutputStream=new java.io.FileOutputStream(dest);
            PdfWriter.getInstance(document,fileOutputStream);
//            pdfWriter=new PdfWriter( fileOutputStream);// PdfWriter.getInstance(document,fileOutputStream);// (new PdfDocument(), fileOutputStream);
//            pdfDocument = new PdfDocument(pdfWriter);
            //open document
            document.open();
            document.setPageSize(PageSize.A4);
        }catch (Exception ex){
            Log.d(TableLayoutActivity.TAG, ex.getMessage());
        }
        // Document Settings
//        PdfDocumentInfo info=pdfDocument.getDocumentInfo();
        document.addCreationDate();
        document.addTitle("Lieferschein");
        document.addAuthor("mich");
        document.addSubject("Lieferschein");
        document.addKeywords("Lieferschein, Unterschrift");
        document.addCreator("hjgode");

        /***
         * Variables for further use....
         */
        BaseColor mColorAccent = new BaseColor(153, 204, 255);
        BaseColor mColorBlack = new BaseColor(0, 0, 0);
        float mHeadingFontSize = 20.0f;
        float mValueFontSize = 20.0f;
        float mBodyFontSize = 12.0f;
        BaseFont baseFont=null;
        Font font=null;
        Font fontLarge=null;
        try {
            /**
             * How to USE FONT....
             */
            final String fontFreeMono = "assets/fonts/FreeMono.otf";
            baseFont=BaseFont.createFont(fontFreeMono,"UTF-8", BaseFont.EMBEDDED);
            font = new Font(baseFont, mBodyFontSize);
            fontLarge=new Font(baseFont, mHeadingFontSize);
        }catch (Exception ex){
            Log.d(TableLayoutActivity.TAG, "Exception for createfont "+ex.getMessage());
        }
        try{
            // LINE SEPARATOR
            DottedLineSeparator lineSeparator = new DottedLineSeparator();
            lineSeparator.setLineColor (new BaseColor(0, 0, 68));

            // Title Order Details...
            // Adding Title....
            Chunk mOrderDetailsTitleChunk = new Chunk("Lieferschein", fontLarge);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            // Fields of Order Details...
            // Adding Chunks for Title and value
            Chunk mOrderIdChunk = new Chunk("Order No:", fontLarge);
            Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
            document.add(mOrderIdParagraph);

            Chunk mOrderIdValueChunk = new Chunk("#123123", font);
            Paragraph mOrderIdValueParagraph = new Paragraph(mOrderIdValueChunk);
            document.add(mOrderIdValueParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(""));
            // Adding Horizontal Line...
            document.add(lineSeparator);
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            // Fields of Order Details...
            Chunk mOrderDateChunk = new Chunk("Datum:", fontLarge);
            Paragraph mOrderDateParagraph = new Paragraph(mOrderDateChunk);
            document.add(mOrderDateParagraph);

            Chunk mOrderDateValueChunk = new Chunk("16.11.2021",fontLarge);
            Paragraph mOrderDateValueParagraph = new Paragraph(mOrderDateValueChunk);
            document.add(mOrderDateValueParagraph);

            document.add(new Paragraph(""));
            document.add(lineSeparator);
            document.add(new Paragraph(""));

            // Fields of Order Details...
            Chunk mOrderAcNameChunk = new Chunk("Kunden Name:", font);
            Paragraph mOrderAcNameParagraph = new Paragraph(mOrderAcNameChunk);
            document.add(mOrderAcNameParagraph);

            Chunk mOrderAcNameValueChunk = new Chunk("Max Mustermann", font);
            Paragraph mOrderAcNameValueParagraph = new Paragraph(mOrderAcNameValueChunk);
            document.add(mOrderAcNameValueParagraph);

            document.add(new Paragraph(""));
            document.add(lineSeparator);
            document.add(new Paragraph(""));

            PdfPTable table=new PdfPTable(2);
            table.setWidthPercentage(80f);
            // Adding cell 1 to the table
            float[] columnw={150f,150f,150f};
            PdfPCell cell1 = new PdfPCell(new Phrase("Menge"));
            cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

            //cell1.add(new BlockElement<String>(){"Name"});         // Adding content to the cell
            table.addCell(cell1);      // Adding cell to the table

            // Adding cell 2 to the table Cell
            PdfPCell cell2 = new PdfPCell(new Paragraph("Artikel"));
            cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);// Creating a cell
            table.addCell(cell2);     // Adding cell to the table

            document.add(table);

            //Inserting Image in PDF
            // Creating an ImageData object
            String imageFile = bundle.getString(Constants.BUNDLE_SIGNATUREFILE);
            Image image = Image.getInstance(imageFile);
            // Creating an Image object
            image.setScaleToFitHeight(true);
            image.scaleToFit(100f,100f);

            document.add(image);

            document.close();
            Toast.makeText(context, "PDF gespeichert: "+dest,Toast.LENGTH_LONG);

        }catch (Exception ex){
            Log.d(TableLayoutActivity.TAG, "document.add exception: "+ex.getMessage());
        }

    }
    /**
     * Get Path of App which contains Files
     *
     * @return path of root dir
     */
    public static String getAppPath(Context context) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory()
                + File.separator
                + context.getResources().getString(R.string.app_name)
                + File.separator);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath() + File.separator;
    }
    public File getDocumentsStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e(TableLayoutActivity.TAG, "Directory not created");
        }
        return file;
    }

}
