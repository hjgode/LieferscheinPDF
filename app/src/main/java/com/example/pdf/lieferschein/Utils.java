package com.example.pdf.lieferschein;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getDateShort() {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        //File myFile = new File(pdfFolder + timeStamp + ".pdf");
        return timeStamp;
    }

    public static void viewPdf(Context context, File pdfFile){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
    public static void emailNote(Context context, String subjectText, File pdfFile)
    {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT,subjectText);
        email.putExtra(Intent.EXTRA_TEXT, "Der Lieferschein");
        Uri uri = Uri.parse(pdfFile.getAbsolutePath());
        email.putExtra(Intent.EXTRA_STREAM, uri);
        email.setType("message/rfc822");
        context.startActivity(email);
    }}
