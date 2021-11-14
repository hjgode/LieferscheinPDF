package com.example.layout.table;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class WritePdf {
    static PdfWriter pdfwriter;
    Context _context =null;
    public WritePdf(Context context, Bundle bundle) throws IOException, BadElementException {
        _context=context;
        String pdffile=bundle.getString(Constants.BUNDLE_PDF_FILE);// "/home/hgode/Documents/TestFile.pdf";
        String eMail=bundle.getString(Constants.BUNDLE_PDF_EMAIL);

        Image imageLogo=getImageFromAssets("images/LoGo.jpg");

//        String signaturefile="/home/hgode/Pictures/sign.png";
        String signaturefile = bundle.getString(Constants.BUNDLE_SIGNATUREFILE);

        String kundenname="Kundenname",kundenstrasse="Kundenstrasse 20",kundenort="55500 Kundenort";
        kundenname=bundle.getString(Constants.BUNDLE_KUNDEN_NAME);
        kundenstrasse=bundle.getString(Constants.BUNDLE_KUNDEN_STRASSE);
        kundenort=bundle.getString(Constants.BUNDLE_KUNDEN_ORT);

        String firmadaten=context.getResources().getString(R.string.firmadaten);

        String lieferdatum="21.10.2021";
        String liefernummer="12345689";
        String lieferankunft="10:30", lieferabfahrt="11:15";
        lieferdatum=bundle.getString(Constants.BUNDLE_LIEFER_DATUM);
        liefernummer=bundle.getString(Constants.BUNDLE_LIEFER_NUMMER);
        lieferankunft=bundle.getString(Constants.BUNDLE_LIEFER_START);
        lieferabfahrt=bundle.getString(Constants.BUNDLE_LIEFER_ENDE);

		try {
        float left = 30;
        float right = 20;
        float top = 20;
        float bottom = 20;

//Create Document instance.
        Document document = new Document(PageSize.A4, left,right,top,bottom);
//Create OutputStream instance.
        OutputStream outputStream = new FileOutputStream(new File(pdffile));
//Create PDFWriter instance.
        pdfwriter = PdfWriter.getInstance(document, outputStream);
//Open the document.
        document.open();
        Rectangle pagesize = document.getPageSize();
        // Creating a table
        float w=pagesize.getWidth()-left-right;

        float [] pointColumnWidths = {w/2, w/2};
        PdfPTable table = new PdfPTable(2);//pointColumnWidths);
        PdfPCell cellImage= new PdfPCell();
        PdfPCell cellFirma=new PdfPCell();
        //paragraph to place tale in
        Paragraph pHeadTable=new Paragraph();
        pHeadTable.setIndentationLeft(20);

        //Create Image object
        Image image = imageLogo;// Image.getInstance(logofile);
        image.setScaleToFitHeight(true);
        image.scaleAbsoluteHeight(100);

        Paragraph pFirma=new Paragraph(firmadaten);
        pFirma.setIndentationLeft(20);

        cellImage.addElement(image);
        cellImage.setBorder(0);// Rectangle.BOX);

        cellFirma.addElement(pFirma);
        cellFirma.setBorder(0);

        table.setWidthPercentage(100);

        table.addCell(cellImage);
        table.addCell(cellFirma);
        //table.setWidths(new float[] {40f,60f});

        pHeadTable.add(table);

        document.add(pHeadTable);// table);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        Paragraph pAddress=new Paragraph(kundenname+"\n"+kundenstrasse+"\n"+kundenort);
        pAddress.setIndentationLeft(20);

        //does not support line breaks
//	        PlaceChunck("Kundenname1111\nKundenstrasse 20\n55500 Kundenort", 20, 200, pdfwriter);

        //Add content to the document.
        document.add(pAddress);

        float curY=pdfwriter.getVerticalPosition(true);
/*	        for(int i=0;i<10;i++) {
	        	document.add(new Paragraph("\n"));
	        	System.out.println("curY="+curY);
	        	curY=pdfwriter.getVerticalPosition(true);
	        }
*/	        while(curY>500) {
            document.add(new Paragraph("\n"));
            curY=pdfwriter.getVerticalPosition(true);
        }

        //datum
        PdfPTable tableInfo=new PdfPTable(new float[] {30,30});
        tableInfo.addCell(getCellLeftNoBorder("Datum:"));
        tableInfo.addCell(getCellLeftNoBorder(lieferdatum));

        tableInfo.addCell(getCellLeftNoBorder("Lieferschein:"));
        tableInfo.addCell(getCellLeftNoBorder(liefernummer));

        Paragraph pDatum=new Paragraph();
        pDatum.add(tableInfo);
        pDatum.setIndentationLeft(340);
        document.add(pDatum);
        document.add(new Paragraph("\n"));

        LineSeparator lineSeparator=new LineSeparator();
        lineSeparator.setLineWidth(2);
//	        DottedLineSeparator lineSeparator = new DottedLineSeparator();
        lineSeparator.setLineColor (new BaseColor(0, 0, 68));
        document.add(lineSeparator);

        Paragraph pLieferschein=new Paragraph("Lieferschein",new Font(Font.FontFamily.HELVETICA, 24f));
        pLieferschein.setIndentationLeft(30);

        document.add(pLieferschein);

        PdfPTable tableArtikel=new PdfPTable(4);

        tableArtikel.setWidths(new int[] {10,20,60,10});
        List<Artikel> artikel=new ArrayList<Artikel>();

        Artikel art=new Artikel("1", "123456", "Artikeltext", "21,99");
        artikel.add(art);
        art=new Artikel("2", "1236", "Artikeltext2", "34,50");
        artikel.add(art);
        art=new Artikel("1", "0ABcX", "Weiterer Artikel", "79,90");
        artikel.add(art);

        tableArtikel.setHeaderRows(2); //total header/footer rows
        tableArtikel.setFooterRows(1);
        //header
        PdfPCell cell=getCellCentered("Menge");
        tableArtikel.addCell(cell);

        cell=getCellLeft("Artikelnummer");
        tableArtikel.addCell(cell);

        cell=getCellLeft("Text");
        tableArtikel.addCell(cell);

        cell=getCellLeft("Preis");
        tableArtikel.addCell(cell);

        //footer
        cell=getCellLeft("");
        cell.setBorder(PdfPCell.NO_BORDER);
        tableArtikel.addCell(cell);
        tableArtikel.addCell(cell);

        cell=getCellRight("SUMME");
        cell.setBorder(PdfPCell.NO_BORDER);
        tableArtikel.addCell(cell);

        cell=getCellRight("xxx,yy");
        tableArtikel.addCell(cell);

        for (Artikel artikel2 : artikel) {
            cell=getCellCentered(artikel2.get_menge());
            tableArtikel.addCell(cell);

            cell=getCellLeft(artikel2.get_artikelnummer());
            tableArtikel.addCell(cell);

            tableArtikel.addCell(getCellLeft(artikel2.get_artikeltext()));

            cell=getCellRight(artikel2.get_preistext());
            tableArtikel.addCell(cell);
        }

        tableArtikel.setSpacingBefore(10);
        tableArtikel.setSpacingAfter(10);
        document.add(tableArtikel);

        document.add(lineSeparator);

        float kundenIndent=300;
        Paragraph pKunde=new Paragraph("Kunde");
        pKunde.setIndentationLeft(kundenIndent);
        pKunde.setSpacingBefore(80);

        //Create Image object
        Image imageSign = Image.getInstance(signaturefile);
        imageSign.setScaleToFitHeight(true);
        imageSign.scaleAbsoluteHeight(100);
        imageSign.enableBorderSide(Rectangle.BOTTOM);
        imageSign.setBorderColor(BaseColor.GRAY);
        imageSign.setUseVariableBorders(true);
        imageSign.setBorderWidth(1f);
        imageSign.setIndentationLeft(kundenIndent);

/*
	        PdfContentByte cb=pdfwriter.getDirectContent();
	        cb.saveState();
	        cb.setColorStroke(BaseColor.BLACK);
	        cb.rectangle(imageSign.getLeft(),imageSign.getTop(),imageSign.getWidth(),imageSign.getHeight());
	        cb.stroke();
	        cb.restoreState();
*/

        PdfPTable tableTimes=new PdfPTable(2);
        tableTimes.setTotalWidth(160);
        tableTimes.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        tableTimes.setLockedWidth(true);
        tableTimes.addCell(getCellLeftNoBorder("Lieferankunft"));
        tableTimes.addCell(getCellLeftNoBorder(lieferankunft));
        tableTimes.addCell(getCellLeftNoBorder("Abfahrt"));
        tableTimes.addCell(getCellLeftNoBorder(lieferabfahrt));

        Paragraph ptableTimes=new Paragraph();
        ptableTimes.setIndentationLeft(left);
        ptableTimes.add(tableTimes);

        document.add(ptableTimes);

        document.add(pKunde);
        document.add(imageSign);

        document.close();
        outputStream.close();
        System.out.println("Pdf created successfully.");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static PdfPCell getCellLeftNoBorder(String t) {
        Paragraph p=new Paragraph(t);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPCell c=new PdfPCell(p);
        c.setBorder(PdfPCell.NO_BORDER);
        c.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        return c;
    }
    public static PdfPCell getCellLeft(String t) {
        Paragraph p=new Paragraph(t);
        p.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPCell c=new PdfPCell(p);
        c.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        return c;
    }
    public static PdfPCell getCellCentered(String t) {
        Paragraph p=new Paragraph(t);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        PdfPCell c=new PdfPCell(p);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return c;
    }
    public static PdfPCell getCellRight(String t) {
        Paragraph p=new Paragraph(t);
        p.setAlignment(Paragraph.ALIGN_RIGHT);
        PdfPCell c=new PdfPCell(p);
        c.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        return c;
    }

    private static void PlaceChunck(String text, int x, int y, PdfWriter writer) {
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf;
        try {
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, 12);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Image getImageFromAssets(String fileName) throws IOException {
        AssetManager assetManager = _context.getAssets();
        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        InputStream istr = assetManager.open(fileName);

        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = istr.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        istr.close();
        Image img=null;
        android.media.Image image;
        try {
             img = Image.getInstance(byteBuffer.toByteArray());
        }catch (Exception ex){

        }
        return img;
    }

}
