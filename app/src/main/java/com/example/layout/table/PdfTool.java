package com.example.layout.table;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
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
            FileOutputStream fileOutputStream=new java.io.FileOutputStream(dest);
            pdfWriter=new PdfWriter(fileOutputStream);// PdfWriter.getInstance(document,fileOutputStream);// (new PdfDocument(), fileOutputStream);
            pdfDocument = new PdfDocument(pdfWriter);
        }catch (Exception ex){
            Log.d(TableLayoutActivity.TAG, ex.getMessage());
        }
        // Document Settings
        PdfDocumentInfo info=pdfDocument.getDocumentInfo();
        info.addCreationDate();
        info.setTitle("Lieferschein");
        info.setAuthor("mich");
        info.setSubject("Lieferschein");
        info.setKeywords("Lieferschein, Unterschrift");
        info.setCreator("hjgode");
        //open document
        document=new Document(pdfDocument, PageSize.A4, true);

        /***
         * Variables for further use....
         */
        Color mColorAccent = new DeviceRgb(153, 204, 255);
        Color mColorBlack = new DeviceRgb(0, 0, 0);
        float mHeadingFontSize = 20.0f;
        float mValueFontSize = 26.0f;
        PdfFont font=null;
        try {
            /**
             * How to USE FONT....
             */
            font = PdfFontFactory.createFont("assets/fonts/FreeMono.otf", "UTF-8", true);
        }catch (Exception ex){
            Log.d(TableLayoutActivity.TAG, "Exception for createfont "+ex.getMessage());
        }
        try{
            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator(new DottedLine());
            lineSeparator.setStrokeColor(new DeviceRgb(0, 0, 68));

            // Title Order Details...
            // Adding Title....
            Text mOrderDetailsTitleChunk = new Text("Order Details").setFont(font).setFontSize(36.0f).setFontColor(mColorBlack);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(mOrderDetailsTitleParagraph);

            // Fields of Order Details...
            // Adding Chunks for Title and value
            Text mOrderIdChunk = new Text("Order No:").setFont(font).setFontSize(mHeadingFontSize).setFontColor(mColorAccent);
            Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
            document.add(mOrderIdParagraph);

            Text mOrderIdValueChunk = new Text("#123123").setFont(font).setFontSize(mValueFontSize).setFontColor(mColorBlack);
            Paragraph mOrderIdValueParagraph = new Paragraph(mOrderIdValueChunk);
            document.add(mOrderIdValueParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(""));
            // Adding Horizontal Line...
            document.add(lineSeparator);
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            // Fields of Order Details...
            Text mOrderDateChunk = new Text("Order Date:").setFont(font).setFontSize(mHeadingFontSize).setFontColor(mColorAccent);
            Paragraph mOrderDateParagraph = new Paragraph(mOrderDateChunk);
            document.add(mOrderDateParagraph);

            Text mOrderDateValueChunk = new Text("06/07/2017").setFont(font).setFontSize(mValueFontSize).setFontColor(mColorBlack);
            Paragraph mOrderDateValueParagraph = new Paragraph(mOrderDateValueChunk);
            document.add(mOrderDateValueParagraph);

            document.add(new Paragraph(""));
            document.add(lineSeparator);
            document.add(new Paragraph(""));

            // Fields of Order Details...
            Text mOrderAcNameChunk = new Text("Account Name:").setFont(font).setFontSize(mHeadingFontSize).setFontColor(mColorAccent);
            Paragraph mOrderAcNameParagraph = new Paragraph(mOrderAcNameChunk);
            document.add(mOrderAcNameParagraph);

            Text mOrderAcNameValueChunk = new Text("Pratik Butani").setFont(font).setFontSize(mValueFontSize).setFontColor(mColorBlack);
            Paragraph mOrderAcNameValueParagraph = new Paragraph(mOrderAcNameValueChunk);
            document.add(mOrderAcNameValueParagraph);

            document.add(new Paragraph(""));
            document.add(lineSeparator);
            document.add(new Paragraph(""));


            //Inserting Image in PDF
            // Creating an ImageData object
            String imageFile = bundle.getString("signaturefile");
            ImageData data = ImageDataFactory.create(imageFile);
            // Creating an Image object
            Image img = new Image(data);
            img.setAutoScaleWidth(true);
            img.setHeight(100);
            document.add(img);

        }catch (Exception ex){
            Log.d(TableLayoutActivity.TAG, "document.add exception: "+ex.getMessage());
        }
        document.close();

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
