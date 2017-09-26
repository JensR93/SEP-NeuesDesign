package sample;

import sample.DAO.*;
import sample.Spielsysteme.*;
import sample.Enums.*;
import java.util.Vector;

public class Verein {
	private int vereinsID;
	private String extVereinsID;
	private String name;
	private String verband;

	public VereinDAO getVereinDAO() {
		return vereinDAO;
	}

	public void setVereinDAO(VereinDAO vereinDAO) {
		this.vereinDAO = vereinDAO;
	}

	VereinDAO vereinDAO = new VereinDAOimpl();

	public int getVereinsID() {
		return vereinsID;
	}

	public void setExtVereinsID(String extVereinsID) {
		this.extVereinsID = extVereinsID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVerband(String verband) {
		this.verband = verband;
	}

	public String getName() {
		return name;
	}

	public String getVerband() {
		return verband;
	}

	public String getExtVereinsID() {
		return extVereinsID;
	}

	public Verein(String extVereinsID, String name, String verband) {
		this.extVereinsID = extVereinsID;
		this.name = name;
		this.verband = verband;
		vereinDAO.create(this);
	}
	public Verein(int vereinsID, String extVereinsID, String name, String verband) {
		this.vereinsID = vereinsID;
		this.extVereinsID = extVereinsID;
		this.name = name;
		this.verband = verband;
	}

	public void setVereinsID(int vereinsID) {
		this.vereinsID = vereinsID;
	}

	@Override
	public String toString() {
		return name;
	}


}