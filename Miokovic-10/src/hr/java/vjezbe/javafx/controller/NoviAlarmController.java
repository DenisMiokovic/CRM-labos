package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.javafx.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za prozor za unos alarma.
 * 
 * @author Denis
 *
 */

public class NoviAlarmController {
	
	private Klijent klijent;
	private String opisAlarma;
	private LocalDateTime vrijemeAlarma;
	private Boolean statusAlarma;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private ComboBox<Klijent> klijentComboBox;
	
	@FXML
	private TextField opisTextField;

	@FXML
	private Button spremiButton;
	
	/**
	 * @param klijent podaci o klijentu
	 * @param opisAlarma podatak o opisu alarma
	 * @param vrijemeAlarma podatak o vremenu alarma
	 * @param statusAlarma podatak o statusu alarma
	 * @param logger stvaranje objekta klase za logiranje
	 * @param klijentComboBox stvaranje objekta klase ComboBox za odabir klijenta
	 * @param opisTextField stvaranje polja za unos opisa alarma
	 * @param spremiButton stvaranje objekta klase Button
	 * @throws SQLException 
	 */
	
	@FXML
	public void initialize() {
		
		ObservableList<Klijent> listaKlijenata = FXCollections.observableArrayList(PocetniEkranController.listaKlijenata);
		
		klijentComboBox.setItems(listaKlijenata);
	}
	
	public void spremiAlarm() throws IOException, SQLException {
		
		if (klijentComboBox.getSelectionModel().isEmpty() == false && opisTextField.getText().isEmpty() == false) {
			
			klijent = klijentComboBox.getValue();
			opisAlarma = opisTextField.getText();
			vrijemeAlarma = LocalDateTime.now().plusMinutes(1);
			statusAlarma = true;
			
			Alarm alarm = new Alarm(klijent, opisAlarma, vrijemeAlarma, statusAlarma);
			
			try {
				BazaPodataka.spremiAlarm(alarm);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Uspješno spremanje alarma!");
				alert.setHeaderText("Uspješno spremanje alarma!");
				alert.setContentText("Uneseni podaci za alarm su uspješno spremljeni.");
				alert.showAndWait();
				
			} catch (IOException e) {
				logger.error("Pogreška kod spremanja podataka!", e);
				e.printStackTrace();
			}

			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			
			AlarmiEkranController.dodajNoviAlarm(alarm);
			
		} else {
			
			String upoz = "";
			
			Alert alertE = new Alert(AlertType.ERROR);
			alertE.setTitle("Neuspješno spremanje zaposlenika!");
			alertE.setHeaderText("Potrebno je ispraviti slijedeæe pogreške:");
			
			if (klijentComboBox.getSelectionModel().isEmpty()) {
				String upozKlijent = "Morate odabrati klijenta! \n";
				upoz += upozKlijent;
			} 
			if (opisTextField.getText().isEmpty()) {
				String upozOpis = "Morate unijeti opis alarma! \n";
				upoz += upozOpis;
			}

			alertE.setContentText(upoz);
			alertE.showAndWait();
		}
	}
}
