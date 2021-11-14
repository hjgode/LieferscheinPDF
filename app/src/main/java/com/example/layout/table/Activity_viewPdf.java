package com.example.layout.table;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//import es.voghdev.pdfviewpager.library.PDFViewPager;
//import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
//import es.voghdev.pdfviewpager.library.adapter.PdfErrorHandler;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static android.view.View.VISIBLE;

public class Activity_viewPdf extends AppCompatActivity {

    PDFView pdfView;
    View pdfErrorView;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        btnClose=(Button)findViewById(R.id.btnPdfCancel);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });
        Intent intent=getIntent();
        String pdfFileName=intent.getStringExtra(Constants.BUNDLE_PDF_FILENAME);

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
}