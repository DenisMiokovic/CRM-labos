package hr.java.vjezbe.entitet;

import java.util.List;

/**
 * 
 * Predstavlja entitet tvrtke koji je definiran sa nazivom, OIB-om, te listama klasa Klijent, Zaposlenik, Komunikacija, Usluga i Alarm
 * 
 * @author Denis
 *
 */

public class Tvrtka {
	
	private String nazivTvrtke;
	private String oibTvrtke;
	private Integer id;
	private List<Klijent> klijenti;
	private List<Zaposlenik> zaposlenici;
	private List<Komunikacija> komunikacije;
	private List<Usluga> usluge;
	private List<Alarm> alarmi;
	
	/**
	 * Inicijalizira podatak o nazivu i OIB-u tvrtke, klijentima, zaposlenicima, komunikaciji, uslugama i alarmima.
	 * 
	 * @param nazivTvrtke podatak o nazivu tvrtke
	 * @param oibTvrtke podatak o OIB-u tvrtke
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public Tvrtka(String nazivTvrtke, String oibTvrtke) {
		
		this.nazivTvrtke = nazivTvrtke;
		this.oibTvrtke = oibTvrtke;
		
	}

	public String getNazivTvrtke() {
		return nazivTvrtke;
	}

	public void setNazivTvrtke(String nazivTvrtke) {
		this.nazivTvrtke = nazivTvrtke;
	}

	public String getOibTvrtke() {
		return oibTvrtke;
	}

	public void setOibTvrtke(String oibTvrtke) {
		this.oibTvrtke = oibTvrtke;
	}

	public List<Klijent> getKlijenti() {
		return klijenti;
	}

	public void setKlijenti(List<Klijent> klijenti) {
		this.klijenti = klijenti;
	}

	public List<Zaposlenik> getZaposlenici() {
		return zaposlenici;
	}

	public void setZaposlenici(List<Zaposlenik> zaposlenici) {
		this.zaposlenici = zaposlenici;
	}

	public List<Komunikacija> getKomunikacije() {
		return komunikacije;
	}

	public void setKomunikacije(List<Komunikacija> komunikacije) {
		this.komunikacije = komunikacije;
	}

	public List<Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(List<Usluga> usluge) {
		this.usluge = usluge;
	}

	public List<Alarm> getAlarmi() {
		return alarmi;
	}

	public void setAlarmi(List<Alarm> alarmi) {
		this.alarmi = alarmi;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
