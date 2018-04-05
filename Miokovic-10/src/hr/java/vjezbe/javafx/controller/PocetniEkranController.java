package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Tvrtka;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.javafx.Main;
import hr.java.vjezbe.niti.AlarmiNit;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za poèetni ekran.
 * 
 * @author Denis
 *
 */

public class PocetniEkranController {
	
	static List<Klijent> listaKlijenata;
	static List<Zaposlenik> listaZaposlenika;
	static List<Artikl> listaArtikala;
	static List<Alarm> listaAlarma;
	private Tvrtka tvrtka;
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
	 * @param listaKlijenata stvaranje liste objekata klase Klijent
	 * @param listaZaposlenika stvaranje liste objekata klase Zaposlenik
	 * @param listaArtikala stvaranje liste objekata klase Artikl
	 * @param listaAlarma stvaranje liste objekata klase Alarm
	 * @param tvrtka stvaranje objekta klase MaloprodajnaTvrtka
	 * @param logger stvaranje objekta klase za logiranje
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
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		AlarmiNit alarmiNit = new AlarmiNit();
		scheduler.scheduleAtFixedRate(alarmiNit, 0, 10, TimeUnit.SECONDS);
		
		try {
			listaKlijenata = BazaPodataka.dohvatiKlijente();
			listaAlarma = BazaPodataka.dohvatiAlarme("SELECT * FROM CRM.ALARM");
			listaArtikala = BazaPodataka.dohvatiArtikle();
			listaZaposlenika = BazaPodataka.dohvatiZaposlenike();
			List<Tvrtka> listaTvrtka = BazaPodataka.dohvatiTvrtku();
			tvrtka = listaTvrtka.get(0);
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}

		tvrtka.setKlijenti(listaKlijenata);
		tvrtka.setZaposlenici(listaZaposlenika);
	}
	
	public void prikaziAlarme() throws SQLException {
		
		List<Alarm> filtriraniAlarmi = new ArrayList<Alarm>();
		
		try {
			listaAlarma = BazaPodataka.dohvatiAlarme("SELECT * FROM CRM.ALARM");
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
		
		if (alarmiFilterTextField.getText().isEmpty() == false) {
			
			filtriraniAlarmi = listaAlarma.stream().filter(p -> p.getKlijent().getOib().contains(alarmiFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraniAlarmi = listaAlarma;
		}
		ObservableList<Alarm> listaAlarma = FXCollections.observableArrayList(filtriraniAlarmi);
		alarmiTableView.setItems(listaAlarma);
	}
	
	public void prikaziEkranAlarma() {
		try{
			BorderPane alarmiPane = (BorderPane)
			FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/DohvatAlarmaEkran.fxml"));
			Main.setCenterPane(alarmiPane);
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja ekrana alarma!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranKlijenata() {
		try{
			BorderPane klijentiPane = (BorderPane)
			FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/DohvatKlijenataEkran.fxml"));
			Main.setCenterPane(klijentiPane);
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja ekrana klijenata!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaposlenika() {
		try{
			BorderPane zaposleniciPane = (BorderPane)
			FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/DohvatZaposlenikaEkran.fxml"));
			Main.setCenterPane(zaposleniciPane);
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja ekrana zaposlenika!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranArtikala() {
		try{
			BorderPane artikliPane = (BorderPane)
			FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/DohvatArtikalaEkran.fxml"));
			Main.setCenterPane(artikliPane);
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja ekrana artikala!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranKomunikacija() {
		try{
			BorderPane komunikacijePane = (BorderPane)
			FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/DohvatKomunikacijaEkran.fxml"));
			Main.setCenterPane(komunikacijePane);
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja ekrana komunikacija!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranUsluga() {
		try{
			BorderPane uslugePane = (BorderPane)
			FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/DohvatUslugaEkran.fxml"));
			Main.setCenterPane(uslugePane);
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja ekrana usluga!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovogKlijenta() {
		
		try {			
			BorderPane noviKlijentPane = FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/NoviKlijent.fxml"));
			Scene scene = new Scene(noviKlijentPane, 450, 400);
			
			scene.getStylesheets().add(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/application.css").toExternalForm());
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja prozora za unos klijenata!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovogZaposlenika() {
		
		try {
			BorderPane noviZaposlenikPane = FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/NoviZaposlenik.fxml"));
			Scene scene = new Scene(noviZaposlenikPane, 450, 400);
			
			scene.getStylesheets().add(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/application.css").toExternalForm());
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja prozora za unos zaposlenika!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNoviArtikl() {
		
		try {
			BorderPane noviArtiklPane = FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/NoviArtikl.fxml"));
			Scene scene = new Scene(noviArtiklPane, 450, 400);
			
			scene.getStylesheets().add(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/application.css").toExternalForm());
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja prozora za unos artikala!", e);
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNoviAlarm() {
		
		try {
			BorderPane noviAlarmPane = FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/NoviAlarm.fxml"));
			Scene scene = new Scene(noviAlarmPane, 450, 400);
			
			scene.getStylesheets().add(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/application.css").toExternalForm());
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja prozora za unos alarma!", e);
			e.printStackTrace();
		}
	}
	
//	public void prikaziEkranZaNovuKomunikaciju() {
//		
//		try {
//			BorderPane novaKomunikacijaPane = FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/NovaKomunikacija.fxml"));
//			Scene scene = new Scene(novaKomunikacijaPane, 450, 400);
//			
//			scene.getStylesheets().add(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/application.css").toExternalForm());
//			
//			Stage stage = new Stage();
//			stage.setScene(scene);
//			stage.show();
//		} catch (IOException e) {
//			logger.error("Pogreška kod otvaranja prozora za unos komunikacija!", e);
//			e.printStackTrace();
//		}
//	}
	
	public void prikaziEkranZaNovuUslugu() {
		
		try {
			BorderPane novaUslugaPane = FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/NovaUsluga.fxml"));
			Scene scene = new Scene(novaUslugaPane, 450, 400);
			
			scene.getStylesheets().add(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/application.css").toExternalForm());
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			logger.error("Pogreška kod otvaranja prozora za unos usluga!", e);
			e.printStackTrace();
		}
	}
}
