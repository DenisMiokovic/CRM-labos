package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * 
 * Predstavlja entitet zaposlenika koji naslje�uje klasu Osoba te je definiran sa korisni�kim imenom i �ifrom zaposlenika.
 * 
 * @author Denis
 *
 */

public class Zaposlenik extends Osoba implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String korisnickoIme;
	private String sifraZaposlenika;
	private Integer id;
	
	/**
	 * Inicijalizira podatak o korisni�kom imenu i �ifri zaposlenika te imenu i prezimenu koji se naslje�uju iz klase Osoba.
	 * 
	 * @param ime podatak o imenu (super)
	 * @param prezime podatak o prezimenu (super)
	 * @param korisnickoIme podatak o korisni�kom imenu
	 * @param sifraZaposlenika podatak o �ifri zaposlenika
	 */
	
	public Zaposlenik(String ime, String prezime, String korisnickoIme, String sifraZaposlenika) {
		
		super(ime, prezime);
		this.korisnickoIme = korisnickoIme;
		this.sifraZaposlenika = sifraZaposlenika;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getSifraZaposlenika() {
		return sifraZaposlenika;
	}

	public void setSifraZaposlenika(String sifraZaposlenika) {
		this.sifraZaposlenika = sifraZaposlenika;
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