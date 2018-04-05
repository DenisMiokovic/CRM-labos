package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Komunikacija;
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

/**
 * 
 * Predstavlja kontroler klasu aplikacije za ekran prikaza komunikacija.
 * 
 * @author Denis
 *
 */

public class KomunikacijeEkranController {
	
	private static ObservableList<Komunikacija> observableListaKomunikacija = FXCollections.observableArrayList();
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField zaposleniciFilterTextField;
	
	@FXML
	private TableView<Komunikacija> komunikacijeTableView;
	
	@FXML
	private TableColumn<Komunikacija, String> klijentColumn;
	
	@FXML
	private TableColumn<Komunikacija, String> zaposlenikColumn;
	
	@FXML
	private TableColumn<Komunikacija, String> vrstaKomunikacijeColumn;
	
	@FXML
	private TableColumn<Komunikacija, String> sadrzajKomunikacijeColumn;
	
	@FXML
	private TableColumn<Komunikacija, String> vrijemeKomunikacijeColumn;
		
	/**
	 * @param observableListaKomunikacija stvaranje prikazne liste objekata klase Komunikacija
	 * @param logger stvaranje objekta klase za logiranje
	 * @param zaposleniciFilterTextField stvaranje polja za pretraživanje komunikacija prema prezimenu zaposlenika
	 * @param komunikacijeTableView stvaranje tabelarnog prikaza komunikacija
	 * @param klijentColumn stvaranje stupca u tabelarnom prikazu koji prikazuje klijenta komunikacije
	 * @param zaposlenikColumn stvaranje stupca u tabelarnom prikazu koji prikazuje zaposlenika koji je izvršio komunikaciju
	 * @param vrstaKomunikacijeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje vrstu komunikacije
	 * @param sadrzajKomunikacijeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje sadržaj komunikacije
	 * @param vrijemeKomunikacijeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje vrijeme komunikacije
	 */
	
	@FXML
	public void initialize() throws IOException {
		
		klijentColumn.setCellValueFactory(new PropertyValueFactory<Komunikacija, String>("klijent"));
		
		zaposlenikColumn.setCellValueFactory(new PropertyValueFactory<Komunikacija, String>("zaposlenik"));
		
		vrstaKomunikacijeColumn.setCellValueFactory(new PropertyValueFactory<Komunikacija, String>("vrstaKomunikacije"));
		
		sadrzajKomunikacijeColumn.setCellValueFactory(new PropertyValueFactory<Komunikacija, String>("sadrzajKomunikacije"));
		
		vrijemeKomunikacijeColumn.setCellValueFactory(new PropertyValueFactory<Komunikacija, String>("vrijemeKomunikacije") {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Komunikacija, String> param) {
				return new ReadOnlyObjectWrapper<String>(Main.dateTimeFormatter.format(param.getValue().getVrijemeKomunikacije())); 
			} 
			});
	}
	
	public void prikaziKomunikacije() throws SQLException {
		
		List<Komunikacija> filtriraneKomunikacije = new ArrayList<Komunikacija>();
		List<Komunikacija> listaKomunikacija = new ArrayList<Komunikacija>();
		try {
			listaKomunikacija = BazaPodataka.dohvatiKomunikacije();
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
		
		if (zaposleniciFilterTextField.getText().isEmpty() == false) {
			
			filtriraneKomunikacije = listaKomunikacija.stream().filter(p -> p.getZaposlenik().getPrezime().contains(zaposleniciFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraneKomunikacije = listaKomunikacija;
		}
		observableListaKomunikacija = FXCollections.observableArrayList(filtriraneKomunikacije);
		komunikacijeTableView.setItems(observableListaKomunikacija);
	}
	
	public static void dodajNovuKomunikaciju(Komunikacija novaKomunikacija) {
		
		observableListaKomunikacija.add(novaKomunikacija);
	}
}
