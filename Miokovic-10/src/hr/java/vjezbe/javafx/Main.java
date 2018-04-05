package hr.java.vjezbe.javafx;
	
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * 
 * Predstavlja glavnu klasu aplikacije.
 * 
 * @author Denis
 *
 */

public class Main extends Application {
	
	public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
	public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	private static BorderPane root;
	private Stage primaryStage;
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	/**
	 * Inicijalizacija podataka
	 * 
	 * @param dateTimeFormatter stvaranje objekta klase DateTimeFormatter za datum i vrijeme
	 * @param dateFormatter stvaranje objekta klase DateTimeFormatter za datum
	 * @param root stvaranje objekta klase BorderPane
	 * @param primaryStage stvaranje objekta klase Stage
	 * @param logger stvaranje objekta klase za logiranje
	 * @param upaljeno stvaranje objekta koji vraæa vrijednost TRUE ukoliko je aplikacija upaljena, a FALSE ukoliko nije
	 */
	
	@Override
	public void start(Stage stage) {
		
		primaryStage = stage;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getClassLoader().getResource("hr/java/vjezbe/javafx/fxml/PocetniEkran.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			       @Override
			       public void handle(WindowEvent e) {
			          Platform.exit();
			          System.exit(0);
			       }
			});
			
		} catch(Exception e) {
			logger.error("Pogreška!", e);
			e.printStackTrace();
		}
	}
	
	public static void setCenterPane(BorderPane centerPane) {
		root.setCenter(centerPane);
	}
		
	public static void main(String[] args) {
		
		launch(args);
	}
	
}
