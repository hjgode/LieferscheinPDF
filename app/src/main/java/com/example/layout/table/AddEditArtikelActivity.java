package com.example.layout.table;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEditArtikelActivity extends AppCompatActivity {
    EditText etMenge, etArtNr, etArtText, etArtPreis;
    Button btnSave, btnCancel;
    int itemPostion=0;
    String sAction="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_artikel);

        etMenge=(EditText)findViewById(R.id.edEditMenge);
        etArtNr=(EditText)findViewById(R.id.edEditArtNr);
        etArtText=(EditText)findViewById(R.id.edEditArtText);
        etArtPreis=(EditText)findViewById(R.id.edEditArtPreis);

        Intent intent=getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.INTENT_ART_BUNDLE);
        if(bundle!=null){
            sAction = bundle.getString(Constants.INTENT_ART_EDIT_ACTIONS);
            itemPostion=bundle.getInt(Constants.INTENT_ART_EDIT_LISTVIEW_ID);
            etMenge.setText(bundle.getString(Constants.INTENT_ART_MENGE));
            etArtNr.setText(bundle.getString(Constants.INTENT_ART_NUMMER));
            etArtText.setText(bundle.getString(Constants.INTENT_ART_TEXT));
            etArtPreis.setText(bundle.getString(Constants.INTENT_ART_PREIS));
        }
        btnSave=(Button)findViewById(R.id.btnEditSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
// TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra(Constants.INTENT_ART_EDIT_ACTIONS,sAction);
                resultIntent.putExtra(Constants.INTENT_ART_EDIT_LISTVIEW_ID,itemPostion);
                resultIntent.putExtra(Constants.INTENT_ART_MENGE, etMenge.getText().toString());
                resultIntent.putExtra(Constants.INTENT_ART_NUMMER, etArtNr.getText().toString());
                resultIntent.putExtra(Constants.INTENT_ART_TEXT, etArtText.getText().toString());
                resultIntent.putExtra(Constants.INTENT_ART_PREIS, etArtPreis.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        btnCancel=(Button)findViewById(R.id.btnEditCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
// TODO Add extras or a data URI to this intent as appropriate.
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });

    }
}