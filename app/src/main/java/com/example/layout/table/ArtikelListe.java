package com.example.layout.table;

import android.os.Parcelable;

import java.util.ArrayList;

public class ArtikelListe  {
    ArrayList<Artikel> _artikelliste=new ArrayList<>();

    public ArtikelListe(){
        _artikelliste=new ArrayList<>();
    }
    public void add(Artikel artikel){
        _artikelliste.add(artikel);
    }
    public ArrayList<Artikel> getArtikel(){
        return this._artikelliste;
    }
    public String[] getList(){
        String[] array = new String[_artikelliste.size()];
        array=_artikelliste.toArray(array);
        return  array;
    }
}
