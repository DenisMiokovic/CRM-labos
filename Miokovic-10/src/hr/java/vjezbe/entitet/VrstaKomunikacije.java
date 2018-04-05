package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public enum VrstaKomunikacije {
	
	VERBALNA (1, "VERBALNA"),
	PISMENA (2, "PISMENA"), 
	ELEKTRONICKA (3, "ELEKTRONICKA"), 
	OSTALO (4, "OSTALO");

	private Integer id;
	private String naziv;
	
	private VrstaKomunikacije(Integer id, String naziv) {
		
		this.setId(id);
		this.setNaziv(naziv);
		}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public static List<String> vrijednosti() {
		
		List<String> listaVrKom = new ArrayList<>();
		
		for (VrstaKomunikacije vrstaKomunikacije : VrstaKomunikacije.values()) {
			
			listaVrKom.add(vrstaKomunikacije.getNaziv());
			
		}
		return listaVrKom;
	}
}
