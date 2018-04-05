package hr.java.vjezbe.entitet;

public enum KategorijaArtikla {
	
	SOFTVER, ELEKTROTEHNIKA, MEHANIKA, OSTALO;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
