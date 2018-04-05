package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 
 * Predstavlja entitet usluge koji je definiran objektom klase Klijent te vrstom, opisom, datumom i cijenom usluge, kao i parametrima da li je usluga obavljena ili nije.
 * 
 * @author Denis
 *
 */

public class Usluga implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Klijent klijent;
	private VrstaUsluge vrstaUsluge;
	private String opisUsluge;
	private LocalDate datumUsluge;
	private BigDecimal cijenaUsluge;
	private Boolean uslugaObavljena;
	private Boolean uslugaNaplacena;
	private Integer id;
	
	/**
	 * Inicijalizira podatak o klijentu te vrsti, opisu, datumu i cijeni usluge, kao i podatak o obavljenoj, odnosno naplaæenoj usluzi.
	 * 
	 * @param klijent podatak o klijentu
	 * @param vrstaUsluge podatak o vrsti usluge
	 * @param opisUsluge podatak o opisu usluge
	 * @param datumUsluge podatak o datumu usluge
	 * @param cijenaUsluge podatak o cijenu usluge
	 * @param uslugaObavljena podatak da li je usluga obavljena
	 * @param uslugaNaplacena podatak da li je usluga naplaæena
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public Usluga(Klijent klijent, VrstaUsluge vrstaUsluge, String opisUsluge, LocalDate datumUsluge, BigDecimal cijenaUsluge,
			Boolean uslugaObavljena, Boolean uslugaNaplacena) {
		
		this.klijent = klijent;
		this.vrstaUsluge = vrstaUsluge;
		this.opisUsluge = opisUsluge;
		this.datumUsluge = datumUsluge;
		this.cijenaUsluge = cijenaUsluge;
		this.uslugaObavljena = uslugaObavljena;
		this.uslugaNaplacena = uslugaNaplacena;
		
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public VrstaUsluge getVrstaUsluge() {
		return vrstaUsluge;
	}

	public void setVrstaUsluge(VrstaUsluge vrstaUsluge) {
		this.vrstaUsluge = vrstaUsluge;
	}

	public String getOpisUsluge() {
		return opisUsluge;
	}

	public void setOpisUsluge(String opisUsluge) {
		this.opisUsluge = opisUsluge;
	}

	public LocalDate getDatumUsluge() {
		return datumUsluge;
	}

	public void setDatumUsluge(LocalDate datumUsluge) {
		this.datumUsluge = datumUsluge;
	}

	public BigDecimal getCijenaUsluge() {
		return cijenaUsluge;
	}

	public void setCijenaUsluge(BigDecimal cijenaUsluge) {
		this.cijenaUsluge = cijenaUsluge;
	}

	public Boolean isUslugaObavljena() {
		return uslugaObavljena;
	}

	public void setUslugaObavljena(Boolean uslugaObavljena) {
		this.uslugaObavljena = uslugaObavljena;
	}

	public Boolean isUslugaNaplacena() {
		return uslugaNaplacena;
	}

	public void setUslugaNaplacena(Boolean uslugaNaplacena) {
		this.uslugaNaplacena = uslugaNaplacena;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void obavljena() {
		
		this.uslugaObavljena = true;
	}
	
	public void naplacena() {
		
		this.uslugaNaplacena = true;
	}
}
