package com.example.layout.table;

import android.os.Parcel;
import android.os.Parcelable;

public class Artikel implements Parcelable {
    private String _pos="1";
    private String _menge = "1";
    private String _artikelnummer = "";
    private String _artikelText = "";

    public String get_menge() {
        return _menge;
    }

    public void set_menge(String i) {
        _menge = i;
    }

    public void set_artikelnummer(String _artikelnummer) {
        this._artikelnummer = _artikelnummer;
    }

    public String get_artikelnummer() {
        return this._artikelnummer;
    }

    public void set_artikelText(String artikelText) {
        this._artikelText = artikelText;
    }

    public String get_artikeltext() {
        return this._artikelText;
    }

    public Artikel(String menge, String nummer, String text){
        _artikelText=text;
        _artikelnummer=nummer;
        _menge=menge;
    }
    public Artikel(){

    }
    // Parcelling part
    public Artikel(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this._pos = data[0];
        this._menge = data[1];
        this._artikelnummer = data[2];
        this._artikelText=data[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeStringArray(new String[] {this._pos,
                this._menge,
                this._artikelnummer,
                this._artikelText});

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Artikel createFromParcel(Parcel in) {
            return new Artikel(in);
        }

        public Artikel[] newArray(int size) {
            return new Artikel[size];
        }
    };

}
