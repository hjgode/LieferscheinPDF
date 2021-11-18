//TableLayoutActivity
package com.example.pdf.lieferschein;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {
    public static String TAG="Lieferschein";
    private Context context = this;
    int currentRowNumber=0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    EditText editName;
    EditText editStrasse;
    EditText editOrt;
    EditText editLieferDatum, editLieferNummer, editLieferStart, editLieferEnde;

    EditText editEmail;
    SignaturePad mSignaturePad=null;
    Bitmap signatureBitmap=null;
    String signatureFilename="Signature_lieferschein.jpg";
    String _pdfFilename="Lieferschein.pdf";

    Button btn_show_hide_kunde, btn_show_hide_lieferdaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);

        setContentView(R.layout.activity_main);

        setTitle("Lieferschein");
        _pdfFilename=getDocumentPdfFile(_pdfFilename);

        editName=(EditText)findViewById(R.id.editTextTextPersonName);
        editStrasse=(EditText)findViewById(R.id.editTextPersonStrasse);
        editOrt=(EditText)findViewById(R.id.editTextPersonOrt);

        editLieferDatum=(EditText)findViewById(R.id.editTextLieferDatum);
        editLieferNummer=(EditText)findViewById(R.id.editTextLieferNummer);
        editLieferStart=(EditText)findViewById(R.id.editTextLieferStart);
        editLieferEnde=(EditText)findViewById(R.id.editTextLieferEnde);

        btn_show_hide_kunde=(Button)findViewById(R.id.button_show_hide_kunde);
        View view_kunde=(View)findViewById(R.id.view_kunde);

        view_kunde.setVisibility(View.VISIBLE);
        btn_show_hide_kunde.setText("Verbergen");

        btn_show_hide_kunde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view_kunde.getVisibility()== View.GONE){
                    view_kunde.setVisibility(View.VISIBLE);
                    btn_show_hide_kunde.setText("Verbergen");
                }else {
                    view_kunde.setVisibility(View.GONE);
                    btn_show_hide_kunde.setText("Zeigen");
                }
            }
        });

        View view_lieferdaten=(View)findViewById(R.id.view_lieferdaten);
        view_lieferdaten.setVisibility(View.GONE);
        btn_show_hide_lieferdaten=(Button)findViewById(R.id.button_show_hide_lieferung);
        btn_show_hide_lieferdaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view_lieferdaten.getVisibility()== View.GONE){
                    view_lieferdaten.setVisibility(View.VISIBLE);
                    btn_show_hide_lieferdaten.setText("Verbergen");
                }else {
                    view_lieferdaten.setVisibility(View.GONE);
                    btn_show_hide_lieferdaten.setText("Zeigen");
                }

            }
        });

        context = getApplicationContext();

        // Get add table row button.
        Button btnShowArtikel = (Button)findViewById(R.id.btnArtikelBearbeiten);
        btnShowArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,ArtikelListeActivity.class);
                Bundle bundle=new Bundle();
                intent.putExtra(Constants.INTENT_ARTIKEL_LISTE_DO_RESTORE,true);
                startActivity(intent);
//                startActivityForResult(intent, Constants.ACTIVITY_ADD_EDIT_ARTIKEL);
            }
        });

        editEmail=(EditText)findViewById(R.id.etEmail);
        mSignaturePad=(SignaturePad)findViewById(R.id.signature_pad);

        Button buttonCreatePDF=(Button)findViewById(R.id.button_create_pdf);
        buttonCreatePDF.setEnabled(false);
        buttonCreatePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    WritePdf writePdf=new WritePdf(context,createBundle());

                    Intent intentShowPdfintent = new Intent(context, Activity_viewPdf.class);
                    intentShowPdfintent.putExtra(Constants.BUNDLE_PDF_FILENAME, _pdfFilename);
                    intentShowPdfintent.putExtra(Constants.BUNDLE_PDF_EMAIL, editEmail.getText().toString());
                    startActivityForResult(intentShowPdfintent, Constants.ACTIVITY_SHOW_PDF);
                }catch (Exception ex){
                    Log.d(TAG, "WritePdf..exception: "+ex.getMessage());
                }
            }
        });

        Button buttonSaveSignature=(Button)findViewById(R.id.saveSignatureButton);

        buttonSaveSignature.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   signatureBitmap=mSignaturePad.getSignatureBitmap();
                   //save the bitmap
                   if (addJpgSignatureToGallery(signatureBitmap, signatureFilename)) {
                       Toast.makeText(context, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                       mSignaturePad.setEnabled(false);
                       buttonCreatePDF.setEnabled(true);
                   } else {
                       Toast.makeText(context, "Unable to store the signature", Toast.LENGTH_SHORT).show();
                   }

               }
           });
        Button buttonClearSignature = (Button) findViewById(R.id.clearSignatureButton);
        buttonClearSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
                mSignaturePad.setEnabled(true);
                buttonCreatePDF.setEnabled(false);
            }
        });

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(MainActivity.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                buttonSaveSignature.setEnabled(true);
                buttonClearSignature.setEnabled(true);
                buttonCreatePDF.setEnabled(true);
            }

            @Override
            public void onClear() {
                buttonClearSignature.setEnabled(false);
                buttonSaveSignature.setEnabled(false);
                buttonCreatePDF.setEnabled(true);
            }
        });
        if(savedInstanceState!=null){
            dataFromBundle(savedInstanceState);
        }
        btn_show_hide_kunde.requestFocus();
    }//onCreate

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putAll(createBundle());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bundle bundle=savedInstanceState;
        dataFromBundle(bundle);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (Constants.ACTIVITY_SHOW_PDF): {
                if (resultCode == Activity.RESULT_OK) {

                }
            }
        }
    }
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER) &&
                (view.getClass().getName().equals("android.widget.EditText"))) {
            // Perform action on key press
            String s=view.getParent().getClass().getName();
            if (s.equals("android.widget.TableRow")) {
                view.clearFocus();
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }
    public File getDocumentStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()) {
            Log.e("PdfLieferscheine", "Directory not created");
        }
        return file;
    }
    public String getDocumentPdfFile(String pdfFilename){
        String result = _pdfFilename;
        try {
            File pdfFile = new File(getDocumentStorageDir("PdfLieferscheine"), pdfFilename);
            _pdfFilename=pdfFile.getAbsolutePath();
            result = _pdfFilename;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
    public boolean addJpgSignatureToGallery(Bitmap signature, String signaturefile) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), signaturefile);
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            signatureFilename=photo.getAbsolutePath();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        MainActivity.this.sendBroadcast(mediaScanIntent);
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    private void dataFromBundle(Bundle bundle){
        Log.d(TAG, "dataFromBundle: ");
        editName.setText(bundle.getString(Constants.BUNDLE_KUNDEN_NAME));
        editStrasse.setText(bundle.getString(Constants.BUNDLE_KUNDEN_STRASSE));
        editOrt.setText(bundle.getString(Constants.BUNDLE_KUNDEN_ORT));

        editLieferDatum.setText(bundle.getString(Constants.BUNDLE_LIEFER_DATUM));
        editLieferNummer.setText(bundle.getString(Constants.BUNDLE_LIEFER_NUMMER));
        editLieferStart.setText(bundle.getString(Constants.BUNDLE_LIEFER_START));
        editLieferEnde.setText(bundle.getString(Constants.BUNDLE_LIEFER_ENDE));

        editEmail.setText(bundle.getString(Constants.BUNDLE_PDF_EMAIL));
        _pdfFilename=bundle.getString(Constants.BUNDLE_PDF_FILE);

        String sf=bundle.getString("signaturefile");
        if(sf!=""){
            Bitmap bitmap= BitmapFactory.decodeFile(signatureFilename);
            mSignaturePad.setSignatureBitmap(bitmap);
        }

    }

    private Bundle createBundle(){
        Log.d(TAG,"createBundle...");
        Bundle bundle=new Bundle();
        bundle.putString(Constants.BUNDLE_KUNDEN_NAME, editName.getText().toString());
        bundle.putString(Constants.BUNDLE_KUNDEN_STRASSE, editStrasse.getText().toString());
        bundle.putString(Constants.BUNDLE_KUNDEN_ORT, editOrt.getText().toString());

        bundle.putString(Constants.BUNDLE_LIEFER_DATUM, editLieferDatum.getText().toString());
        bundle.putString(Constants.BUNDLE_LIEFER_NUMMER, editLieferNummer.getText().toString());
        bundle.putString(Constants.BUNDLE_LIEFER_START, editLieferStart.getText().toString());
        bundle.putString(Constants.BUNDLE_LIEFER_ENDE, editLieferEnde.getText().toString());

        bundle.putString(Constants.BUNDLE_PDF_EMAIL,editEmail.getText().toString());
        //signature must be passed by file name
        bundle.putString(Constants.BUNDLE_SIGNATUREFILE, signatureFilename);
        bundle.putString(Constants.BUNDLE_PDF_FILE,_pdfFilename);

        return bundle;
    }

}