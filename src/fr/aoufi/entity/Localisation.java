package fr.aoufi.entity;

import java.io.Serializable;

/**
 * bean metier
 * 
 *
 */
public class Localisation implements Serializable {
	
	private static final long	serialVersionUID	= 1L;
	private int idLocalisation;
	private String lieu;
	private String emp;

	public Localisation() { }

	public Localisation(String lieu, String emp) {
		this.lieu = lieu;
		this.emp  = emp;	// emplacement
	}


	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public String getEmp() {
		return emp;
	}
	public void setEmp(String emp) {
		this.emp = emp;
	}
	public int getIdLocalisation() {
		return idLocalisation;
	}
	public void setIdLocalisation(int idLocalisation) {
		this.idLocalisation = idLocalisation;
	}
	@Override
	public String toString() {
		return "Localisation [" + idLocalisation + ", lieu="
				+ lieu + ", emp=" + emp + "]";
	}
	
	

}
