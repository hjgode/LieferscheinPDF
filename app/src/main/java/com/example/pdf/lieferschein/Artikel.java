package com.example.pdf.lieferschein;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Artikel implements Parcelable {
    private String _menge = "1";
    private String _artikelnummer = "";
    private String _artikelText = "";
    private String _preis="0,00";

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

    public void set_preisText(String preis) {
        this._preis = preis;
    }

    public String get_preistext() {
        return this._preis;
    }

    @NonNull
    @Override
    public String toString() {
        return _menge+":"+_artikelnummer+"/"+_artikelText+"/"+_preis;
    }

    public Artikel(String menge, String nummer, String text, String preis){
        _artikelText=text;
        _artikelnummer=nummer;
        _menge=menge;
        _preis=preis;
    }
    public Artikel(){

    }

    // Parcelling part
    public Artikel(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this._menge = data[0];
        this._artikelnummer = data[1];
        this._artikelText=data[2];
        this._preis = data[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeStringArray(new String[] {
                this._menge,
                this._artikelnummer,
                this._artikelText,
                this._preis});

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
