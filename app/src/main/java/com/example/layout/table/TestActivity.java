package com.example.layout.table;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ListView listView=(ListView)findViewById(android.R.id.list);
        // Setting header
        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("Artikel ");
        listView.addHeaderView(textView);

        List<Artikel> artikelList=new ArrayList<Artikel>();

        Artikel artikel=new Artikel("1", "123456","Text","9,99");
        artikelList.add(artikel);
        artikel=new Artikel("2", "123450","Text Artikel","7,99");
        artikelList.add(artikel);

        //listdata
        ArtikelListArrayAdapter artikelListArrayAdapter=new ArtikelListArrayAdapter(this, artikelList);
        listView.setAdapter(artikelListArrayAdapter);
/*        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("TEST","Clicked on "+position);
//                Toast.makeText(getApplicationContext(),"You Selected "+artikelList.get(position-1).get_artikelnummer(), Toast.LENGTH_SHORT).show();        }
            };
        });

 */

    }
    public void myClickHandler(View v)
    {
        //reset all the listView items background colours
        //before we set the clicked one..
        ListView lvItems = (ListView)findViewById(android.R.id.list); // getListView();

/*
        for (int i=0; i < lvItems.getChildCount(); i++)
        {
            lvItems.getChildAt(i).setBackgroundColor(Color.BLUE);
        }
*/


        //get the row the clicked button is in
        ViewParent vParent1=v.getParent();
        int c = Color.CYAN;
        if(vParent1 instanceof TableRow) {
            vParent1.getParent();//get to TableLayout
            if (vParent1 instanceof TableLayout) {
                TableLayout tableLayout = (TableLayout) vParent1;
                //changes all rows!?
                tableLayout.setBackgroundColor(c);
                tableLayout.refreshDrawableState();
            }
        }
/*
        TextView child = (TextView)vwParentRow.getChildAt(0);
        Button btnChild = (Button)vwParentRow.getChildAt(1);
        btnChild.setText(child.getText());
        btnChild.setText("I've been clicked!");
*/

    }
}