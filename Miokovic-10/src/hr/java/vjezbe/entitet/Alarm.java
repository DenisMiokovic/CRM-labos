package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * Predstavlja entitet alarma koji je definiran objektom klase Klijent te opisom, vremenom i statusom alarma.
 * 
 * @author Denis
 * 
 */

public class Alarm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Klijent klijent;
	private String opisAlarma;
	private LocalDateTime vrijemeAlarma;
	private Boolean statusAlarma;
	private Integer id;
	
	/**
	 * Inicijalizira podatak o klijentu, opisu, vremenu i statusu alarma.
	 * 
	 * @param klijent podatak o klijentu
	 * @param opisAlarma podatak o opisu alarma
	 * @param vrijemeAlarma podatak o vremenu alarma
	 * @param statusAlarma podatak o statusu alarma
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public Alarm(Klijent klijent, String opisAlarma, LocalDateTime vrijemeAlarma, Boolean statusAlarma) {
		
		this.klijent = klijent;
		this.opisAlarma = opisAlarma;
		this.vrijemeAlarma = vrijemeAlarma;
		this.statusAlarma = statusAlarma;
	}

	public Klijent getKlijent() {
		return klijent;
	}

	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}

	public String getOpisAlarma() {
		return opisAlarma;
	}

	public void setOpisAlarma(String opisAlarma) {
		this.opisAlarma = opisAlarma;
	}

	public LocalDateTime getVrijemeAlarma() {
		return vrijemeAlarma;
	}

	public void setVrijemeAlarma(LocalDateTime vrijemeAlarma) {
		this.vrijemeAlarma = vrijemeAlarma;
	}

	public Boolean isStatusAlarma() {
		return statusAlarma;
	}

	public void setStatusAlarma(Boolean statusAlarma) {
		this.statusAlarma = statusAlarma;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
