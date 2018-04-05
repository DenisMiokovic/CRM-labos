package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public enum VrstaUsluge {
	
	PRODAJNA (1, "PRODAJNA"),
	PRAVNA (2, "PRAVNA"),
	OSTALO (3, "OSTALO");
	
	private Integer id;
	private String naziv;
	
	private VrstaUsluge(Integer id, String naziv) {
		
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
		
		List<String> listaVrUsl = new ArrayList<>();
		
		for (VrstaUsluge vrstaUsluge : VrstaUsluge.values()) {
			
			listaVrUsl.add(vrstaUsluge.getNaziv());
			
		}
		return listaVrUsl;
	}
}
