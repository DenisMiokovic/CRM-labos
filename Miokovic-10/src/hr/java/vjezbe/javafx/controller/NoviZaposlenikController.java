package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Tvrtka;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.javafx.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za prozor za unos zaposlenika.
 * 
 * @author Denis
 *
 */

public class NoviZaposlenikController {
	
	private String prezime;
	private String ime;
	private String korisnickoIme;
	private String sifraZaposlenika;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField korisnickoImeTextField;
	
	@FXML
	private TextField prezimeTextField;
	
	@FXML
	private TextField imeTextField;
	
	@FXML
	private TextField sifraZaposlenikaTextField;

	@FXML
	private Button spremiButton;
	
	/**
	 * @param prezime podatak o prezimenu zaposlenika
	 * @param ime podatak o imenu zaposlenika
	 * @param korisnickoIme podatak o korisnièkom imenu zaposlenika
	 * @param sifraZaposlenika podatak o šifri zaposlenika
	 * @param logger stvaranje objekta klase za logiranje
	 * @param korisnickoImeTextField stvaranje polja za unos korisnièkog imena zaposlenika
	 * @param prezimeTextField stvaranje polja za unos prezimena zaposlenika
	 * @param imeTextField stvaranje polja za unos imena zaposlenika
	 * @param sifraZaposlenikaTextField stvaranje polja za unos šifre zaposlenika
	 * @param spremiButton stvaranje objekta klase Button
	 * @throws SQLException 
	 */
	
	public void spremiZaposlenika() throws IOException, SQLException {
		
		if (korisnickoImeTextField.getText().isEmpty() == false && prezimeTextField.getText().isEmpty() == false
				&& imeTextField.getText().isEmpty() == false && sifraZaposlenikaTextField.getText().isEmpty() == false) {
			
			korisnickoIme = korisnickoImeTextField.getText();
			prezime = prezimeTextField.getText();
			ime = imeTextField.getText();
			sifraZaposlenika = sifraZaposlenikaTextField.getText();
			
			Zaposlenik zaposlenik = new Zaposlenik(ime, prezime, korisnickoIme, sifraZaposlenika);

			List<Tvrtka> listaTvrtka = BazaPodataka.dohvatiTvrtku();
			Tvrtka MPtvrtka = null;
			for (Tvrtka tvrtka : listaTvrtka) {
				MPtvrtka = tvrtka;
			}
			
			try {
				BazaPodataka.spremiZaposlenika(zaposlenik, MPtvrtka);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Uspješno spremanje zaposlenika!");
				alert.setHeaderText("Uspješno spremanje zaposlenika!");
				alert.setContentText("Uneseni podaci za zaposlenika su uspješno spremljeni.");
				alert.showAndWait();
				
			} catch (IOException e) {
				logger.error("Pogreška kod spremanja podataka!", e);
				e.printStackTrace();
			}

			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			
			ZaposleniciEkranController.dodajNovogZaposlenika(zaposlenik);
			
		} else {
			
			String upoz = "";
			
			Alert alertE = new Alert(AlertType.ERROR);
			alertE.setTitle("Neuspješno spremanje zaposlenika!");
			alertE.setHeaderText("Potrebno je ispraviti slijedeæe pogreške:");
			
			if (korisnickoImeTextField.getText().isEmpty()) {
				String upozKorIme = "Morate unijeti korisnièko ime! \n";
				upoz += upozKorIme;
			} 
			if (prezimeTextField.getText().isEmpty()) {
				String upozPrezime = "Morate unijeti prezime! \n";
				upoz += upozPrezime;
			}
			if (imeTextField.getText().isEmpty()) {
				String upozIme = "Morate unijeti ime! \n";
				upoz += upozIme;
			}
			if (sifraZaposlenikaTextField.getText().isEmpty()) {
				String upozSifra = "Morate unijeti šifru zaposlenika! \n";
				upoz += upozSifra;
			}
			alertE.setContentText(upoz);
			alertE.showAndWait();
		}
	}
}
