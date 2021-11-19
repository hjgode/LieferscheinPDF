package com.example.pdf.lieferschein;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

//NOT USED, but here for reference of other PDF possibilties
public class PdfTool {
    Document document;
    String dest;
    Context mContext;

    public PdfTool(Context context, Bundle bundle){
        mContext=context;
        Log.d(MainActivity.TAG, "pdf document about created...");

        String pdfFileName=bundle.getString(Constants.BUNDLE_PDF_FILE,"Signature_lieferschein.pdf");
        String eMailAddress=bundle.getString(Constants.BUNDLE_PDF_EMAIL,"");

        File docudir = new File(getDocumentsStorageDir("Lieferschein"), pdfFileName);
        dest = docudir.getAbsolutePath();// getAppPath(mContext) + "123.pdf";
        PdfWriter pdfWriter=null;
        Document document=null;
        PdfDocument pdfDocument=null;

        //get bundle values
//        ArrayList<Artikel> artikelListe=bundle.getParcelableArrayList(Constants.BUNDLE_ARTIKEL_LISTE);
        // -OR-
        List<Artikel> artikelListe;
        ArtikelList artikelList=new ArtikelList();
        artikelList.restoreList(context);
        artikelListe=artikelList._artikelliste;

        String kundenname=bundle.getString(Constants.BUNDLE_KUNDEN_NAME);
        String kundenstrasse=bundle.getString(Constants.BUNDLE_KUNDEN_STRASSE);
        String kundenort=bundle.getString(Constants.BUNDLE_KUNDEN_ORT);
        String lieferstart=bundle.getString(Constants.BUNDLE_LIEFER_START);
        String lieferende=bundle.getString(Constants.BUNDLE_LIEFER_ENDE);

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
            Log.d(MainActivity.TAG, ex.getMessage());
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
        Font fontBody=null;
        Font fontLarge=null;
        try {
            /**
             * How to USE FONT....
             */
            final String fontFreeMono = "assets/fonts/FreeMono.otf";
            baseFont=BaseFont.createFont(fontFreeMono,"UTF-8", BaseFont.EMBEDDED);
            fontBody = new Font(baseFont, mBodyFontSize);
            fontLarge=new Font(baseFont, mHeadingFontSize);
        }catch (Exception ex){
            Log.d(MainActivity.TAG, "Exception for createfont "+ex.getMessage());
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

            Chunk mOrderIdValueChunk = new Chunk("#123123", fontBody);
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
            Chunk mOrderAcNameChunk = new Chunk("Kunden Name:", fontBody);
            Paragraph mOrderAcNameParagraph = new Paragraph(mOrderAcNameChunk);
            mOrderAcNameParagraph.setIndentationLeft(200f);
            document.add(mOrderAcNameParagraph);

            Chunk mOrderAcNameValueChunk = new Chunk("Max Mustermann", fontBody);
            Paragraph mOrderAcNameValueParagraph = new Paragraph(mOrderAcNameValueChunk);
            mOrderAcNameValueParagraph.setIndentationLeft(200f);
            document.add(mOrderAcNameValueParagraph);

            document.add(new Paragraph(""));
            document.add(lineSeparator);
            document.add(new Paragraph(""));

            PdfPTable table=new PdfPTable(2);
            table.setWidths(new int[]{10,90});
            table.setWidthPercentage(80f);
            table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(10f);

            //add header
            /*Sets the number of rows to be used for the footer. The number of footer rows are
              subtracted from the header rows. For example, for a table with two header rows
              and one footer row the code would be:

             table.setHeaderRows(3);
             table.setFooterRows(1);

            Row 0 and 1 will be the header rows and row 2 will be the footer row.

             */
            table.setHeaderRows(2);
            table.setFooterRows(1);
            //header row
            addRow(table,"Menge", "Artikel");

            //footer row(s)
            int artikelcount=0;
            if(artikelListe.size()!=0){
                for (Artikel a:artikelListe
                ) {
                    artikelcount+=Integer.parseInt(a.get_menge());
                }
                addRow(table, artikelcount+"","Packstücke");

            }else{
                addRow(table, "46","Packstücke");
            }

            //add sample data
            if(artikelListe.size()==0) {
                // Adding cells 1 to the table, rows will be started automatically after row is full
                addRow(table, "10", "Artikeltext");
                addRow(table, "5", "Holzleisten");
                addRow(table, "8", "Kanthölzer");
                addRow(table, "10", "Artikeltext");
                addRow(table, "5", "Holzleisten");
                addRow(table, "8", "Kanthölzer");
            }else{
                for (Artikel a:artikelListe
                ) {
                    addRow(table, a.get_menge().toString(), a.get_artikelnummer().toString());
                }

            }

            Paragraph pTable=new Paragraph();
            pTable.add(table);
            pTable.setIndentationLeft(30);
            document.add(pTable);//document.add(table);

            // see https://github.com/venkatvkpt/Invoice-PDF-ITEXT-/blob/master/src/com/pdf/InvoiceGenerator.java
            PdfPTable irdTable = new PdfPTable(2);
            irdTable.addCell(getIRDCell("Invoice No"));
            irdTable.addCell(getIRDCell("Invoice Date"));
            irdTable.addCell(getIRDCell("XE1234")); // pass invoice number
            irdTable.addCell(getIRDCell("13-Mar-2016")); // pass invoice date

            PdfPTable irhTable = new PdfPTable(3);
            irhTable.setWidthPercentage(100);

            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("Invoice", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            PdfPCell invoiceTable = new PdfPCell (irdTable);
            invoiceTable.setBorder(0);
            irhTable.addCell(invoiceTable);

            FontSelector fs = new FontSelector();
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
            fs.addFont(font);
            Phrase bill = fs.process("Bill To"); // customer information
            Paragraph name = new Paragraph("Herr Max Mustermann");
            name.setIndentationLeft(20);
            Paragraph contact = new Paragraph("9652886877");
            contact.setIndentationLeft(20);
            Paragraph address = new Paragraph("Kuchipudi,Movva");
            address.setIndentationLeft(20);

            PdfPTable billTable = new PdfPTable(6); //one page contains 15 records
            billTable.setWidthPercentage(100);
            billTable.setWidths(new float[] { 1, 2,5,2,1,2 });
            billTable.setSpacingBefore(30.0f);
            billTable.addCell(getBillHeaderCell("Index"));
            billTable.addCell(getBillHeaderCell("Item"));
            billTable.addCell(getBillHeaderCell("Description"));
            billTable.addCell(getBillHeaderCell("Unit Price"));
            billTable.addCell(getBillHeaderCell("Qty"));
            billTable.addCell(getBillHeaderCell("Amount"));

            billTable.addCell(getBillRowCell("1"));
            billTable.addCell(getBillRowCell("Mobile"));
            billTable.addCell(getBillRowCell("Nokia Lumia 610 \n IMI:WQ361989213 "));
            billTable.addCell(getBillRowCell("12000.0"));
            billTable.addCell(getBillRowCell("1"));
            billTable.addCell(getBillRowCell("12000.0"));

            billTable.addCell(getBillRowCell("2"));
            billTable.addCell(getBillRowCell("Accessories"));
            billTable.addCell(getBillRowCell("Nokia Lumia 610 Panel \n Serial:TIN3720 "));
            billTable.addCell(getBillRowCell("200.0"));
            billTable.addCell(getBillRowCell("1"));
            billTable.addCell(getBillRowCell("200.0"));

            PdfPTable accounts = new PdfPTable(2);
            accounts.setWidthPercentage(100);
            accounts.addCell(getAccountsCell("Subtotal"));
            accounts.addCell(getAccountsCellR("12620.00"));
            accounts.addCell(getAccountsCell("Discount (10%)"));
            accounts.addCell(getAccountsCellR("1262.00"));
            accounts.addCell(getAccountsCell("Tax(2.5%)"));
            accounts.addCell(getAccountsCellR("315.55"));
            accounts.addCell(getAccountsCell("Total"));
            accounts.addCell(getAccountsCellR("11673.55"));
            PdfPCell summaryR = new PdfPCell (accounts);
            summaryR.setColspan (3);
            billTable.addCell(summaryR);

            document.add(irhTable);
            document.add(bill);
            document.add(name);
            document.add(contact);
            document.add(address);
            document.add(billTable);

            //Inserting Image in PDF
            // Creating an ImageData object
            String imageFile = bundle.getString(Constants.BUNDLE_SIGNATUREFILE);
            Image image = Image.getInstance(imageFile);
            // Creating an Image object
            image.setScaleToFitHeight(true);
            image.scaleToFit(100f,100f);
            //image.setAlignment(Image.ALIGN_RIGHT);
            image.setIndentationLeft(200);

            document.add(image);

            document.close();
            Toast.makeText(context, "PDF gespeichert: "+dest,Toast.LENGTH_LONG);
            Log.d(MainActivity.TAG, "pdf document ready: "+dest);

        }catch (Exception ex){
            Log.d(MainActivity.TAG, "document.add exception: "+ex.getMessage());
        }

    }

    private void addRow(PdfPTable pdfPTable, String menge, String text){
        PdfPCell cell1 = new PdfPCell(new Phrase(menge));
        cell1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        pdfPTable.addCell(cell1);      // Adding cell to the table

        PdfPCell cell2 = new PdfPCell(new Phrase(text));
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        pdfPTable.addCell(cell2);      // Adding cell to the table

    }

    public static PdfPCell getIRHCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }
    public static PdfPCell getIRDCell(String text) {
        PdfPCell cell = new PdfPCell (new Paragraph (text));
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (5.0f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    public static PdfPCell getBillHeaderCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (5.0f);
        return cell;
    }
    public static PdfPCell getBillRowCell(String text) {
        PdfPCell cell = new PdfPCell (new Paragraph (text));
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getBillFooterCell(String text) {
        PdfPCell cell = new PdfPCell (new Paragraph (text));
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }
    public static PdfPCell getAccountsCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setPadding (5.0f);
        return cell;
    }
    public static PdfPCell getAccountsCellR(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
        cell.setPadding (5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
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
            Log.e(MainActivity.TAG, "Directory not created");
        }
        return file;
    }

}
