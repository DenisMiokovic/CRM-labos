package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.javafx.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za ekran prikaza artikala.
 * 
 * @author Denis
 *
 */

public class ArtikliEkranController {
	
	private static ObservableList<Artikl> observableListaArtikala = FXCollections.observableArrayList();
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField artikliFilterTextField;
	
	@FXML
	private TableView<Artikl> artikliTableView;
	
	@FXML
	private TableColumn<Artikl, String> nazivColumn;
	
	@FXML
	private TableColumn<Artikl, String> kategorijaColumn;

	/**
	 * @param observableListaArtikala stvaranje prikazne liste objekata klase Artikl
	 * @param logger stvaranje objekta klase za logiranje
	 * @param artikliFilterTextField stvaranje polja za pretraživanje artikala
	 * @param artikliTableView stvaranje tabelarnog prikaza artikala
	 * @param nazivColumn stvaranje stupca u tabelarnom prikazu koji prikazuje naziv artikla
	 * @param kategorijaColumn stvaranje stupca u tabelarnom prikazu koji prikazuje kategoriju artikla
	 */
	
	@FXML
	public void initialize() {
		
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Artikl, String>("naziv"));
		
		kategorijaColumn.setCellValueFactory(new PropertyValueFactory<Artikl, String>("kategorija"));
		
	}
	
	public void prikaziArtikle() throws SQLException {
		
		List<Artikl> filtriraniArtikli = new ArrayList<Artikl>();
		List<Artikl> listaArtikala = new ArrayList<Artikl>();
		try {
			listaArtikala = BazaPodataka.dohvatiArtikle();
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
		
		if (artikliFilterTextField.getText().isEmpty() == false) {
			
			filtriraniArtikli = listaArtikala.stream().filter(p -> p.getNaziv().contains(artikliFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraniArtikli = listaArtikala;
		}
		observableListaArtikala = FXCollections.observableArrayList(filtriraniArtikli);
		artikliTableView.setItems(observableListaArtikala);
	}
	
	public static void dodajNoviArtikl(Artikl noviArtikl) {
		observableListaArtikala.add(noviArtikl);
	}
}
