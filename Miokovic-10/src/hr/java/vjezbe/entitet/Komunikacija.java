package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * Predstavlja entitet komunikacije definiran sa klijentom, zaposlenikom te vrstom, sadržajem i vremenom komunikacije
 * 
 * @author Denis
 *
 */

public class Komunikacija implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Klijent klijent;
	private Zaposlenik zaposlenik;
	private VrstaKomunikacije vrstaKomunikacije;
	private String sadrzajKomunikacije;
	private LocalDateTime vrijemeKomunikacije;
	private Integer id;
	
	/**
	 * Inicijalizira podatak o klijentu, zaposleniku, te vrsti, sadržaju i vremenu komunikacije.
	 * 
	 * @param klijent podatak o klijentu
	 * @param zaposlenik podatak o zaposleniku
	 * @param vrstaKomunikacije podatak o vrsti komunikacije
	 * @param sadrzajKomunikacije podatak o sadržaju komunikacije
	 * @param vrijemeKomunikacije podatak o vremenu komunikacije
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public Komunikacija(Klijent klijent, Zaposlenik zaposlenik, VrstaKomunikacije vrstaKomunikacije, String sadrzajKomunikacije,
			LocalDateTime vrijemeKomunikacije) {
		
		this.klijent = klijent;
		this.zaposlenik = zaposlenik;
		this.vrstaKomunikacije = vrstaKomunikacije;
		this.sadrzajKomunikacije = sadrzajKomunikacije;
		this.vrijemeKomunikacije = vrijemeKomunikacije;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public Zaposlenik getZaposlenik() {
		return zaposlenik;
	}

	public void setZaposlenik(Zaposlenik zaposlenik) {
		this.zaposlenik = zaposlenik;
	}

	public VrstaKomunikacije getVrstaKomunikacije() {
		return vrstaKomunikacije;
	}

	public void setVrstaKomunikacije(VrstaKomunikacije vrstaKomunikacije) {
		this.vrstaKomunikacije = vrstaKomunikacije;
	}

	public String getSadrzajKomunikacije() {
		return sadrzajKomunikacije;
	}

	public void setSadrzajKomunikacije(String sadrzajKomunikacije) {
		this.sadrzajKomunikacije = sadrzajKomunikacije;
	}

	public LocalDateTime getVrijemeKomunikacije() {
		return vrijemeKomunikacije;
	}

	public void setVrijemeKomunikacije(LocalDateTime vrijemeKomunikacije) {
		this.vrijemeKomunikacije = vrijemeKomunikacije;
	}

	public String toString() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
		
		return zaposlenik.getPrezime() + " " + zaposlenik.getIme() + " " + "(" + vrijemeKomunikacije.format(formatter) + ")";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
