package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.javafx.Main;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za ekran prikaza usluga.
 * 
 * @author Denis
 *
 */

public class UslugeEkranController {
	
	private static ObservableList<Usluga> observableListaUsluga = FXCollections.observableArrayList();
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField klijentiFilterTextField;
	
	@FXML
	private TableView<Usluga> uslugeTableView;
	
	@FXML
	private TableColumn<Usluga, String> opisColumn;
	
	@FXML
	private TableColumn<Usluga, String> klijentColumn;
	
	@FXML
	private TableColumn<Usluga, String> vrstaColumn;
	
	@FXML
	private TableColumn<Usluga, String> datumColumn;
	
	@FXML
	private TableColumn<Usluga, String> cijenaColumn;
	
	@FXML
	private TableColumn<Usluga, String> obavljenaColumn;
	
	@FXML
	private TableColumn<Usluga, String> placenaColumn;
		
	/**
	 * @param observableListaUsluga stvaranje prikazne liste objekata klase Usluga
	 * @param logger stvaranje objekta klase za logiranje
	 * @param klijentiFilterTextField stvaranje polja za pretraživanje komunikacija prema prezimenu klijenta
	 * @param uslugeTableView stvaranje tabelarnog prikaza usluga
	 * @param opisColumn stvaranje stupca u tabelarnom prikazu koji prikazuje opis usluge
	 * @param klijentColumn stvaranje stupca u tabelarnom prikazu koji prikazuje klijenta usluge
	 * @param vrstaColumn stvaranje stupca u tabelarnom prikazu koji prikazuje vrstu usluge
	 * @param datumColumn stvaranje stupca u tabelarnom prikazu koji prikazuje datum usluge
	 * @param cijenaColumn stvaranje stupca u tabelarnom prikazu koji prikazuje cijenu usluge
	 * @param obavljenaColumn stvaranje stupca u tabelarnom prikazu koji prikazuje da li je usluga obavljena
	 * @param placenaColumn stvaranje stupca u tabelarnom prikazu koji prikazuje da li je usluga plaæena
	 */
	
	@FXML
	public void initialize() throws IOException {
		
		opisColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("opisUsluge"));
		
		klijentColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("klijent"));
		
		vrstaColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("vrstaUsluge"));

		datumColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("datumUsluge") {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Usluga, String> param) {
				return new ReadOnlyObjectWrapper<String>(Main.dateFormatter.format(param.getValue().getDatumUsluge())); 
			} 
			});

		cijenaColumn.setCellValueFactory(new PropertyValueFactory<Usluga, String>("cijenaUsluge"));
				
		obavljenaColumn.setCellValueFactory(obavljenaData -> {
			boolean obavljena = obavljenaData.getValue().isUslugaObavljena();
			String obavljenaString;
			if (obavljena == true) {
				obavljenaString = "DA";
			} else {
				obavljenaString = "NE";
			}
			return new ReadOnlyStringWrapper(obavljenaString);
		});
		
		placenaColumn.setCellValueFactory(naplacenaData -> {
			boolean naplacena = naplacenaData.getValue().isUslugaNaplacena();
			String naplacenaString;
			if (naplacena == true) {
				naplacenaString = "DA";
			} else {
				naplacenaString = "NE";
			}
			return new ReadOnlyStringWrapper(naplacenaString);
		});
	}
	
	public void prikaziUsluge() throws SQLException {
		
		List<Usluga> filtriraneUsluge = new ArrayList<Usluga>();
		List<Usluga> listaUsluga = new ArrayList<Usluga>();
		try {
			listaUsluga = BazaPodataka.dohvatiUsluge();
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
		
		if (klijentiFilterTextField.getText().isEmpty() == false) {
			
			filtriraneUsluge = listaUsluga.stream().filter(p -> p.getKlijent().getPrezime().contains(klijentiFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraneUsluge = listaUsluga;
		}
		observableListaUsluga = FXCollections.observableArrayList(filtriraneUsluge);
		uslugeTableView.setItems(observableListaUsluga);
	}
	
	public static void dodajNovuUslugu(Usluga novaUsluga) {
		
		observableListaUsluga.add(novaUsluga);
	}
}