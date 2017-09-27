package sample.Enums;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import sample.DAO.auswahlklasse;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public enum Disziplin implements ObservableList<Disziplin> {




	Herreneinzel, 
	Herrendoppel, 
	Dameneinzel, 
	Damendoppel, 
	Mixed;





	@Override
	public void addListener(ListChangeListener<? super Disziplin> listener) {

	}

	@Override
	public void removeListener(ListChangeListener<? super Disziplin> listener) {

	}

	@Override
	public boolean addAll(Disziplin... elements) {
		return false;
	}

	@Override
	public boolean setAll(Disziplin... elements) {
		return false;
	}

	@Override
	public boolean setAll(Collection<? extends Disziplin> col) {
		return false;
	}

	@Override
	public boolean removeAll(Disziplin... elements) {
		return false;
	}

	@Override
	public boolean retainAll(Disziplin... elements) {
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
	public Iterator<Disziplin> iterator() {
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
	public boolean add(Disziplin Disziplin) {
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
	public boolean addAll(Collection<? extends Disziplin> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Disziplin> c) {
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
	public Disziplin get(int index) {
		return null;
	}

	@Override
	public Disziplin set(int index, Disziplin element) {
		return null;
	}

	@Override
	public void add(int index, Disziplin element) {

	}

	@Override
	public Disziplin remove(int index) {
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
	public ListIterator<Disziplin> listIterator() {
		return null;
	}

	@Override
	public ListIterator<Disziplin> listIterator(int index) {
		return null;
	}

	@Override
	public List<Disziplin> subList(int fromIndex, int toIndex) {
		return null;
	}

	@Override
	public void addListener(InvalidationListener listener) {

	}

	@Override
	public void removeListener(InvalidationListener listener) {

	}

}