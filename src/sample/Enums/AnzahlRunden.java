package sample.Enums;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public enum AnzahlRunden implements ObservableList<AnzahlRunden> {
    eins,
    zwei,
    drei,
    vier,
    fuenf;

    public void AnzahlRundenHinzufuegen() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addListener(ListChangeListener<? super AnzahlRunden> listener) {

    }

    @Override
    public void removeListener(ListChangeListener<? super AnzahlRunden> listener) {

    }

    @Override
    public boolean addAll(AnzahlRunden... elements) {
        return false;
    }

    @Override
    public boolean setAll(AnzahlRunden... elements) {
        return false;
    }

    @Override
    public boolean setAll(Collection<? extends AnzahlRunden> col) {
        return false;
    }

    @Override
    public boolean removeAll(AnzahlRunden... elements) {
        return false;
    }

    @Override
    public boolean retainAll(AnzahlRunden... elements) {
        return false;
    }

    @Override
    public void remove(int from, int to) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<AnzahlRunden> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(AnzahlRunden AnzahlRunden) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends AnzahlRunden> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends AnzahlRunden> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public AnzahlRunden get(int index) {
        return null;
    }

    @Override
    public AnzahlRunden set(int index, AnzahlRunden element) {
        return null;
    }

    @Override
    public void add(int index, AnzahlRunden element) {

    }

    @Override
    public AnzahlRunden remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<AnzahlRunden> listIterator() {
        return null;
    }

    @Override
    public ListIterator<AnzahlRunden> listIterator(int index) {
        return null;
    }

    @Override
    public List<AnzahlRunden> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }

}