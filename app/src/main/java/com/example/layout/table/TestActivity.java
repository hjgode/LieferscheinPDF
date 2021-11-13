package com.example.layout.table;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
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

import static com.example.layout.table.Constants.INTENT_ART_EDIT_LISTVIEW_ID;

public class TestActivity extends AppCompatActivity {
    ListView listView;
    Activity context=this;
    List<Artikel> artikelList;
    ArtikelListArrayAdapter artikelListArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btnAdd=(Button)findViewById(R.id.btnArtikelListeAddNew);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewArtikel(-1);
            }
        });

        Button btnSave=(Button)findViewById(R.id.btnArtikelListeSpeichern);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArtikelList myArtikelist=new ArtikelList();
                myArtikelist.set_artikelliste(artikelList);
                myArtikelist.saveList(context);
            }
        });

        Button btnCancel=(Button)findViewById(R.id.btnArtikelListeVerwerfen);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        listView=(ListView)findViewById(android.R.id.list);

        // Setting header
        TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("Artikel ");
        listView.addHeaderView(textView);

        artikelList=new ArrayList<Artikel>();

        Artikel artikel=new Artikel("1", "123456","Text","9,99");
        artikelList.add(artikel);
        artikel=new Artikel("2", "123450","Text Artikel","7,99");
        artikelList.add(artikel);

        //listdata
        artikelListArrayAdapter=new ArtikelListArrayAdapter(this, artikelList);
        listView.setAdapter(artikelListArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Artikel selectedItem = (Artikel) parent.getItemAtPosition(position);
                //textView.setText("The best football player is : " + selectedItem);
                Log.d("TEST","Clicked on "+selectedItem.get_artikelnummer());
//                Toast.makeText(getApplicationContext(),"You Selected "+artikelList.get(position-1).get_artikelnummer(), Toast.LENGTH_SHORT).show();        }
            };
        });

        this.registerForContextMenu(listView);

    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, 0, 0, "Delete");
        menu.add(0, 1, 1, "Edit");
        menu.add(0, 2, 2, "Add");

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info
                = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        int itemPosition = info.position;
        Artikel selectedItem = (Artikel) listView.getItemAtPosition(itemPosition);
        Bundle bundle;
        Artikel artikel;
        Intent intent;
        //Uri uri;
        int selected = 0;
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(context,
                        "Delete? "+selectedItem.toString(), Toast.LENGTH_LONG).show();
                AlertDialog.Builder adb=new AlertDialog.Builder(context);
                adb.setTitle("Delete?");
                adb.setMessage("Möchten Sie dies löschen: " + selectedItem.toString());
                final int positionToRemove = itemPosition-1;//we have added one line in front, so subtract 1
                adb.setNegativeButton("Nein", null);
                adb.setPositiveButton("Ja", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        artikelList.remove(positionToRemove);
                        artikelListArrayAdapter.notifyDataSetChanged();
                    }});
                adb.show();

                break;
            case 1: //EDIT
                artikel=(Artikel)selectedItem;
                Toast.makeText(context,
                        "Edit? "+selectedItem.toString(), Toast.LENGTH_LONG).show();
// Start the SecondActivity
                addNewArtikel(itemPosition);
/*                bundle=new Bundle();
                bundle.putString(Constants.INTENT_ART_EDIT_ACTIONS, Constants.INTENT_ART_EDIT_ACTION);
                bundle.putInt(INTENT_ART_EDIT_LISTVIEW_ID,itemPosition);
                bundle.putString(Constants.INTENT_ART_MENGE,artikel.get_menge());
                bundle.putString(Constants.INTENT_ART_NUMMER,artikel.get_artikelnummer());
                bundle.putString(Constants.INTENT_ART_TEXT,artikel.get_artikeltext());
                bundle.putString(Constants.INTENT_ART_PREIS,artikel.get_preistext());

                intent = new Intent(this, AddEditArtikelActivity.class);
                intent.putExtra(Constants.INTENT_ART_BUNDLE,bundle);

                startActivityForResult(intent, Constants.ACTIVITY_ADD_EDIT_ARTIKEL);
*/
                break;
            case 2: //ADD
                Toast.makeText(context,
                        "Add? "+selectedItem.toString(), Toast.LENGTH_LONG).show();
                artikel=new Artikel();
                bundle=new Bundle();
                bundle.putString(Constants.INTENT_ART_EDIT_ACTIONS, Constants.INTENT_ART_ADD_ACTION);
                bundle.putInt(INTENT_ART_EDIT_LISTVIEW_ID,itemPosition);
                bundle.putString(Constants.INTENT_ART_MENGE,artikel.get_menge());
                bundle.putString(Constants.INTENT_ART_NUMMER,artikel.get_artikelnummer());
                bundle.putString(Constants.INTENT_ART_TEXT,artikel.get_artikeltext());
                bundle.putString(Constants.INTENT_ART_PREIS,artikel.get_preistext());

                intent = new Intent(this, AddEditArtikelActivity.class);
                intent.putExtra(Constants.INTENT_ART_BUNDLE,bundle);

                startActivityForResult(intent, Constants.ACTIVITY_ADD_EDIT_ARTIKEL);
                break;
        }
        return super.onContextItemSelected(item);
    }
    public void addNewArtikel(int pos){
        Artikel artikel=new Artikel();
        Bundle bundle=new Bundle();
        Intent intent;
        bundle.putString(Constants.INTENT_ART_EDIT_ACTIONS, Constants.INTENT_ART_ADD_ACTION);
        bundle.putInt(INTENT_ART_EDIT_LISTVIEW_ID,pos);
        bundle.putString(Constants.INTENT_ART_MENGE,artikel.get_menge());
        bundle.putString(Constants.INTENT_ART_NUMMER,artikel.get_artikelnummer());
        bundle.putString(Constants.INTENT_ART_TEXT,artikel.get_artikeltext());
        bundle.putString(Constants.INTENT_ART_PREIS,artikel.get_preistext());

        intent = new Intent(this, AddEditArtikelActivity.class);
        intent.putExtra(Constants.INTENT_ART_BUNDLE,bundle);

        startActivityForResult(intent, Constants.ACTIVITY_ADD_EDIT_ARTIKEL);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (Constants.ACTIVITY_ADD_EDIT_ARTIKEL) : {
                if (resultCode == Activity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    String returnValue = data.getStringExtra(Constants.INTENT_ART_MENGE);
                    int itempos=data.getIntExtra(INTENT_ART_EDIT_LISTVIEW_ID,-1);
                    Artikel artikel=new Artikel(
                            data.getStringExtra(Constants.INTENT_ART_MENGE),
                            data.getStringExtra(Constants.INTENT_ART_NUMMER),
                            data.getStringExtra(Constants.INTENT_ART_TEXT),
                            data.getStringExtra(Constants.INTENT_ART_PREIS)
                    );
                    if(data.getStringExtra(Constants.INTENT_ART_EDIT_ACTIONS)==Constants.INTENT_ART_EDIT_ACTION)
                    {
                        //this an edit
                        if(itempos!=-1){
                            Log.d("onActivityResult", "itempos="+itempos);
                            artikelList.set(itempos-1, artikel); // -1 as we have added a header line
                            artikelListArrayAdapter.notifyDataSetChanged();
                        }
                    }else{
                        //this is an add
                        artikelList.add(artikel);
                        artikelListArrayAdapter.notifyDataSetChanged();
                    }

                }
                break;
            }
        }
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