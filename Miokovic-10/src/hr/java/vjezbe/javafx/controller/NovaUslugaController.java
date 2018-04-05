package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.entitet.VrstaUsluge;
import hr.java.vjezbe.javafx.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za prozor za unos komunikacije.
 * 
 * @author Denis
 *
 */

public class NovaUslugaController {
	
	private Klijent klijent;
	private VrstaUsluge vrstaUsluge;
	private String opisUsluge;
	private LocalDate datumUsluge;
	private BigDecimal cijenaUsluge;
	private Boolean uslugaObavljena;
	private Boolean uslugaNaplacena;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	ObservableList<String> opcije = FXCollections.observableArrayList("DA", "NE");
	
	@FXML
	private ComboBox<Klijent> klijentComboBox;
	
	@FXML
	private ComboBox<String> vrstaUslugeComboBox;
	
	@FXML
	private TextField opisTextField;
	
	@FXML
	private DatePicker datumDatePicker;
	
	@FXML
	private TextField cijenaTextField;

	@FXML
	private Button spremiButton;
	
	@FXML
	private ComboBox<String> obavljenaComboBox;
	
	@FXML
	private ComboBox<String> naplacenaComboBox;
	
	/**
	 * @param klijent podaci o klijentu
	 * @param vrstaUsluge podaci o vrsti usluge
	 * @param opisUsluge podatak o opisu usluge
	 * @param datumUsluge podatak o datumu usluge
	 * @param cijenaUsluge podatak o cijenu usluge
	 * @param uslugaObavljena podatak da li je usluga obavljena
	 * @param uslugaNaplacena podatak da li je usluga naplacena
	 * @param logger stvaranje objekta klase za logiranje
	 * @param opcije stvaranje liste klase ObservableList za pregled opcija
	 * @param klijentComboBox stvaranje objekta klase ComboBox za odabir klijenta
	 * @param vrstaUslugeComboBox stvaranje objekta klase ComboBox za odabir vrste usluge
	 * @param opisTextField stvaranje polja za unos opisa usluge
	 * @param datumDatePicker stvaranje objekta klase DatePicker za odabir datuma usluge
	 * @param cijenaTextField stvaranje polja za unos cijene usluge
	 * @param spremiButton stvaranje objekta klase Button
	 * @param obavljenaComboBox stvaranje objekta klase ComboBox za odabir da li je usluga obavljena ili nije
	 * @param naplacenaComboBox stvaranje objekta klase ComboBox za odabir da li je usluga naplaæena ili nije
	 * @throws SQLException 
	 */
	
	@FXML
	public void initialize() {
		
		ObservableList<Klijent> listaKlijenata = FXCollections.observableArrayList(PocetniEkranController.listaKlijenata);
		klijentComboBox.setItems(listaKlijenata);

		vrstaUslugeComboBox.getItems().setAll(VrstaUsluge.vrijednosti());
		
		obavljenaComboBox.setItems(opcije);
		naplacenaComboBox.setItems(opcije);
	}
	
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
	
	public void spremiUslugu() throws IOException, SQLException {
		
		if (klijentComboBox.getSelectionModel().isEmpty() == false && vrstaUslugeComboBox.getSelectionModel().isEmpty() == false &&
				opisTextField.getText().isEmpty() == false && datumDatePicker.getValue() != null && cijenaTextField.getText().isEmpty() == false
				&& obavljenaComboBox.getSelectionModel().isEmpty() == false && naplacenaComboBox.getSelectionModel().isEmpty() == false) {
			
			klijent = klijentComboBox.getValue();
			vrstaUsluge = VrstaUsluge.valueOf(vrstaUslugeComboBox.getValue());
			opisUsluge = opisTextField.getText();
			datumUsluge = datumDatePicker.getValue();
			cijenaUsluge = new BigDecimal(cijenaTextField.getText());
			String uslugaObavljenaString = obavljenaComboBox.getValue();
			uslugaObavljena = false;
			String uslugaNaplacenaString = naplacenaComboBox.getValue();
			uslugaNaplacena = false;
			
			Usluga usluga = new Usluga(klijent, vrstaUsluge, opisUsluge, datumUsluge, cijenaUsluge, uslugaObavljena, uslugaNaplacena);
			
			if (uslugaNaplacenaString.equals("DA")) {
				usluga.naplacena();
			} else {
				usluga.setUslugaNaplacena(false);
			}
			if (uslugaObavljenaString.equals("DA")) {
				usluga.obavljena();
			} else {
				usluga.setUslugaObavljena(false);
			}
			
			try {
				BazaPodataka.spremiUslugu(usluga);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Uspješno spremanje usluge!");
				alert.setHeaderText("Uspješno spremanje usluge!");
				alert.setContentText("Uneseni podaci za uslugu su uspješno spremljeni.");
				alert.showAndWait();
				
			} catch (IOException e) {
				logger.error("Pogreška kod spremanja podataka!", e);
				e.printStackTrace();
			}
			
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			
			UslugeEkranController.dodajNovuUslugu(usluga);
			
		} else {
			
			String upoz = "";
			
			Alert alertE = new Alert(AlertType.ERROR);
			alertE.setTitle("Neuspješno spremanje zaposlenika!");
			alertE.setHeaderText("Potrebno je ispraviti slijedeæe pogreške:");
			
			if (klijentComboBox.getSelectionModel().isEmpty()) {
				String upozKlijent = "Morate odabrati klijenta! \n";
				upoz += upozKlijent;
			} 
			
			if (vrstaUslugeComboBox.getSelectionModel().isEmpty()) {
				String upozVrUsl = "Morate odabrati vrstu usluge! \n";
				upoz += upozVrUsl;
			} 
			
			if (opisTextField.getText().isEmpty()) {
				String upozOpUsl = "Morate unijeti opis usluge! \n";
				upoz += upozOpUsl;
			} 
			
			if (datumDatePicker.getValue() == null) {
				String upozDatum = "Morate odabrati datum usluge! \n";
				upoz += upozDatum;
			}
			
			if (cijenaTextField.getText().isEmpty()) {
				String upozCijena = "Morate unijeti cijenu! \n";
				upoz += upozCijena;
			}
			
			if (isNumeric(cijenaTextField.getText()) == false) {
				String upozDatum = "Morate unijeti cijenu brojèano! \n";
				upoz += upozDatum;
			}
			
			if (obavljenaComboBox.getSelectionModel().isEmpty()) {
				String upozUslOb = "Morate odabrati da li je usluga obavljena! \n";
				upoz += upozUslOb;
			}
			
			if (naplacenaComboBox.getSelectionModel().isEmpty()) {
				String upozUslNap = "Morate odabrati da li je usluga naplaæena! \n";
				upoz += upozUslNap;
			}

			alertE.setContentText(upoz);
			alertE.showAndWait();
		}
	}
}
