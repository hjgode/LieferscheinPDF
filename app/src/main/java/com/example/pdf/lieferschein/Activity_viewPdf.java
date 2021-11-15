package com.example.pdf.lieferschein;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import es.voghdev.pdfviewpager.library.PDFViewPager;
//import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
//import es.voghdev.pdfviewpager.library.adapter.PdfErrorHandler;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Activity_viewPdf extends AppCompatActivity {

    PDFView pdfView;
    View pdfErrorView;
    Button btnClose;
    Button btnSendMail;
    Context _context;
    String _pdfFile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        _context=getApplicationContext();

        Intent intent=getIntent();
        String pdfFileName=intent.getStringExtra(Constants.BUNDLE_PDF_FILENAME);
        String eMailTo=intent.getStringExtra(Constants.BUNDLE_PDF_EMAIL);
        _pdfFile=pdfFileName;

        btnClose=(Button)findViewById(R.id.btnPdfCancel);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });
        btnSendMail=(Button)findViewById(R.id.btnPdfSendEmail);
        List<String> attachements=new ArrayList<String>();
        attachements.add(_pdfFile);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: send email intent
                if( !email(_context, eMailTo, "", "Lieferschein",
                        "Lieferschein", attachements)){
                    Toast.makeText(_context, "eMail Send error",Toast.LENGTH_LONG);
                };
                finish();
            }
        });

        pdfView = findViewById(R.id.pdfView);
        pdfErrorView = findViewById(R.id.pdfErrorView);
        File pdfFile= null;
        try {
            pdfFile = new File(pdfFileName);
            pdfView.fromFile(pdfFile).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    public static boolean email(Context context, String emailTo, String emailCC,
                             String subject, String emailText, List<String> filePaths)
    {
        boolean result=false;
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{emailTo});
        emailIntent.putExtra(android.content.Intent.EXTRA_CC,
                new String[]{emailCC});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
        for (String file : filePaths)
        {
            File fileIn = new File(file);
            //Uri u = Uri.fromFile(fileIn);
            Uri u = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider",fileIn);
            uris.add(u);
        }
        try {
            emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            result=true;
        }catch (Exception ex){
            Log.d("eMail", "exception: "+ex.getMessage());
        }
        return result;
    }
}