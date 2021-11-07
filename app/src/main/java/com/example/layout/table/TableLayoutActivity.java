//TableLayoutActivity
package com.example.layout.table;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TableLayoutActivity extends AppCompatActivity implements View.OnKeyListener {

    private Context context = null;
    int currentRowNumber=0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);

        setContentView(R.layout.activity_table_layout);

        setTitle("Artikel Liste");

        // Get TableLayout object in layout xml.
        final TableLayout tableLayout = (TableLayout)findViewById(R.id.table_layout_table);

        context = getApplicationContext();

        int rowCount = tableLayout.getChildCount();
        currentRowNumber=rowCount+1;
        // Get add table row button.
        Button addRowButton = (Button)findViewById(R.id.table_layout_add_row_button);
        addRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int column=0;
                // Create a new table row.
                TableRow tableRow = new TableRow(context);
                currentRowNumber=tableLayout.getChildCount();

                // Set new table row layout parameters.
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(layoutParams);

                // Add a TextView in the first column.
                //Menge
                EditText textView = (EditText) getLayoutInflater().inflate(R.layout.small_edittext, null);//new EditText(context);
                textView.setInputType(InputType.TYPE_CLASS_NUMBER);
                textView.setText("1"+currentRowNumber);
                textView.setPadding(5,5,5,5);
                textView.setMaxLines(1);
                textView.setOnKeyListener(TableLayoutActivity.this::onKey);
                tableRow.addView(textView, column++);

                // Add a TextView in the second column
                //artikelText
                EditText textView2 = (EditText) getLayoutInflater().inflate(R.layout.wide_edittext, null);//new EditText(context);
                textView2.setText("Artikeltext "+ currentRowNumber);
//                textView2.setPadding(5,5,5,5);
                textView2.setMaxLines(2);
//                textView2.setMinLines(2);
                textView2.setOnKeyListener(TableLayoutActivity.this::onKey);
                tableRow.addView(textView2, column++);
/*
                // Add a TextView in the third column
                EditText textView3 = new EditText(context);
                textView3.setText("99.99");
                textView3.setPadding(5,5,5,5);
                textView3.setMaxLines(1);
                textView3.setOnKeyListener(TableLayoutActivity.this::onKey);
                tableRow.addView(textView3, 2);
*/
                // Button to delete row
//                Button buttonDeleteRow=new Button(context);
                /*
                Button buttonDeleteRow=(Button)getLayoutInflater().inflate(R.layout.small_button, null);
                buttonDeleteRow.setText("-");
                buttonDeleteRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tableLayout.removeView(tableRow);
                    }
                });
                tableRow.addView(buttonDeleteRow, column++);
*/
                int resourceId = context.getResources().getIdentifier("delete_icon", "drawable",
                        context.getPackageName());

                ImageView imageView=(ImageView) getLayoutInflater().inflate(R.layout.small_image, null);// new ImageView(context);
                imageView.setImageResource(resourceId);
                imageView.setMaxHeight(30);
                imageView.setMaxWidth(30);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tableLayout.removeView(tableRow);
                    }
                });
                tableRow.addView(imageView, column++);

                tableLayout.addView(tableRow);
            }
        });

        SignaturePad mSignaturePad=(SignaturePad)findViewById(R.id.signature_pad);

        Button buttonSaveSignature=(Button)findViewById(R.id.saveSignatureButton);

        buttonSaveSignature.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Bitmap signatureBitmap=mSignaturePad.getSignatureBitmap();
                   //save the bitmap
                   if (addJpgSignatureToGallery(signatureBitmap)) {
                       Toast.makeText(context, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                       mSignaturePad.setEnabled(false);
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
            }
        });

        Button buttonCreatePDF=(Button)findViewById(R.id.button_create_pdf);

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

    }//onCreate

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
                    Toast.makeText(TableLayoutActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
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
    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), "Signature_lieferschein.jpg");
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
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
        TableLayoutActivity.this.sendBroadcast(mediaScanIntent);
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
}