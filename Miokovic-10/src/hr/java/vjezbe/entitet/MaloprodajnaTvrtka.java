package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Predstavlja entitet maloprodajne tvrtke koja nasljeðuje klasu Tvrtka i sve njene parametre, te nadodaje svoj parametar, polje klase Artikl.
 * 
 * @author Denis
 *
 */

public class MaloprodajnaTvrtka extends Tvrtka {
	
	@SuppressWarnings("unused")
	private List<Artikl> artikli = new ArrayList<>();
	private Integer id;
	
	/**
	 * Inicijalizira podatak o artiklu, te nasljeðuje podatak o nazivu i OIB-u.
	 * 
	 * @param nazivTvrtke podatak o nazivu tvrtke (super)
	 * @param oibTvrtke podatak o OIB-u tvrtke (super)
	 * @param artikli podatak o polju klase Artikl
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public MaloprodajnaTvrtka(String nazivTvrtke, String oibTvrtke, List<Artikl> artikli) {
		
		super(nazivTvrtke, oibTvrtke);
		this.artikli = artikli;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
