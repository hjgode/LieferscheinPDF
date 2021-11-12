package com.example.layout.table;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArtikelList {
    List<Artikel> _artikelliste=new List<Artikel>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Artikel> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(Artikel artikel) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends Artikel> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends Artikel> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Artikel get(int i) {
            return null;
        }

        @Override
        public Artikel set(int i, Artikel artikel) {
            return null;
        }

        @Override
        public void add(int i, Artikel artikel) {

        }

        @Override
        public Artikel remove(int i) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<Artikel> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Artikel> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<Artikel> subList(int i, int i1) {
            return null;
        }
    };

    public ArtikelList(){
        _artikelliste=new ArrayList<Artikel>();
    }
    public void add(Artikel artikel){
        _artikelliste.add(artikel);
    }
    public List<Artikel> getArtikel(){
        return this._artikelliste;
    }

}
