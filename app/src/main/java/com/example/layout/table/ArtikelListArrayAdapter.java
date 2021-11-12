package com.example.layout.table;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArtikelListArrayAdapter extends ArrayAdapter<Artikel> {
    private String[] menge, artikelnummer, artikeltext, preis;
    private List<Artikel> _artikelliste;
    private Activity context;

    public ArtikelListArrayAdapter(Activity context, List<Artikel> liste){
        super(context,R.layout.rowitem, liste);
        this.context=context;
        }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        Artikel artikel=getItem(position);
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.rowitem, null, true);

        TextView tvMenge = (TextView) row.findViewById(R.id.tvMenge);
        TextView tvArtikelnummer = (TextView) row.findViewById(R.id.tvArtikelnummer);
        TextView tvArtikeltext = (TextView) row.findViewById(R.id.tvArtikeltext);
        TextView tvArtikelpreis = (TextView) row.findViewById(R.id.tvPreis);
        ImageButton btnDelete = (ImageButton) row.findViewById(R.id.btnDelete);
//        btnDelete.setFocusable(false);

        tvMenge.setText(artikel.get_menge());
        tvArtikelnummer.setText(artikel.get_artikelnummer());
        tvArtikeltext.setText(artikel.get_artikeltext());
        tvArtikelpreis.setText(artikel.get_preistext());

//        imageFlag.setImageResource(imageid[position]);
        return  row;
    }
}
