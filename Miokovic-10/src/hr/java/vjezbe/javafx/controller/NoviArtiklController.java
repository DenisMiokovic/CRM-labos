package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.KategorijaArtikla;
import hr.java.vjezbe.entitet.Tvrtka;
import hr.java.vjezbe.javafx.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za prozor za unos artikala.
 * 
 * @author Denis
 *
 */

public class NoviArtiklController {
	
	private String naziv;
	@SuppressWarnings("unused")
	private ToggleGroup grupa;
	private String kategorijaA;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	@FXML
	private TextField nazivTextField;
	
	@FXML
	private RadioButton softverRButton;
	
	@FXML
	private RadioButton elektrotehnikaRButton;
	
	@FXML
	private RadioButton mehanikaRButton;
	
	@FXML
	private RadioButton ostaloRButton;
	
	@FXML
	private Button spremiButton;
	
	/**
	 * @param naziv podatak o nazivu artikla
	 * @param grupa stvaranje grupe objekata klase RadioButton
	 * @param kategorijaA podatak o broju kategorije
	 * @param logger stvaranje objekta klase za logiranje
	 * @param nazivTextField stvaranje polja za unos naziva artikla
	 * @param softverRButton stvaranje objekta klase RadioButton za odabir kategorije SOFTVER
	 * @param elektrotehnikaRButton stvaranje objekta klase RadioButton za odabir kategorije ELEKTROTEHNIKA
	 * @param mehanikaRButton stvaranje objekta klase RadioButton za odabir kategorije MEHANIKA
	 * @param ostaloRButton stvaranje objekta klase RadioButton za odabir kategorije OSTALO
	 * @param spremiButton stvaranje objekta klase Button
	 */
	
	public static boolean isInteger(String s) {
		
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public void spremiArtikl() throws IOException, SQLException {
		
		KategorijaArtikla kategorija = null;
		
		String upoz = "";
		
		Alert alertE = new Alert(AlertType.ERROR);
		alertE.setTitle("Neuspješno spremanje artikla!");
		alertE.setHeaderText("Potrebno je ispraviti slijedeæe pogreške:");
	
		if (nazivTextField.getText().isEmpty() == false) {
			
			if (softverRButton.isSelected()	|| elektrotehnikaRButton.isSelected() || mehanikaRButton.isSelected()
				|| ostaloRButton.isSelected()) {
			
				naziv = nazivTextField.getText();
				
				if (softverRButton.isSelected()) {
					
					kategorijaA = "1";
				}
				if (elektrotehnikaRButton.isSelected()) {
					
					kategorijaA = "2";
				}
				if (mehanikaRButton.isSelected()) {
					
					kategorijaA = "3";
				}
				if (ostaloRButton.isSelected()) {
					
					kategorijaA = "4";
				}
	
				Integer kategorijaBr = Integer.parseInt(kategorijaA);
				
				if (kategorijaBr >= 1 && kategorijaBr < KategorijaArtikla.values().length) {
					kategorija = KategorijaArtikla.values()[kategorijaBr - 1];
				} else {
					kategorija = KategorijaArtikla.OSTALO;
				}
				
				Artikl artikl = new Artikl(naziv, kategorija);

				List<Tvrtka> listaTvrtka = BazaPodataka.dohvatiTvrtku();
				Tvrtka MPtvrtka = null;
				for (Tvrtka tvrtka : listaTvrtka) {
					MPtvrtka = tvrtka;
				}
				
				try {
					BazaPodataka.spremiArtikl(artikl, MPtvrtka);
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Uspješno spremanje artikla!");
					alert.setHeaderText("Uspješno spremanje artikla!");
					alert.setContentText("Uneseni podaci za artikl su uspješno spremljeni.");
					alert.showAndWait();
					
				} catch (Exception e) {
					logger.error("Pogreška kod spremanja podataka!", e);
					e.printStackTrace();
				}

				Stage stage = (Stage) spremiButton.getScene().getWindow();
				stage.close();
				
				ArtikliEkranController.dodajNoviArtikl(artikl);
			} else {
				
				if (softverRButton.isSelected() == false && elektrotehnikaRButton.isSelected() == false && mehanikaRButton.isSelected() == false
						&& ostaloRButton.isSelected() == false) {
					String upozKategorija = "Morate odabrati kategoriju! \n";
					upoz += upozKategorija;
				}
				
				alertE.setContentText(upoz);
				alertE.showAndWait();
			}
			
		} else {
			
			if (nazivTextField.getText().isEmpty()) {
				String upozNaziv = "Morate unijeti naziv! \n";
				upoz += upozNaziv;
			} 
			if (softverRButton.isSelected() == false && elektrotehnikaRButton.isSelected() == false && mehanikaRButton.isSelected() == false
					&& ostaloRButton.isSelected() == false) {
				String upozKategorija = "Morate odabrati kategoriju! \n";
				upoz += upozKategorija;
			}
			
			alertE.setContentText(upoz);
			alertE.showAndWait();
		}
	}
}
