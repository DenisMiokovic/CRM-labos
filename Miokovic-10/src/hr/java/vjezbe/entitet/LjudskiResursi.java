package hr.java.vjezbe.entitet;

import java.util.Collections;
import java.util.List;

/**
 * 
 * Predstavlja generièku klasu koja je definirana listom ljudskih resursa
 * 
 * @author Denis
 *
 */

public class LjudskiResursi<T extends Osoba> {
	
	private List<T> listaLjudskihResursa;
	private Integer id;
	
	/**
	 * Inicijalizira podatak o listi ljudskih resursa.
	 * 
	 * @param listaLjudskihResursa lista podataka o ljudskim resursima
	 * @param id podatak o ID-u u bazi podataka
	 */
	
	public LjudskiResursi(List<T> listaLjudskihResursa) {
		
		this.listaLjudskihResursa = listaLjudskihResursa;
	}
	
	public T get(Integer i) {
		
		return listaLjudskihResursa.get(i);
	}
	
	public void add(T noviObjekt) {
		
		listaLjudskihResursa.add(noviObjekt);
	}
	
	public List<T> getSortedList() {

		Collections.sort(listaLjudskihResursa, (p1, p2) -> p1.getPrezime().compareTo(p2.getPrezime()));
		return listaLjudskihResursa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
