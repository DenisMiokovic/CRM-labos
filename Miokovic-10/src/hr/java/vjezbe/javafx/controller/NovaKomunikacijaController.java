package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Komunikacija;
import hr.java.vjezbe.entitet.VrstaKomunikacije;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.javafx.Main;
import hr.java.vjezbe.niti.AlarmiNit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za prozor za unos komunikacije.
 * 
 * @author Denis
 *
 */

public class NovaKomunikacijaController {
	
	private Klijent klijent;
	private Zaposlenik zaposlenik;
	private VrstaKomunikacije vrstaKomunikacije;
	private String sadrzajKomunikacije;
	private LocalDateTime vrijemeKomunikacije;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField klijentTextField;
	
	@FXML
	private TextField zaposlenikTextField;
	
	@FXML
	private ComboBox<String> vrstaKomunikacijeComboBox;
	
	@FXML
	private TextField sadrzajTextField;

	@FXML
	private Button spremiButton;
	
	/**
	 * @param klijent podaci o klijentu
	 * @param zaposlenik podaci o zaposleniku
	 * @param vrstaKomunikacije podatak o vrsti komunikacije
	 * @param sadrzajKomunikacije podatak o sadržaju komunikacije
	 * @param vrijemeKomunikacije podatak o vremenu komunikacije
	 * @param logger stvaranje objekta klase za logiranje
	 * @param klijentComboBox stvaranje objekta klase ComboBox za odabir klijenta
	 * @param zaposlenikComboBox stvaranje objekta klase ComboBox za odabir zaposlenika
	 * @param vrstaKomunikacijeComboBox stvaranje objekta klase ComboBox za odabir vrste komunikacije
	 * @param sadrzajTextField stvaranje polja za unos sadržaja komunikacije
	 * @param spremiButton stvaranje objekta klase Button
	 * @throws SQLException 
	 */
	
	@FXML
	public void initialize() {
		
		klijentTextField.setText(AlarmiNit.klijentKomunikacije.getIme()
				+ " " + AlarmiNit.klijentKomunikacije.getPrezime());
		klijentTextField.setEditable(false);
		
		zaposlenikTextField.setText(ZaposleniciEkranController.zaposlenikKomunikacije.getIme()
				+ " " + ZaposleniciEkranController.zaposlenikKomunikacije.getPrezime());
		zaposlenikTextField.setEditable(false);
		
		vrstaKomunikacijeComboBox.getItems().setAll(VrstaKomunikacije.vrijednosti());
	}
	
	public void spremiKomunikaciju() throws IOException, SQLException {
		
		if (vrstaKomunikacijeComboBox.getSelectionModel().isEmpty() == false && sadrzajTextField.getText().isEmpty() == false) {
			
			klijent = AlarmiNit.klijentKomunikacije;
			zaposlenik = ZaposleniciEkranController.zaposlenikKomunikacije;
			vrstaKomunikacije = VrstaKomunikacije.valueOf(vrstaKomunikacijeComboBox.getValue());
			sadrzajKomunikacije = sadrzajTextField.getText();
			vrijemeKomunikacije = LocalDateTime.now();
			
			Komunikacija komunikacija = new Komunikacija(klijent, zaposlenik, vrstaKomunikacije, sadrzajKomunikacije, vrijemeKomunikacije);
			
			try {
				BazaPodataka.spremiKomunikaciju(komunikacija);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Uspješno spremanje komunikacije!");
				alert.setHeaderText("Uspješno spremanje komunikacije!");
				alert.setContentText("Uneseni podaci za komunikaciju su uspješno spremljeni.");
				alert.showAndWait();
				
			} catch (IOException e) {
				logger.error("Pogreška kod spremanja podataka!", e);
				e.printStackTrace();
			}
			
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			
			KomunikacijeEkranController.dodajNovuKomunikaciju(komunikacija);
			
			AlarmiNit.unosKomunikacije = true;
			
		} else {
			
			String upoz = "";
			
			Alert alertE = new Alert(AlertType.ERROR);
			alertE.setTitle("Neuspješno spremanje zaposlenika!");
			alertE.setHeaderText("Potrebno je ispraviti slijedeæe pogreške:");
			
			if (vrstaKomunikacijeComboBox.getSelectionModel().isEmpty()) {
				String upozVrKom = "Morate odabrati vrstu komunikacije! \n";
				upoz += upozVrKom;
			} 
			
			if (sadrzajTextField.getText().isEmpty()) {
				String upozSadrzaj = "Morate unijeti sadržaj komunikacije! \n";
				upoz += upozSadrzaj;
			}

			alertE.setContentText(upoz);
			alertE.showAndWait();
		}
	}
}
