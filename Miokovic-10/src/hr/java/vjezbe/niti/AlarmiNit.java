package hr.java.vjezbe.niti;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.javafx.Main;
import hr.java.vjezbe.javafx.controller.ZaposleniciEkranController;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 
 * Predstavlja klasu za višenitnost.
 * 
 * @author Denis
 *
 */

public class AlarmiNit implements Runnable {
	
	private static List<Alarm> listaAlarma;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	public static boolean unosKomunikacije = true;
	public static Klijent klijentKomunikacije;
	public static Stage odabirZaposlenikaProzor;
	
	public AlarmiNit() {

	}
	
	public void run() {
					
		String upit = "SELECT * FROM CRM.ALARM WHERE TIMESTAMPDIFF('SECOND', VRIJEME, NOW()) <= 0 ORDER BY VRIJEME ASC;";
			
		try {
			listaAlarma = BazaPodataka.dohvatiAlarme(upit);
		} catch (IOException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
		
		if (listaAlarma.isEmpty() == false && unosKomunikacije == true) {
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
	
					unosKomunikacije = false;
					
					Alarm alarm = listaAlarma.get(0);

					klijentKomunikacije = alarm.getKlijent();
					
					ButtonType confirmButton = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
					ButtonType cancelButton = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);

					Alert alert = new Alert(AlertType.CONFIRMATION,
							"Alarm za korisnika " + alarm.getKlijent().getPrezime() + " "
									+ alarm.getKlijent().getIme() + " s opisom '" + alarm.getOpisAlarma()
									+ "' istjeèe " + Main.dateTimeFormatter.format(alarm.getVrijemeAlarma())
									+ " Želite li unijeti podatke o komunikaciji?",
							confirmButton, cancelButton);
					alert.setTitle("Upozorenje");
					alert.setHeaderText("Alarm pred istekom");
					Optional<ButtonType> response = alert.showAndWait();

					if (response.isPresent() && response.get() == confirmButton) {
						
						ZaposleniciEkranController.odabir = true;

						BorderPane odabirZaposlenika;
						try {
							odabirZaposlenika = FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/DohvatZaposlenikaEkran.fxml"));
							Scene scene = new Scene(odabirZaposlenika, 600, 400);
							
							scene.getStylesheets().add(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/application.css").toExternalForm());
							
							odabirZaposlenikaProzor = new Stage();
							odabirZaposlenikaProzor.setScene(scene);
							odabirZaposlenikaProzor.show();
						} catch (IOException e) {
							logger.error("Pogreška kod otvaranja prozora za odabir zaposlenika!", e);
							e.printStackTrace();
						}
					} else {
						
						unosKomunikacije = true;
					}
				}
			});
		}
	}
}
