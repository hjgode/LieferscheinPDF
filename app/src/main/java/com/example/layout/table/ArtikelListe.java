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
    public Parcelable[] getArtikel(){
        return this._artikelliste;
    }
}
