package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * 
 * Predstavlja entitet artikla definiran nazivom i kategorijom.
 * 
 * @author Denis
 *
 */

public class Artikl implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String naziv;
	private KategorijaArtikla kategorija;
	private Integer id;
	
	/**
	 * Inicijalizira podatak o nazivu i kategoriji.
	 * 
	 * @param naziv podatak o nazivu artikla
	 * @param kategorija podatak o kategoriji artikla
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public Artikl(String naziv, KategorijaArtikla kategorija) {
		
		this.naziv = naziv;
		this.kategorija = kategorija;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public KategorijaArtikla getKategorija() {
		return kategorija;
	}
	
	public void setKategorija(KategorijaArtikla kategorija) {
		this.kategorija = kategorija;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
