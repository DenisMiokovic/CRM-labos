package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Tvrtka;
import hr.java.vjezbe.javafx.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za prozor za unos klijenata.
 * 
 * @author Denis
 *
 */

public class NoviKlijentController {
	
	private String prezime;
	private String ime;
	private String oib;
	private String brojTelefona;
	private String eMailAdresa;
	private LocalDate datumRodjenja;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField oibTextField;
	
	@FXML
	private TextField prezimeTextField;
	
	@FXML
	private TextField imeTextField;
	
	@FXML
	private TextField brojTelefonaTextField;
	
	@FXML
	private TextField eMailAdresaTextField;
	
	@FXML
	private DatePicker datumRodjenjaDatePicker;
	
	@FXML
	private Button spremiButton;
	
	/**
	 * @param prezime podatak o prezimenu klijenta
	 * @param ime podatak o imenu klijenta
	 * @param oib podatak o OIB-u klijenta
	 * @param brojTelefona podatak o broju telefona klijenta
	 * @param eMailAdresa podatak o E-mail adresi klijenta
	 * @param datumRodjenja podatak o datumu roðenja klijenta
	 * @param logger stvaranje objekta klase za logiranje
	 * @param oibTextField stvaranje polja za unos OIB-a klijenta
	 * @param prezimeTextField stvaranje polja za unos prezimena klijenta
	 * @param imeTextField stvaranje polja za unos imena klijenta
	 * @param brojTelefonaTextField stvaranje polja za unos broja telefona klijenta
	 * @param eMailAdresaTextField stvaranje polja za unos e-mail adrese klijenta
	 * @param datumRodjenjaDatePicker stvaranje izbornika datuma za unos datuma roðenja klijenta
	 * @param spremiButton stvaranje objekta klase Button
	 * @throws SQLException 
	 */
	
	public static boolean isNumeric(String str)  {  
		try  {  
		    @SuppressWarnings("unused")
			double d = Double.parseDouble(str);  
		}  
		catch (NumberFormatException nfe)  {  
			return false;  
		}  
		return true;  
	}
	
	public void spremiKlijenta() throws IOException, SQLException {
		
		if (oibTextField.getText().isEmpty() == false && oibTextField.getText().length() == 11
				&& prezimeTextField.getText().isEmpty() == false && imeTextField.getText().isEmpty() == false
				&& brojTelefonaTextField.getText().isEmpty() == false && eMailAdresaTextField.getText().isEmpty() == false
				&& datumRodjenjaDatePicker.getValue() != null) {
			
			oib = oibTextField.getText();
			prezime = prezimeTextField.getText();
			ime = imeTextField.getText();
			brojTelefona = brojTelefonaTextField.getText();
			eMailAdresa = eMailAdresaTextField.getText();
			datumRodjenja = datumRodjenjaDatePicker.getValue();
			
			Klijent klijent = new Klijent(ime, prezime, oib, brojTelefona, eMailAdresa, datumRodjenja);
			
			List<Tvrtka> listaTvrtka = BazaPodataka.dohvatiTvrtku();
			Tvrtka MPtvrtka = null;
			for (Tvrtka tvrtka : listaTvrtka) {
				MPtvrtka = tvrtka;
			}
			
			try {
				BazaPodataka.spremiKlijenta(klijent, MPtvrtka);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Uspješno spremanje klijenta!");
				alert.setHeaderText("Uspješno spremanje klijenta!");
				alert.setContentText("Uneseni podaci za klijenta su uspješno spremljeni.");
				alert.showAndWait();
				
			} catch (Exception e) {
				logger.error("Pogreška kod spremanja podataka!", e);
				e.printStackTrace();
			}

			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			
			KlijentiEkranController.dodajNovogKlijenta(klijent);
			
		} else {
			
			String upoz = "";
			
			Alert alertE = new Alert(AlertType.ERROR);
			alertE.setTitle("Neuspješno spremanje klijenta!");
			alertE.setHeaderText("Potrebno je ispraviti slijedeæe pogreške:");
			
			if (prezimeTextField.getText().isEmpty()) {
				String upozPrezime = "Morate unijeti prezime! \n";
				upoz += upozPrezime;
			}
			if (imeTextField.getText().isEmpty()) {
				String upozIme = "Morate unijeti ime! \n";
				upoz += upozIme;
			}
			if (oibTextField.getText().isEmpty()) {
				String upozOIB = "Morate unijeti OIB! \n";
				upoz += upozOIB;
			} 
			if (oibTextField.getText().length() < 11 | oibTextField.getText().length() > 11) {
				String upozOIB1 = "OIB mora imati 11 znakova! \n";
				upoz += upozOIB1;
			} 
			if (isNumeric(oibTextField.getText())) {
				String upozOIB2 = "OIB mora biti u brojèanom obliku! \n";
				upoz += upozOIB2;
			}
			if (brojTelefonaTextField.getText().isEmpty()) {
				String upozBrTel = "Morate unijeti broj telefona! \n";
				upoz += upozBrTel;
			}
			if (eMailAdresaTextField.getText().isEmpty()) {
				String upozEMail = "Morate unijeti e-mail adresu! \n";
				upoz += upozEMail;
			}
			if (datumRodjenjaDatePicker.getValue() == null) {
				String upozDatum = "Morate unijeti datum roðenja! \n";
				upoz += upozDatum;
			}
			alertE.setContentText(upoz);
			alertE.showAndWait();
		}
	}
}
