package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 
 * Predstavlja entitet prodaje artikla koji nasljeðuje klasu Usluga i sve njene parametre te implementira suèelje Robna, dok dodaje svoj parametar artikl. 
 * 
 * @author Denis
 *
 */

public class ProdajaArtikla extends Usluga implements Robna {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8405071683099990235L;
	private Artikl artikl;
	private Integer id;
	
	public ProdajaArtikla(Klijent klijent, VrstaUsluge vrstaUsluge, String opisUsluge, LocalDate datumUsluge, BigDecimal cijenaUsluge, 
			Boolean uslugaObavljena, Boolean uslugaNaplacena, Artikl artikl) {
		super(klijent, vrstaUsluge, opisUsluge, datumUsluge, cijenaUsluge, uslugaObavljena, uslugaNaplacena);
		this.artikl = artikl;
	}
	
	/**
	 * Inicijalizira podatak o artiklu i metodi prodaja.
	 * 
	 * @param artikl podatak o artiklu
	 * @param id podatak o ID-u u bazi podataka
	 * @return vraæa konaènu cijenu usluge
	 */
	
	public Artikl getArtikl() {
		return artikl;
	}
	
	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}
	
	public BigDecimal prodaja(int kolicinaArtikala) {
		
		setUslugaObavljena(true);
		setUslugaNaplacena(true);
		
		BigDecimal cijenaUsluge = getCijenaUsluge();
		
		BigDecimal konacnaCijenaUsluge = cijenaUsluge.multiply(new BigDecimal(kolicinaArtikala));
		return konacnaCijenaUsluge;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
