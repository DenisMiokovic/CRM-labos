package hr.java.vjezbe.entitet;

/**
 * 
 * Predstavlja entitet osoba koji je definiran imenom i prezimenom.
 * 
 * @author Denis
 *
 */

public abstract class Osoba {
	
	private String ime;
	private String prezime;
	private Integer id;
	
	/**
	 * Inicijalizira podatak o imenu i prezimenu.
	 * 
	 * @param ime podatak o imenu
	 * @param prezime podatak o prezimenu
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public Osoba(String ime, String prezime) {
		
		this.ime = ime;
		this.prezime = prezime;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
