package sample.Enums;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public enum Niveau implements ObservableList<Niveau> {
	A, 
	B, 
	C, 
	D, 
	U9, 
	U11, 
	U13, 
	U15, 
	U17, 
	U19, 
	U22, 
	O19, 
	O35, 
	O40, 
	O45, 
	O50, 
	O55, 
	O60, 
	O65, 
	O70, 
	Hobby;

	public void niveauHinzufuegen() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addListener(ListChangeListener<? super Niveau> listener) {

	}

	@Override
	public void removeListener(ListChangeListener<? super Niveau> listener) {

	}

	@Override
	public boolean addAll(Niveau... elements) {
		return false;
	}

	@Override
	public boolean setAll(Niveau... elements) {
		return false;
	}

	@Override
	public boolean setAll(Collection<? extends Niveau> col) {
		return false;
	}

	@Override
	public boolean removeAll(Niveau... elements) {
		return false;
	}

	@Override
	public boolean retainAll(Niveau... elements) {
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
	public Iterator<Niveau> iterator() {
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
	public boolean add(Niveau niveau) {
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
	public boolean addAll(Collection<? extends Niveau> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Niveau> c) {
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
	public Niveau get(int index) {
		return null;
	}

	@Override
	public Niveau set(int index, Niveau element) {
		return null;
	}

	@Override
	public void add(int index, Niveau element) {

	}

	@Override
	public Niveau remove(int index) {
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
	public ListIterator<Niveau> listIterator() {
		return null;
	}

	@Override
	public ListIterator<Niveau> listIterator(int index) {
		return null;
	}

	@Override
	public List<Niveau> subList(int fromIndex, int toIndex) {
		return null;
	}

	@Override
	public void addListener(InvalidationListener listener) {

	}

	@Override
	public void removeListener(InvalidationListener listener) {

	}

}