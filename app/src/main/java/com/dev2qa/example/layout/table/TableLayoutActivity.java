//TableLayoutActivity
package com.dev2qa.example.layout.table;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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

import java.util.ArrayList;
import java.util.List;

public class TableLayoutActivity extends AppCompatActivity implements View.OnKeyListener {

    private Context context = null;
    int currentRowNumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}