package com.example.layout.table;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
//import es.voghdev.pdfviewpager.library.adapter.PdfErrorHandler;

import static android.view.View.VISIBLE;

public class Activity_viewPdf extends AppCompatActivity {

    PDFViewPager pdfViewPager;
    PDFPagerAdapter adapter;
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
        String pdfFile=intent.getStringExtra(Constants.BUNDLE_PDF_FILENAME);

        pdfViewPager = findViewById(R.id.pdfViewPager);
        pdfErrorView = findViewById(R.id.pdfErrorView);

        adapter = new PDFPagerAdapter.Builder(this)
/*                .setErrorHandler(new PdfErrorHandler() {
                    @Override
                    public void onPdfError(Throwable t) {
                        pdfErrorView.setVisibility(VISIBLE);
                    }
                })
*/                .setPdfPath(pdfFile)
                .create();

        pdfViewPager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ((PDFPagerAdapter)pdfViewPager.getAdapter()).close();
    }
}