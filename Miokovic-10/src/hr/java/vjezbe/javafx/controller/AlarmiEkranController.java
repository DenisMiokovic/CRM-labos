package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.javafx.Main;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za ekran prikaza alarma.
 * 
 * @author Denis
 *
 */

public class AlarmiEkranController {
	
	private static ObservableList<Alarm> observableListaAlarma = FXCollections.observableArrayList();
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField alarmiFilterTextField;
	
	@FXML
	private TableView<Alarm> alarmiTableView;
	
	@FXML
	private TableColumn<Alarm, String> klijentColumn;
	
	@FXML
	private TableColumn<Alarm, String> opisColumn;
	
	@FXML
	private TableColumn<Alarm, String> vrijemeColumn;
	
	/**
	 * @param alarmiFilterTextField stvaranje polja za pretraživanje alarma
	 * @param alarmiTableView stvaranje tabelarnog prikaza alarma
	 * @param klijentColumn stvaranje stupca u tabelarnom prikazu koji prikazuje klijenta vezanog uz alarm
	 * @param opisColumn stvaranje stupca u tabelarnom prikazu koji prikazuje opis alarma
	 * @param vrijemeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje vrijeme alarma
	 */
	
	@FXML
	public void initialize() {
		
		klijentColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alarm, String>, ObservableValue<String>>() {

		    @Override
		    public ObservableValue<String> call(CellDataFeatures<Alarm, String> p) {
		            return new ReadOnlyObjectWrapper<String>(p.getValue().getKlijent().getIme() + " " + p.getValue().getKlijent().getPrezime());
		    }
		});
		
		opisColumn.setCellValueFactory(new PropertyValueFactory<Alarm, String>("opisAlarma"));
		
		vrijemeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alarm, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Alarm, String> param) {
				return new ReadOnlyObjectWrapper<String>(Main.dateTimeFormatter.format(param.getValue().getVrijemeAlarma())); 
			} 
			});
		
	}
	
	public void prikaziAlarme() throws SQLException {
		
		List<Alarm> filtriraniAlarmi = new ArrayList<Alarm>();
		List<Alarm> listaAlarma = new ArrayList<Alarm>();
		try {
			listaAlarma = BazaPodataka.dohvatiAlarme("SELECT * FROM CRM.ALARM");
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
		
		if (alarmiFilterTextField.getText().isEmpty() == false) {
			
			filtriraniAlarmi = PocetniEkranController.listaAlarma.stream().filter(p -> p.getKlijent().getOib().contains(alarmiFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraniAlarmi = listaAlarma;
		}
		observableListaAlarma = FXCollections.observableArrayList(filtriraniAlarmi);
		alarmiTableView.setItems(observableListaAlarma);
	}
	
	public static void dodajNoviAlarm(Alarm noviAlarm) {
		observableListaAlarma.add(noviAlarm);
	}
}
