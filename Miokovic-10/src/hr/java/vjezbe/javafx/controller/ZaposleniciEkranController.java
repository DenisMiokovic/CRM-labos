package hr.java.vjezbe.javafx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.javafx.Main;
import hr.java.vjezbe.niti.AlarmiNit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 
 * Predstavlja kontroler klasu aplikacije za ekran prikaza zaposlenika.
 * 
 * @author Denis
 *
 */

public class ZaposleniciEkranController {

	private static ObservableList<Zaposlenik> observableListaZaposlenika = FXCollections.observableArrayList();
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	public static boolean odabir = false;
	public static Zaposlenik zaposlenikKomunikacije;
	
	@FXML
	private TextField zaposleniciFilterTextField;
	
	@FXML
	private TableView<Zaposlenik> zaposleniciTableView;
	
	@FXML
	private TableColumn<Zaposlenik, String> korisnickoImeColumn;
	
	@FXML
	private TableColumn<Zaposlenik, String> prezimeColumn;
	
	@FXML
	private TableColumn<Zaposlenik, String> imeColumn;
	
	@FXML
	private TableColumn<Zaposlenik, String> sifraZaposlenikaColumn;

	/**
	 * @param observableListaZaposlenika stvaranje prikazne liste objekata klase Zaposlenik
	 * @param logger stvaranje objekta klase za logiranje
	 * @param odabir stvaranje boolean objekta koji se koristi u svrhu odabira zaposlenika kod unosa komunikacije
	 * @param zaposleniciFilterTextField stvaranje polja za pretraživanje zaposlenika
	 * @param zaposleniciTableView stvaranje tabelarnog prikaza zaposlenika
	 * @param korisnickoImeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje korisnièko ime zaposlenika
	 * @param prezimeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje prezime zaposlenika
	 * @param imeColumn stvaranje stupca u tabelarnom prikazu koji prikazuje ime zaposlenika
	 * @param sifraZaposlenikaColumn stvaranje stupca u tabelarnom prikazu koji prikazuje šifru zaposlenika
	 */
	
	@FXML
	public void initialize() {

		korisnickoImeColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("korisnickoIme"));
		
		prezimeColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("prezime"));
		
		imeColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("ime"));
		
		sifraZaposlenikaColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("sifraZaposlenika"));
		
		if (odabir == true) {
			zaposleniciTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (event.getClickCount() > 1) {
						
						try {
							zaposlenikKomunikacije = zaposleniciTableView.getSelectionModel().getSelectedItem();
							
							BorderPane novaKomunikacijaPane = FXMLLoader.load(getClass().getClassLoader()
									.getResource("hr/java/vjezbe/javafx/fxml/NovaKomunikacija.fxml"));
							Scene scene = new Scene(novaKomunikacijaPane, 450, 400);

							scene.getStylesheets().add(getClass().getClassLoader()
									.getResource("hr/java/vjezbe/javafx/application.css").toExternalForm());

							Stage stage = new Stage();
							stage.setScene(scene);
							stage.show();
							
							AlarmiNit.odabirZaposlenikaProzor.close();
							
						} catch (IOException e) {
							logger.error("Pogreška kod otvaranja prozora za unos komunikacija!", e);
							e.printStackTrace();
						}
					}
				}
			});
		}
	}
	
	public void prikaziZaposlenike() throws SQLException {
		
		List<Zaposlenik> filtriraniZaposlenici = new ArrayList<Zaposlenik>();
		List<Zaposlenik> listaZaposlenika = new ArrayList<Zaposlenik>();
		try {
			listaZaposlenika = BazaPodataka.dohvatiZaposlenike();
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
		
		if (zaposleniciFilterTextField.getText().isEmpty() == false) {
			
			filtriraniZaposlenici = listaZaposlenika.stream().filter(p -> p.getPrezime().contains(zaposleniciFilterTextField.getText()))
					.collect(Collectors.toList());
		} else {
			filtriraniZaposlenici = listaZaposlenika;
		}
		observableListaZaposlenika = FXCollections.observableArrayList(filtriraniZaposlenici);
		zaposleniciTableView.setItems(observableListaZaposlenika);
	}
	
	public static void dodajNovogZaposlenika(Zaposlenik noviZaposlenik) {
		observableListaZaposlenika.add(noviZaposlenik);
	}
}
