package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 
 * Predstavlja entitet klijenta koji je definiran sa OIB-om, brojem telefona, e-mail adresom i datumom roðenja, te isti nasljeðuje klasu Osoba.
 * 
 * @author Denis
 *
 */

public class Klijent extends Osoba implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String oib;
	private String brojTelefona;
	private String eMailAdresa;
	private LocalDate datumRodjenja;
	private Integer id;
	
	/**
	 * Inicijalizira podatak o imenu, prezimenu, OIB-u, broju telefona, e-mail adresi i datumu roðenja.
	 * 
	 * @param ime podatak o imenu
	 * @param prezime podatak o prezimenu
	 * @param oib podatak o OIB-u
	 * @param brojTelefona podatak o broju telefona
	 * @param eMailAdresa podatak o e-mail adresi
	 * @param datumRodjenja podatak o datumu roðenja
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public Klijent(String ime, String prezime, String oib, String brojTelefona, String eMailAdresa, LocalDate datumRodjenja) {
		
		super(ime, prezime);
		this.oib = oib;
		this.brojTelefona = brojTelefona;
		this.eMailAdresa = eMailAdresa;
		this.datumRodjenja = datumRodjenja;	
	}

	public String getOib() {
		return oib;
	}

	public void setOib(String oib) {
		this.oib = oib;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public String getEMailAdresa() {
		return eMailAdresa;
	}

	public void setEMailAdresa(String eMailAdresa) {
		this.eMailAdresa = eMailAdresa;
	}

	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toString() {
		return getPrezime() + " " + getIme();
	}
}
