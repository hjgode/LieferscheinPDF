package com.example.pdf.lieferschein;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.util.ULocale;
import android.net.Uri;
import android.util.DisplayMetrics;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static float getNumberDecimal(String sInDecimal){
        Locale loc=Locale.getDefault();
        loc=Locale.GERMAN;
        NumberFormat f = NumberFormat.getInstance(loc);
        if (f instanceof DecimalFormat) {
            ((DecimalFormat) f).setDecimalSeparatorAlwaysShown(true);
        }
        Float total = Float.valueOf(0);
        try
        {
            total = Float.valueOf(sInDecimal);
        }
        catch(NumberFormatException ex)
        {
            DecimalFormat df = (DecimalFormat)f;//new DecimalFormat();
            Number n = null;
            try
            {
                n = df.parse(sInDecimal);
            }
            catch(ParseException ex2){ }
            if(n != null)
                total = n.floatValue();
        }
        return total;
    }
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
