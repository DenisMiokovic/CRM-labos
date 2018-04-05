package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.javafx.Main;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za ekran prikaza klijenata.
 * 
 * @author Denis
 *
 */

public class KlijentiEkranController {
	
	private static ObservableList<Klijent> observableListaKlijenata = FXCollections.observableArrayList();
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField klijentiFilterTextField;
	
	@FXML
	private TableView<Klijent> klijentiTableView;
	
	@FXML
	private TableColumn<Klijent, String> oibColumn;
	
	@FXML
	private TableColumn<Klijent, String> prezimeColumn;
	
	@FXML
	private TableColumn<Klijent, String> imeColumn;
	
	@FXML
	private TableColumn<Klijent, String> brojTelefonaColumn;
	
	@FXML
	private TableColumn<Klijent, String> eMailColumn;
	
	@FXML
	private TableColumn<Klijent, String> datumRodjenjaColumn;
	
	/**
	 * @param observableListaKlijenata stvaranje prikazne liste objekata klase Klijent
	 * @param logger stvaranje objekta klase za logiranje
	 * @param klijentiFilterTextField stvaranje polja za pretraživanje klijenata
	 * @param klijentiTableView stvaranje tabelarnog prikaza klijenata
	 * @param oibColumn stvaranje stupca u tabelarnom prikazu koji prikazuje OIB klijenta
	 * @param prezimeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje prezime klijenta
	 * @param imeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje ime klijenta
	 * @param brojTelefonaColumn stvaranje stupca u tabelarnom prikazu koji prikazuje broj telefona klijenta
	 * @param eMailColumn stvaranje stupca u tabelarnom prikazu koji prikazuje e-mail adresu klijenta
	 * @param datumRodjenjaColumn stvaranje stupca u tabelarnom prikazu koji prikazuje datum roðenja klijenta
	 */
	
	@FXML
	public void initialize() throws IOException {
		
		oibColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("oib"));
		
		prezimeColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("prezime"));
		
		imeColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("ime"));
		
		brojTelefonaColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("brojTelefona"));
		
		eMailColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("eMailAdresa"));
		
		datumRodjenjaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Klijent, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Klijent, String> param) {
				return new ReadOnlyObjectWrapper<String>(Main.dateFormatter.format(param.getValue().getDatumRodjenja())); 
			} 
			});
	}
	
	public void prikaziKlijente() throws SQLException {
		
		List<Klijent> filtriraniKlijenti = new ArrayList<Klijent>();
		List<Klijent> listaKlijenata = new ArrayList<Klijent>();
		try {
			listaKlijenata = BazaPodataka.dohvatiKlijente();
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
		
		if (klijentiFilterTextField.getText().isEmpty() == false) {
			
			filtriraniKlijenti = listaKlijenata.stream().filter(p -> p.getPrezime().contains(klijentiFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraniKlijenti = listaKlijenata;
		}
		observableListaKlijenata = FXCollections.observableArrayList(filtriraniKlijenti);
		klijentiTableView.setItems(observableListaKlijenata);
	}
	
	public static void dodajNovogKlijenta(Klijent noviKlijent) {
		
		observableListaKlijenata.add(noviKlijent);
	}
	
}
