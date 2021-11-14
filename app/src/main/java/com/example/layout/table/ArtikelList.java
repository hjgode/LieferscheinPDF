package com.example.layout.table;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArtikelList {
    List<Artikel> _artikelliste=new ArrayList<Artikel>();

    public ArtikelList(){
        _artikelliste=new ArrayList<Artikel>();
    }
    public void add(Artikel artikel){
        _artikelliste.add(artikel);
    }
    public List<Artikel> getArtikel(){
        return this._artikelliste;
    }

    public void set_artikelliste(List<Artikel>listArtikel){
        ArrayList<Artikel> arrlistofArtikel = new ArrayList<Artikel>(listArtikel);
    }
    public boolean saveList(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedPreferencesEditor=sharedPreferences.edit();

        StringBuilder csvList = new StringBuilder();
        for(Artikel a : _artikelliste){
            csvList.append(a.get_menge());
            csvList.append("|");
            csvList.append(a.get_artikelnummer());
            csvList.append("|");
            csvList.append(a.get_artikeltext());
            csvList.append("|");
            csvList.append(a.get_preistext());
            csvList.append("\n");
        }

        sharedPreferencesEditor.putString ("myList", csvList.toString());
        return true;
    }
    public int restoreList(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);

        _artikelliste.clear();
        String csvList = sharedPreferences.getString("myList","");
        if(csvList.equals(""))
            return 0;
        String[] lines = csvList.split("\n");
        for (String line:lines
             ) {
            String[] items = csvList.split("|");
            for(int i=0; i < items.length; i++){
                Artikel artikel=new Artikel();
                artikel.set_menge(items[0]);
                artikel.set_artikelnummer(items[1]);
                artikel.set_artikelText(items[2]);
                artikel.set_preisText(items[3]);
                _artikelliste.add(artikel);
            }

        }
        return _artikelliste.size();
    }
}